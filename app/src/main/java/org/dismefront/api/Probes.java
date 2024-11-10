package org.dismefront.api;

import java.util.Map;
import java.util.Optional;
import org.dismefront.data.user.User;
import org.dismefront.data.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Probes {

  private final UserRepository userRepository;

  public Probes(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/alive")
  public ResponseEntity<String> alive() {
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("/db-ready")
  public ResponseEntity<Map<String, String>> dbReady() {
    Optional<User> user = userRepository.findByUsername("admin");
    return user.map(
            value ->
                ResponseEntity.status(HttpStatus.OK).body(Map.of("username", value.getUsername())))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null));
  }

  @PostMapping("/protected")
  public ResponseEntity<String> protectedRoute() {
    return ResponseEntity.ok().body("Hello world");
  }

  @GetMapping("/user/get-current")
  public ResponseEntity<Optional<User>> getCurrentUser(Authentication authentication) {
    String username = authentication.getName();
    return ResponseEntity.ok().body(userRepository.findByUsername(username));
  }
}
