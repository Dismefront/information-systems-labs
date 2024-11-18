package org.dismefront.api.organization;

import lombok.RequiredArgsConstructor;
import org.dismefront.data.location.LocationRepository;
import org.dismefront.data.organization.OrganizationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationRepository locationRepository;

    @GetMapping("/get-all")
    public ResponseEntity findAll() {
        return ResponseEntity.ok(locationRepository.findAll());
    }

}
