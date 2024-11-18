package org.dismefront.api.coordinates;

import lombok.RequiredArgsConstructor;
import org.dismefront.data.coordinates.CoordinatesRepository;
import org.dismefront.data.coordinates.CoordinatesService;
import org.dismefront.data.shared.Role;
import org.dismefront.data.user.User;
import org.dismefront.data.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("api/coordinates")
@RequiredArgsConstructor
public class CoordinatesController {

    private final CoordinatesRepository coordinatesRepository;
    private final CoordinatesService coordinatesService;
    private final UserRepository userRepository;

    @GetMapping("/get-all")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(coordinatesRepository.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity add(@RequestBody CoordinatesRequest coordinatesRequest, Principal principal) {
        String username = principal.getName();
        try {
            if (coordinatesRequest.getY() == null) {
                return ResponseEntity.badRequest().body("Y coordinate cannot be null");
            }
            return ResponseEntity.ok().body(coordinatesService.saveCoordinates(coordinatesRequest, username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody CoordinatesRequest coordinatesRequest, Principal principal) {
        String username = principal.getName();
        try {
            if (coordinatesRequest.getY() == null) {
                return ResponseEntity.badRequest().body("Y coordinate cannot be null");
            }
            return ResponseEntity.ok().body(coordinatesService.updateCoordinates(coordinatesRequest, username, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity list(@RequestParam int page, @RequestParam int size, Principal principal) {
        Optional<User> user = userRepository.findByUsername(principal.getName());
        boolean isAdmin = user.isPresent() && user.get().getRoles().contains(Role.ROLE_ADMIN);
        return ResponseEntity.ok().body(coordinatesService.getCoordinatesList(page, size, principal.getName(), isAdmin));
    }
}
