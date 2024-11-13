package org.dismefront.api.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;

  @PostMapping("/login")
  public ResponseEntity<String> login(
      @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  loginRequest.getUsername(), loginRequest.getPassword()));
      SecurityContext sc = SecurityContextHolder.getContext();
      sc.setAuthentication(authentication);
      HttpSession session = request.getSession(true);
      session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
      return ResponseEntity.ok("Login successful");
    } catch (AuthenticationException e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
  }
}
