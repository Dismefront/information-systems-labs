package org.dismefront.data.location;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dismefront.api.location.LocationRequest;
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
public class LocationService {

    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;

    @Transactional
    public Location updateLocation(LocationRequest locationRequest, String username, Long locationId) {
        Location location = locationRepository.getReferenceById(locationId);
        locationParams(locationRequest, location);

        Event event = new Event(EventName.LOCATION_UPDATED, username, location.getId(), new Timestamp(new Date().getTime()));
        eventRepository.save(event);
        return locationRepository.save(location);
    }

    @Transactional
    public Location saveLocation(LocationRequest locationRequest, String username) {
        Location location = new Location();
        locationParams(locationRequest, location);

        Location savedLocation = locationRepository.save(location);
        locationRepository.flush();
        Event event = new Event(EventName.LOCATION_CREATED, username, savedLocation.getId(), new Timestamp(new Date().getTime()));
        eventRepository.save(event);
        return savedLocation;
    }

    private void locationParams(LocationRequest locationRequest, Location location) {
        location.setX(locationRequest.getX());
        location.setY(locationRequest.getY());
        location.setZ(locationRequest.getZ());
    }

    @Transactional
    public void deleteLocation(Long id, String username) {
        locationRepository.deleteById(id);
        Event event = new Event(EventName.LOCATION_DELETED, username, id, new Timestamp(new Date().getTime()));
        eventRepository.save(event);
    }

    public Page<LocationManaged> getLocationList(int page, int size, String username, boolean isAdmin) {
        Pageable pageable = PageRequest.of(page, size);
        return locationRepository.findLocationsWithEditableFlag(username, EventName.LOCATION_CREATED, isAdmin, pageable);
    }
}
