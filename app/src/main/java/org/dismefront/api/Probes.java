package org.dismefront.api;

import lombok.RequiredArgsConstructor;
import org.dismefront.data.Entity;
import org.dismefront.data.EntityRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class Probes {

    private final EntityRepository entityRepository;

    public Probes(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    @GetMapping("/alive")
    public Map<String, String> alive() {
        return Map.of("ok", "200");
    }

    @GetMapping("/bd/create/alive")
    public Entity entity() {
        Entity entity = new Entity();
        entity.setSurname("234234");
        return entityRepository.save(entity);
    }

    @GetMapping("bd/get/alive/{id}")
    public Optional<Entity> getEntity(@PathVariable Long id) {
        return entityRepository.findById(id);
    }
}
