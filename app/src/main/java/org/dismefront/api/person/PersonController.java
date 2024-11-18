package org.dismefront.api.person;

import lombok.RequiredArgsConstructor;
import org.dismefront.data.person.Person;
import org.dismefront.data.person.PersonRepository;
import org.dismefront.data.person.PersonService;
import org.dismefront.data.shared.Role;
import org.dismefront.data.user.User;
import org.dismefront.data.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonRepository personRepository;
    private final PersonService personService;
    private final UserRepository userRepository;

    @GetMapping("/get-all")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(personRepository.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity add(@RequestBody PersonRequest personRequest, Principal principal) {
        String username = principal.getName();
        try {
            if (personRequest.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Name cannot be empty");
            }
            return ResponseEntity.ok().body(personService.savePerson(personRequest, username));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity add(@PathVariable long id, @RequestBody PersonRequest personRequest, Principal principal) {
        String username = principal.getName();
        try {
            if (personRequest.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Name cannot be empty");
            }
            return ResponseEntity.ok().body(personService.updatePerson(personRequest, username, id));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity list(@RequestParam int page, @RequestParam int size, Principal principal) {
        Optional<User> user = userRepository.findByUsername(principal.getName());
        boolean isAdmin = user.isPresent() && user.get().getRoles().contains(Role.ROLE_ADMIN);
        return ResponseEntity.ok().body(personService.getPersonList(page, size, principal.getName(), isAdmin));
    }
}
