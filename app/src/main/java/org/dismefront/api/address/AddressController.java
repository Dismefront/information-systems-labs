package org.dismefront.api.address;

import lombok.RequiredArgsConstructor;
import org.dismefront.data.address.AddressRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressRepository addressRepository;

    @GetMapping("/get-all")
    public ResponseEntity findAll() {
        return ResponseEntity.ok(addressRepository.findAll());
    }
}
