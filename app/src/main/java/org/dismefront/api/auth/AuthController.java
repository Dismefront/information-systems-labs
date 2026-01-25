package org.dismefront.api.auth;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.dismefront.app.exception.ValidationException;
import org.dismefront.data.auth.AuthenticationService;
import org.dismefront.data.user.User;
import org.dismefront.data.user.UserRepository;
import org.dismefront.data.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

  private final AuthenticationService authenticationService;
  private final UserService userService;
  private final UserRepository userRepository;

  @PostMapping("/login")
  public ResponseEntity<String> login(
      @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
    try {
      authenticationService.authenticate(
          loginRequest.getUsername(), loginRequest.getPassword(), request);
      return ResponseEntity.ok("Login successful");
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
  }

  @PostMapping("/register")
  public ResponseEntity register(
      @RequestBody RegisterRequest registerRequest, HttpServletRequest request) {

    validateRegistrationRequest(registerRequest);

    try {
      User user =
          userService.registerNewUser(registerRequest.getUsername(), registerRequest.getPassword());
      authenticationService.authenticate(
          registerRequest.getUsername(), registerRequest.getPassword(), request);
      return ResponseEntity.ok(user);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body("The user with these credentials already exists");
    }
  }

  @GetMapping("/user/get-current")
  public ResponseEntity<Optional<User>> getCurrentUser(Authentication authentication) {
    String username = authentication.getName();
    return ResponseEntity.ok().body(userRepository.findByUsername(username));
  }

  private void validateRegistrationRequest(RegisterRequest registerRequest) {
    if (!registerRequest.getPassword().equals(registerRequest.getPasswordRepeat())) {
      throw new ValidationException("Passwords do not match");
    }
  }
}
