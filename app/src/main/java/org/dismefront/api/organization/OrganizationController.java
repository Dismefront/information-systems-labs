package org.dismefront.api.organization;

import lombok.RequiredArgsConstructor;
import org.dismefront.data.organization.OrganizationRepository;
import org.dismefront.data.organization.OrganizationService;
import org.dismefront.data.shared.Role;
import org.dismefront.data.user.User;
import org.dismefront.data.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.MatchesPattern;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationRepository organizationRepository;
    private final OrganizationService organizationService;
    private final UserRepository userRepository;

    @GetMapping("/get-all")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(organizationRepository.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity add(@RequestBody OrganizationRequest organizationRequest, Principal principal) {
        String username = principal.getName();
        try {
            if (organizationRequest.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Name cannot be empty");
            }
            if (organizationRequest.getAnnualTurnover() <= 0) {
                return ResponseEntity.badRequest().body("Annual Turnover cannot be less than zero");
            }
            if (organizationRequest.getEmployeesCount() <= 0) {
                return ResponseEntity.badRequest().body("Employees count cannot be less than zero");
            }
            return ResponseEntity.ok().body(organizationService.saveOrganization(organizationRequest, username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody OrganizationRequest organizationRequest, Principal principal) {
        String username = principal.getName();
        try {
            if (organizationRequest.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Name cannot be empty");
            }
            if (organizationRequest.getAnnualTurnover() <= 0) {
                return ResponseEntity.badRequest().body("Annual Turnover cannot be less than zero");
            }
            if (organizationRequest.getEmployeesCount() <= 0) {
                return ResponseEntity.badRequest().body("Employees count cannot be less than zero");
            }
            return ResponseEntity.ok().body(organizationService.updateOrganization(organizationRequest, username, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity list(@RequestParam int page, @RequestParam int size, Principal principal) {
        Optional<User> user = userRepository.findByUsername(principal.getName());
        boolean isAdmin = user.isPresent() && user.get().getRoles().contains(Role.ROLE_ADMIN);
        return ResponseEntity.ok().body(organizationService.getOrganizationList(page, size, principal.getName(), isAdmin));
    }

}
