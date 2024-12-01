package org.dismefront.api.address;

import lombok.RequiredArgsConstructor;
import org.dismefront.data.address.AddressRepository;
import org.dismefront.data.address.AddressService;
import org.dismefront.data.shared.Role;
import org.dismefront.data.user.User;
import org.dismefront.data.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @GetMapping("/get-all")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(addressRepository.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity add(@RequestBody AddressRequest addressRequest, Principal principal) {
        String username = principal.getName();
        try {
            if (addressRequest.getZipCode().isEmpty()) {
                return ResponseEntity.badRequest().body("Zip code cannot be empty");
            }
            return ResponseEntity.ok().body(addressService.saveAddress(addressRequest, username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody AddressRequest addressRequest, Principal principal) {
        String username = principal.getName();
        try {
            if (addressRequest.getZipCode().isEmpty()) {
                return ResponseEntity.badRequest().body("Zip code cannot be empty");
            }
            return ResponseEntity.ok().body(addressService.updateAddress(addressRequest, username, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable long id, Principal principal) {
        String username = principal.getName();
        try {
            addressService.deleteAddress(id, username);
            return ResponseEntity.ok().build();
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.badRequest().body("You cannot delete this address");
        }
    }

    @GetMapping("/list")
    public ResponseEntity list(@RequestParam int page, @RequestParam int size, Principal principal) {
        Optional<User> user = userRepository.findByUsername(principal.getName());
        boolean isAdmin = user.isPresent() && user.get().getRoles().contains(Role.ROLE_ADMIN);
        return ResponseEntity.ok().body(addressService.getAddressList(page, size, principal.getName(), isAdmin));
    }
}
