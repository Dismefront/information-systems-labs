package org.dismefront.api.product;

import org.dismefront.data.product.Product;
import org.dismefront.data.product.ProductRequest;
import org.dismefront.data.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<Product> create(@RequestBody ProductRequest productRequest) {
        try {
            return ResponseEntity.ok().body(productService.saveProduct(productRequest));
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
