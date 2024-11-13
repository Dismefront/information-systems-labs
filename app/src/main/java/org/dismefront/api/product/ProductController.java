package org.dismefront.api.product;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.dismefront.data.product.Product;
import org.dismefront.data.product.ProductRequest;
import org.dismefront.data.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

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

    @GetMapping("/list")
    public ResponseEntity list() {
        return ResponseEntity.badRequest().body("List is now not awailable");
    }

}