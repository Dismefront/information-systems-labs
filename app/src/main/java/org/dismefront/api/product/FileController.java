package org.dismefront.api.product;

import lombok.RequiredArgsConstructor;
import org.dismefront.app.MinioService;
import org.dismefront.data.importHistory.ImportHistory;
import org.dismefront.data.importHistory.ImportHistoryRepository;
import org.dismefront.data.importHistory.ImportStatus;
import org.dismefront.data.product.ProductService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.Yaml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class FileController {

    private final ProductService productService;
    private final MinioService minioService;
    private final ImportHistoryRepository importHistoryRepository;

    @PostMapping("/upload-data")
    @Transactional
    public ResponseEntity updloadYamlFile(@RequestBody String yamlContent, Principal principal) {
        String username = principal.getName();
        String bucketName = "import-files";
        String fileName = "upload-" + System.currentTimeMillis() + ".yaml";
        ImportHistory importHistory = null;

        try {
            importHistory = productService.saveToImportHistory(username, -1L);
            InputStream fileStream = new ByteArrayInputStream(yamlContent.getBytes());
            minioService.uploadFile(bucketName, fileName, fileStream, yamlContent.getBytes().length, "application/x-yaml");
            importHistory.setStorageKey(fileName);

            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(yamlContent);

            ArrayList<Map<String, Object>> products = (ArrayList<Map<String, Object>>) yamlData.get("products");

            importHistory.setObjectCount((long) products.size());
            productService.uploadProductsFromFile(products, username, importHistory);

            importHistory.setStatus(ImportStatus.RESOLVED);
            importHistoryRepository.save(importHistory);

            return ResponseEntity.ok("File and data saved successfully.");
        } catch (Exception e) {

            if (importHistory != null) {
                if (minioService.isMinioAvailable()) {
                    importHistory.setStatus(ImportStatus.REJECTED);
                    importHistoryRepository.save(importHistory);
                    return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(e.getMessage());
                } else {
                    try {
                        importHistoryRepository.delete(importHistory);
                    } catch (Exception ex) {
                        System.err.println("Failed to delete import history record: " + ex.getMessage());
                    }
                }
            }

            if (importHistory == null) {
                try {
                    minioService.deleteFile(bucketName, fileName);
                } catch (Exception ex) {
                    System.err.println("Failed to delete file from MinIO during rollback: " + ex.getMessage());
                }
            }

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save data and file: " + e.getMessage());
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity downloadFile(@PathVariable String fileName) {
        try {
            InputStream stream = minioService.downloadFile("import-files", fileName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                    .body(new InputStreamResource(stream));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error downloading file: " + e.getMessage());
        }
    }


}
