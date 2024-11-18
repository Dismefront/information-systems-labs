package org.dismefront.api.person;

import lombok.RequiredArgsConstructor;
import org.dismefront.data.person.Person;
import org.dismefront.data.person.PersonRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonRepository personRepository;

    @GetMapping("/get-all")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(personRepository.findAll());
    }
}
