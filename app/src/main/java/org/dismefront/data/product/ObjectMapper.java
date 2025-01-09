package org.dismefront.data.product;

import lombok.RequiredArgsConstructor;
import org.dismefront.data.address.Address;
import org.dismefront.data.address.AddressRepository;
import org.dismefront.data.coordinates.Coordinates;
import org.dismefront.data.coordinates.CoordinatesRepository;
import org.dismefront.data.location.Location;
import org.dismefront.data.location.LocationRepository;
import org.dismefront.data.organization.Organization;
import org.dismefront.data.organization.OrganizationRepository;
import org.dismefront.data.person.Person;
import org.dismefront.data.person.PersonRepository;
import org.dismefront.data.shared.Color;
import org.dismefront.data.shared.Country;
import org.dismefront.data.shared.UnitOfMeasure;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class ObjectMapper {

    private final CoordinatesRepository coordinatesRepository;
    private final OrganizationRepository organizationRepository;
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;
    private final LocationRepository locationRepository;

    public void setCoordinatesParamsFromMap(Coordinates coordinates, Map<String, Object> coordinatesParams) {
        coordinates.setX(((Number) coordinatesParams.get("x")).intValue());
        coordinates.setY(((Number) coordinatesParams.get("y")).intValue());
    }

    public void setAddressParamsFromMap(Address address, Map<String, Object> addressParams) {
        address.setZipCode((String) addressParams.get("zipCode"));

        if (addressParams.get("townId") != null) {
            Long townId = ((Number) addressParams.get("townId")).longValue();
            address.setTown(locationRepository.getReferenceById(townId));
        } else if (addressParams.get("town") != null) {
            Location town = new Location();
            setLocationParamsFromMap(town, (Map<String, Object>) addressParams.get("town"));
            address.setTown(town);
        } else {
            address.setTown(null);
        }
    }

    public void setLocationParamsFromMap(Location location, Map<String, Object> locationParams) {
        location.setX(((Number) locationParams.get("x")).longValue());
        location.setY(((Number) locationParams.get("y")).doubleValue());
        location.setZ(((Number) locationParams.get("z")).floatValue());
    }

    public void setOrganizationParamsFromMap(Organization organization, Map<String, Object> organizationParams) {
        organization.setName((String) organizationParams.get("name"));
        organization.setAnnualTurnover((Integer) organizationParams.get("annualTurnover"));
        organization.setEmployeesCount(((Number) organizationParams.get("employeesCount")).longValue());
        organization.setFullName((String) organizationParams.get("fullName"));

        if (organizationParams.get("officialAddressId") != null) {
            Long officialAddressId = ((Number) organizationParams.get("officialAddressId")).longValue();
            organization.setOfficialAddress(addressRepository.getReferenceById(officialAddressId));
        } else if (organizationParams.get("officialAddress") != null) {
            Address officialAddress = new Address();
            setAddressParamsFromMap(officialAddress, (Map<String, Object>) organizationParams.get("officialAddress"));
            organization.setOfficialAddress(officialAddress);
        }

        if (organizationParams.get("postalAddressId") != null) {
            Long postalAddressId = ((Number) organizationParams.get("postalAddressId")).longValue();
            organization.setPostalAddress(addressRepository.getReferenceById(postalAddressId));
        } else if (organizationParams.get("postalAddress") != null) {
            Address postalAddress = new Address();
            setAddressParamsFromMap(postalAddress, (Map<String, Object>) organizationParams.get("postalAddress"));
            organization.setPostalAddress(postalAddress);
        }
    }

    public void setPersonParamsFromMap(Person person, Map<String, Object> personParams) {
        person.setName((String) personParams.get("name"));
        person.setEyeColor(Color.valueOf((String) personParams.get("eyeColor")));
        person.setHairColor(Color.valueOf((String) personParams.get("hairColor")));
        person.setHeight(((Number) personParams.get("height")).longValue());
        person.setNationality(Country.valueOf((String) personParams.get("nationality")));

        if (personParams.get("locationId") != null) {
            Long locationId = ((Number) personParams.get("locationId")).longValue();
            person.setLocation(locationRepository.getReferenceById(locationId));
        } else if (personParams.get("location") != null) {
            Location location = new Location();
            setLocationParamsFromMap(location, (Map<String, Object>) personParams.get("location"));
            person.setLocation(location);
        }
    }

    public void setProductParamsFromMap(Product product, Map<String, Object> productParams) {
        product.setName((String) productParams.get("name"));

        if (productParams.get("coordinatesId") != null) {
            Long coordinateId = ((Number) productParams.get("coordinatesId")).longValue();
            product.setCoordinates(coordinatesRepository.getReferenceById(coordinateId));
        }
        else {
            Coordinates coordinates = new Coordinates();
            setCoordinatesParamsFromMap(coordinates, (Map<String, Object>) productParams.get("coordinates"));
            product.setCoordinates(coordinates);
        }

        product.setPrice(((Number) productParams.get("price")).longValue());
        product.setCreationDate(ZonedDateTime.now());
        product.setUnitOfMeasure(UnitOfMeasure.valueOf((String) productParams.get("unitOfMeasure")));

        if (productParams.get("manufacturerId") != null) {
            Long manufacturerId = ((Number) productParams.get("manufacturerId")).longValue();
            product.setManufacturer(organizationRepository.getReferenceById(manufacturerId));
        }
        else {
            Organization organization = new Organization();
            setOrganizationParamsFromMap(organization, (Map<String, Object>) productParams.get("manufacturer"));
            product.setManufacturer(organization);
        }

        if (productParams.get("ownerId") != null) {
            Long ownerId = ((Number) productParams.get("ownerId")).longValue();
            product.setOwner(personRepository.getReferenceById(ownerId));
        }
        else if (productParams.get("owner") != null) {
            Person person = new Person();
            setPersonParamsFromMap(person, (Map<String, Object>) productParams.get("owner"));
            product.setOwner(person);
        }
        else {
            product.setOwner(null);
        }

        product.setManufactureCost(((Number) productParams.get("manufactureCost")).floatValue());
        product.setRating((Integer) productParams.get("rating"));
        product.setPartNumber((String) productParams.get("partNumber"));
    }

}
