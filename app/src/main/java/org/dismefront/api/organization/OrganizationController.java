package org.dismefront.api.organization;

import lombok.RequiredArgsConstructor;
import org.dismefront.data.organization.OrganizationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.MatchesPattern;

@RestController
@RequestMapping("/api/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationRepository organizationRepository;

    @GetMapping("/get-all")
    public ResponseEntity findAll() {
        return ResponseEntity.ok(organizationRepository.findAll());
    }

}
