package org.dismefront.data.organization;

import jakarta.transaction.Transactional;
import org.dismefront.api.organization.OrganizationRequest;
import org.dismefront.data.address.AddressRepository;
import org.dismefront.data.event.Event;
import org.dismefront.data.event.EventName;
import org.dismefront.data.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final AddressRepository addressRepository;
    private final EventRepository eventRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository, AddressRepository addressRepository, EventRepository eventRepository) {
        this.organizationRepository = organizationRepository;
        this.addressRepository = addressRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public Organization updateOrganization(OrganizationRequest organizationRequest, String username, Long organizationId) {
        Organization organization = organizationRepository.getReferenceById(organizationId);
        organizationParams(organizationRequest, organization);

        Event event = new Event(EventName.ORGANIZATION_UPDATED, username, organization.getId(), new Timestamp(new Date().getTime()));
        eventRepository.save(event);
        return organizationRepository.save(organization);
    }

    @Transactional
    public Organization saveOrganization(OrganizationRequest organizationRequest, String username) {
        Organization organization = new Organization();
        organizationParams(organizationRequest, organization);

        Organization savedOrganization = organizationRepository.save(organization);
        organizationRepository.flush();
        Event event = new Event(EventName.ORGANIZATION_CREATED, username, savedOrganization.getId(), new Timestamp(new Date().getTime()));
        eventRepository.save(event);
        return savedOrganization;
    }

    private void organizationParams(OrganizationRequest organizationRequest, Organization organization) {
        organization.setName(organizationRequest.getName());
        organization.setOfficialAddress(addressRepository.getReferenceById(organizationRequest.getOfficialAddressId()));
        organization.setAnnualTurnover(organizationRequest.getAnnualTurnover());
        organization.setEmployeesCount(organizationRequest.getEmployeesCount());
        organization.setFullName(organizationRequest.getFullName());
        organization.setPostalAddress(addressRepository.getReferenceById(organizationRequest.getPostalAddressId()));
    }

    @Transactional
    public void deleteOrganization(Long id, String username) {
        organizationRepository.deleteById(id);
        Event event = new Event(EventName.ORGANIZATION_DELETED, username, id, new Timestamp(new Date().getTime()));
        eventRepository.save(event);
    }

    public Page<OrganizationManaged> getOrganizationList(int page, int size, String username, boolean isAdmin) {
        Pageable pageable = PageRequest.of(page, size);
        return organizationRepository.findOrganizationsWithEditableFlag(username, EventName.ORGANIZATION_CREATED, isAdmin, pageable);
    }
}
