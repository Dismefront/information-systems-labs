package org.dismefront.data.person;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dismefront.api.person.PersonRequest;
import org.dismefront.data.event.Event;
import org.dismefront.data.event.EventName;
import org.dismefront.data.event.EventRepository;
import org.dismefront.data.location.LocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;

    @Transactional
    public Person updatePerson(PersonRequest personRequest, String username, Long personId) {
        Person person = personRepository.getReferenceById(personId);
        personParams(personRequest, person);

        Event event = new Event(EventName.PERSON_UPDATED, username, person.getId(), new Timestamp(new Date().getTime()));
        eventRepository.save(event);
        return personRepository.save(person);
    }

    @Transactional
    public Person savePerson(PersonRequest personRequest, String username) {
        Person person = new Person();
        personParams(personRequest, person);

        Person savedPerson = personRepository.save(person);
        personRepository.flush();
        Event event = new Event(EventName.PERSON_CREATED, username, savedPerson.getId(), new Timestamp(new Date().getTime()));
        eventRepository.save(event);
        return savedPerson;
    }

    private void personParams(PersonRequest personRequest, Person person) {
        person.setName(personRequest.getName());
        person.setEyeColor(personRequest.getEyeColor());
        person.setHairColor(personRequest.getHairColor());
        person.setLocation(locationRepository.getReferenceById(personRequest.getLocationId()));
        person.setHeight(personRequest.getHeight());
        person.setNationality(personRequest.getNationality());
    }

    @Transactional
    public void deletePerson(Long id, String username) {
        personRepository.deleteById(id);
        Event event = new Event(EventName.PERSON_DELETED, username, id, new Timestamp(new Date().getTime()));
        eventRepository.save(event);
    }

    public Page<PersonManaged> getPersonList(int page, int size, String username, boolean isAdmin) {
        Pageable pageable = PageRequest.of(page, size);
        return personRepository.findPersonsWithEditableFlag(username, EventName.PERSON_CREATED, isAdmin, pageable);
    }
}
