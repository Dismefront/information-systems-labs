package org.dismefront.api.product;

import java.security.Principal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.dismefront.app.exception.BusinessLogicException;
import org.dismefront.app.exception.ValidationException;
import org.dismefront.data.product.ProductService;
import org.dismefront.data.shared.Role;
import org.dismefront.data.user.User;
import org.dismefront.data.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;
  private final UserRepository userRepository;

  @PostMapping("/create")
  public ResponseEntity create(@RequestBody ProductRequest productRequest, Principal principal) {
    String username = principal.getName();
    
    // Validation moved to service layer or here
    validateProductRequest(productRequest);
    
    return ResponseEntity.ok().body(productService.saveProduct(productRequest, username));
  }

  @GetMapping("/list")
  public ResponseEntity list(
      @RequestParam int page,
      @RequestParam int size,
      @RequestParam String sortBy,
      @RequestParam String sortDir,
      Principal principal) {
    Optional<User> user = userRepository.findByUsername(principal.getName());
    boolean isAdmin = user.isPresent() && user.get().getRoles().contains(Role.ROLE_ADMIN);
    return ResponseEntity.ok()
        .body(
            productService.getProductList(
                page, size, principal.getName(), isAdmin, sortBy, sortDir));
  }

  @PutMapping("/update/{id}")
  public ResponseEntity update(
      @PathVariable long id, @RequestBody ProductRequest productRequest, Principal principal) {
    String username = principal.getName();
    
    // Validation moved to service layer or here
    validateProductRequest(productRequest);
    
    return ResponseEntity.ok().body(productService.updateProduct(productRequest, username, id));
  }

  @PostMapping("/delete/{id}")
  public ResponseEntity delete(@PathVariable long id, Principal principal) {
    String username = principal.getName();
    productService.deleteProduct(id, username);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/by-manufacturer")
  public ResponseEntity byManufacturer() {
    return ResponseEntity.ok().body(productService.getProductsGroupedByManufacturer());
  }

  @GetMapping("/by-rating")
  public ResponseEntity byRating(@RequestParam int rating) {
    return ResponseEntity.ok().body(productService.countObjectsByRating(rating));
  }

  @GetMapping("/by-partNumber")
  public ResponseEntity byPartNumber(@RequestParam String partNumber) {
    return ResponseEntity.ok().body(productService.countProductsByPartNumber(partNumber));
  }

  @GetMapping("/products-by-manufacturer")
  public ResponseEntity getProductsByManufacturerId(@RequestParam int manufacturerId) {
    return ResponseEntity.ok().body(productService.getProductsByManufacturer(manufacturerId));
  }

  @GetMapping("/reduce-price")
  public ResponseEntity getProductsByManufacturerId(@RequestParam double percent) {
    productService.reducePricesByPercent(percent);
    return ResponseEntity.ok().build();
  }
  
  private void validateProductRequest(ProductRequest productRequest) {
    if (productRequest.getName() == null || productRequest.getName().isEmpty()) {
      throw new ValidationException("Name cannot be empty");
    }
    if (productRequest.getPrice() <= 0) {
      throw new ValidationException("Price must be greater than 0");
    }
    if (productRequest.getPartNumber() == null || productRequest.getPartNumber().isEmpty()
        || productRequest.getPartNumber().length() >= 49) {
      throw new ValidationException("Part number must be less than 49 symbols long");
    }
    if (productRequest.getRating() <= 0) {
      throw new ValidationException("Rating must be greater than 0");
    }
  }
}
