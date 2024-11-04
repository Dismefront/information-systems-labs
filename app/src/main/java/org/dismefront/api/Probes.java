package org.dismefront.api;

import org.dismefront.data.user.User;
import org.dismefront.data.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

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
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("username", user.get().getUsername()));
    }


}
