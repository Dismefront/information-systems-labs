package org.dismefront.api.product;

import lombok.RequiredArgsConstructor;
import org.dismefront.data.importHistory.ImportHistory;
import org.dismefront.data.product.ProductService;
import org.dismefront.data.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class FileController {

    private final ProductService productService;
    private final UserRepository userRepository;

    @PostMapping("/upload-data")
    public ResponseEntity updloadYamlFile(@RequestBody String yamlContent, Principal principal) {
        String username = principal.getName();
        try {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(yamlContent);
            ArrayList<Map<String, Object>> products = (ArrayList<Map<String, Object>>) yamlData.get("products");
            ImportHistory importHistory = productService.saveToImportHistory(username, (long) products.size());
            productService.uploadProductsFromFile(products, username, importHistory);
            return ResponseEntity.ok().body("Yaml file uploaded successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
