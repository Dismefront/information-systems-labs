package org.dismefront.api.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.dismefront.data.user.User;
import org.dismefront.data.user.UserRepository;
import org.dismefront.data.user.UserService;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final UserRepository userRepository;

  private void authenticate(String username, String password, HttpServletRequest request) throws AuthenticationException {
    Authentication authentication =
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username, password));
    SecurityContext sc = SecurityContextHolder.getContext();
    sc.setAuthentication(authentication);
    HttpSession session = request.getSession(true);
    session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(
          @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
    try {
      authenticate(loginRequest.getUsername(), loginRequest.getPassword(), request);
      return ResponseEntity.ok("Login successful");
    } catch (AuthenticationException e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
  }

  @PostMapping("/register")
  public ResponseEntity register(@RequestBody RegisterRequest registerRequest, HttpServletRequest request) {
    if (!registerRequest.getPassword().equals(registerRequest.getPasswordRepeat())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Passwords do not match");
    }
    try {
      User user = userService.registerNewUser(registerRequest.getUsername(), registerRequest.getPassword());
      authenticate(registerRequest.getUsername(), registerRequest.getPassword(), request);
      return ResponseEntity.ok(user);
    }
    catch (TransactionSystemException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("The user with these credentials already exists");
    }
  }

  @GetMapping("/user/get-current")
  public ResponseEntity<Optional<User>> getCurrentUser(Authentication authentication) {
    String username = authentication.getName();
    return ResponseEntity.ok().body(userRepository.findByUsername(username));
  }
}
