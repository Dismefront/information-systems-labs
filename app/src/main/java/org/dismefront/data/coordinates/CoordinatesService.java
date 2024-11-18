package org.dismefront.data.coordinates;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dismefront.api.coordinates.CoordinatesRequest;
import org.dismefront.data.event.Event;
import org.dismefront.data.event.EventName;
import org.dismefront.data.event.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CoordinatesService {

    private final CoordinatesRepository coordinatesRepository;
    private final EventRepository eventRepository;

    @Transactional
    public Coordinates updateCoordinates(CoordinatesRequest coordinatesRequest, String username, Long coordinatesId) {
        Coordinates coordinates = coordinatesRepository.getReferenceById(coordinatesId);
        coordinatesParams(coordinatesRequest, coordinates);

        Event event = new Event(EventName.COORDINATE_UPDATED, username, coordinates.getId(), new Timestamp(new Date().getTime()));
        eventRepository.save(event);
        return coordinatesRepository.save(coordinates);
    }

    @Transactional
    public Coordinates saveCoordinates(CoordinatesRequest coordinatesRequest, String username) {
        Coordinates coordinates = new Coordinates();
        coordinatesParams(coordinatesRequest, coordinates);

        Coordinates savedCoordinates = coordinatesRepository.save(coordinates);
        coordinatesRepository.flush();
        Event event = new Event(EventName.COORDINATE_CREATED, username, savedCoordinates.getId(), new Timestamp(new Date().getTime()));
        eventRepository.save(event);
        return savedCoordinates;
    }

    private void coordinatesParams(CoordinatesRequest coordinatesRequest, Coordinates coordinates) {
        coordinates.setX(coordinatesRequest.getX());
        coordinates.setY(coordinatesRequest.getY());
    }

    public Page<CoordinatesManaged> getCoordinatesList(int page, int size, String username, boolean isAdmin) {
        Pageable pageable = PageRequest.of(page, size);
        return coordinatesRepository.findCoordinatesWithEditableFlag(username, EventName.COORDINATE_CREATED, isAdmin, pageable);
    }
}
