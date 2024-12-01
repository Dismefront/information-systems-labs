package org.dismefront.data.address;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dismefront.api.address.AddressRequest;
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
public class AddressService {

    private final AddressRepository addressRepository;
    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;

    @Transactional
    public Address updateAddress(AddressRequest addressRequest, String username, Long addressId) {
        Address address = addressRepository.getReferenceById(addressId);
        addressParams(addressRequest, address);

        Event event = new Event(EventName.ADDRESS_UPDATED, username, address.getId(), new Timestamp(new Date().getTime()));
        eventRepository.save(event);
        return addressRepository.save(address);
    }

    @Transactional
    public Address saveAddress(AddressRequest addressRequest, String username) {
        Address address = new Address();
        addressParams(addressRequest, address);

        Address savedAddress = addressRepository.save(address);
        addressRepository.flush();
        Event event = new Event(EventName.ADDRESS_CREATED, username, savedAddress.getId(), new Timestamp(new Date().getTime()));
        eventRepository.save(event);
        return savedAddress;
    }

    private void addressParams(AddressRequest addressRequest, Address address) {
        address.setZipCode(addressRequest.getZipCode());
        address.setTown(locationRepository.getReferenceById(addressRequest.getTownId()));
    }

    @Transactional
    public void deleteAddress(Long id, String username) {
        addressRepository.deleteById(id);
        Event event = new Event(EventName.ADDRESS_DELETED, username, id, new Timestamp(new Date().getTime()));
        eventRepository.save(event);
    }

    public Page<AddressManaged> getAddressList(int page, int size, String username, boolean isAdmin) {
        Pageable pageable = PageRequest.of(page, size);
        return addressRepository.findAddressesWithEditableFlag(username, EventName.ADDRESS_CREATED, isAdmin, pageable);
    }
}
