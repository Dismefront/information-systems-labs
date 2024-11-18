package org.dismefront.api.product;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.dismefront.data.product.Product;
import org.dismefront.data.product.ProductService;
import org.dismefront.data.shared.Role;
import org.dismefront.data.user.AppUserDetailsService;
import org.dismefront.data.user.User;
import org.dismefront.data.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;


@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody ProductRequest productRequest, Principal principal) {
        String username = principal.getName();
        try {
            if (productRequest.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Name cannot be empty");
            }
            if (productRequest.getPrice() <= 0) {
                return ResponseEntity.badRequest().body("Price must be greater than 0");
            }
            if (productRequest.getPartNumber().isEmpty() || productRequest.getPartNumber().length() >= 49) {
                return ResponseEntity.badRequest().body("Part number must be less than 49 symbols long");
            }
            if (productRequest.getRating() <= 0) {
                return ResponseEntity.badRequest().body("Rating must be greater than 0");
            }
            return ResponseEntity.ok().body(productService.saveProduct(productRequest, username));
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity list(@RequestParam int page, @RequestParam int size, Principal principal) {
        Optional<User> user = userRepository.findByUsername(principal.getName());
        boolean isAdmin = user.isPresent() && user.get().getRoles().contains(Role.ROLE_ADMIN);
        return ResponseEntity.ok().body(productService.getProductList(page, size, principal.getName(), isAdmin));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody ProductRequest productRequest, Principal principal) {
        String username = principal.getName();
        try {
            if (productRequest.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Name cannot be empty");
            }
            if (productRequest.getPrice() <= 0) {
                return ResponseEntity.badRequest().body("Price must be greater than 0");
            }
            if (productRequest.getPartNumber().isEmpty() || productRequest.getPartNumber().length() >= 49) {
                return ResponseEntity.badRequest().body("Part number must be less than 49 symbols long");
            }
            if (productRequest.getRating() <= 0) {
                return ResponseEntity.badRequest().body("Rating must be greater than 0");
            }
            return ResponseEntity.ok().body(productService.updateProduct(productRequest, username, id));
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
