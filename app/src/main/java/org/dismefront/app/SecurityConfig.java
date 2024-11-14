package org.dismefront.app;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.dismefront.data.user.AppUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final AppUserDetailsService appUserDetailsService;

  @Bean
  public PasswordEncoder passwordEncoder() {
    // Использую Deprecated метод потому что только он из коробки дает sha-384
    return new MessageDigestPasswordEncoder("SHA-384");
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(appUserDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(authProvider);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            (requests) ->
                requests
                    .requestMatchers("/api/register", "/api/login", "/api/alive", "/api/db-ready")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .cors(
            cors ->
                cors.configurationSource(
                    request -> {
                      CorsConfiguration configuration = new CorsConfiguration();
                      configuration.setAllowedOrigins(List.of("http://localhost:5173"));
                      configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                      configuration.setAllowedHeaders(List.of("*"));
                      configuration.setAllowCredentials(true);
                      return configuration;
                    }))
        .formLogin(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            (session) -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
        .logout(
            logout -> logout.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()));

    return http.build();
  }
}
