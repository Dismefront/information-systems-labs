package org.dismefront.api.location;

import lombok.RequiredArgsConstructor;
import org.dismefront.api.location.LocationRequest;
import org.dismefront.data.location.LocationRepository;
import org.dismefront.data.location.LocationService;
import org.dismefront.data.organization.OrganizationRepository;
import org.dismefront.data.shared.Role;
import org.dismefront.data.user.User;
import org.dismefront.data.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationRepository locationRepository;
    private final LocationService locationService;
    private final UserRepository userRepository;

    @GetMapping("/get-all")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(locationRepository.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity add(@RequestBody LocationRequest locationRequest, Principal principal) {
        String username = principal.getName();
        try {
            if (locationRequest.getZ() == null) {
                return ResponseEntity.badRequest().body("Z coordinate cannot be null");
            }
            return ResponseEntity.ok().body(locationService.saveLocation(locationRequest, username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody LocationRequest locationRequest, Principal principal) {
        String username = principal.getName();
        try {
            if (locationRequest.getZ() == null) {
                return ResponseEntity.badRequest().body("Z coordinate cannot be null");
            }
            return ResponseEntity.ok().body(locationService.updateLocation(locationRequest, username, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity list(@RequestParam int page, @RequestParam int size, Principal principal) {
        Optional<User> user = userRepository.findByUsername(principal.getName());
        boolean isAdmin = user.isPresent() && user.get().getRoles().contains(Role.ROLE_ADMIN);
        return ResponseEntity.ok().body(locationService.getLocationList(page, size, principal.getName(), isAdmin));
    }

}
