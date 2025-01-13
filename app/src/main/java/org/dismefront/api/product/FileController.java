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
        boolean isMinioSaved = false;
        boolean isDatabaseSaved = false;
        ImportHistory importHistory = null;
        try {
            try {
                Yaml yaml = new Yaml();
                Map<String, Object> yamlData = yaml.load(yamlContent);
                ArrayList<Map<String, Object>> products = (ArrayList<Map<String, Object>>) yamlData.get("products");
                importHistory = productService.saveToImportHistory(username, (long) products.size());
                productService.uploadProductsFromFile(products, username, importHistory);
                isDatabaseSaved = true;
            }
            catch (Exception e) {
                if (importHistory != null) {
                    importHistory.setStatus(ImportStatus.REJECTED);
                }
                System.err.println("Failed to save data to the database: " + e.getMessage());
            }

            try {
                InputStream fileStream = new ByteArrayInputStream(yamlContent.getBytes());
                minioService.uploadFile(bucketName, fileName, fileStream, yamlContent.getBytes().length, "application/x-yaml");
                isMinioSaved = true;
                if (importHistory != null) {
                    importHistory.setStorageKey(fileName);
                }
            } catch (Exception e) {
                System.err.println("Failed to save file to MinIO: " + e.getMessage());
            }
            if (importHistory != null) {
                importHistoryRepository.save(importHistory);
            }

            if (isMinioSaved && isDatabaseSaved) {
                return ResponseEntity.ok("File and data saved successfully.");
            } else if (isMinioSaved) {
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                        .body("File saved to MinIO, but database operation failed.");
            } else if (isDatabaseSaved) {
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                        .body("Data saved to database, but file upload to MinIO failed.");
            } else {
                throw new RuntimeException("Both file storage and database operations failed.");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save data and file.");
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
