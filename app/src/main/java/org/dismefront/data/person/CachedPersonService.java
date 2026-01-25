package org.dismefront.data.person;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CachedPersonService {

  private final PersonRepository personRepository;

  @Cacheable(value = "persons", key = "#id")
  public Optional<Person> findById(Long id) {
    return personRepository.findById(id);
  }

  @CacheEvict(value = "persons", key = "#person.id")
  public Person save(Person person) {
    return personRepository.save(person);
  }

  @CacheEvict(value = "persons", key = "#id")
  public void deleteById(Long id) {
    personRepository.deleteById(id);
  }
}
