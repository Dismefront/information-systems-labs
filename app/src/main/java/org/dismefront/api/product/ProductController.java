package org.dismefront.api.product;

import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Product> create(@RequestBody ProductRequest productRequest, Principal principal) {
        String username = principal.getName();
        try {
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

}
