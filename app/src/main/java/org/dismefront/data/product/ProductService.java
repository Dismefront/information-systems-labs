package org.dismefront.data.product;

import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dismefront.api.product.ProductController;
import org.dismefront.api.product.ProductRequest;
import org.dismefront.api.product.ProductsByManufacturersCountResponse;
import org.dismefront.data.address.Address;
import org.dismefront.data.coordinates.Coordinates;
import org.dismefront.data.coordinates.CoordinatesRepository;
import org.dismefront.data.event.Event;
import org.dismefront.data.event.EventName;
import org.dismefront.data.event.EventRepository;
import org.dismefront.data.importHistory.ImportHistory;
import org.dismefront.data.importHistory.ImportHistoryRepository;
import org.dismefront.data.importHistory.ImportStatus;
import org.dismefront.data.location.Location;
import org.dismefront.data.organization.Organization;
import org.dismefront.data.organization.OrganizationRepository;
import org.dismefront.data.person.Person;
import org.dismefront.data.person.PersonRepository;
import org.dismefront.data.shared.UnitOfMeasure;
import org.dismefront.data.user.User;
import org.dismefront.data.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CoordinatesRepository coordinatesRepository;
    private final PersonRepository personRepository;
    private final OrganizationRepository organizationRepository;
    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;
    private final ImportHistoryRepository importHistoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public Product updateProduct(ProductRequest productRequest, String username, Long product_id) {
        Product product = productRepository.getReferenceById(product_id);
        productParams(productRequest, product);

        Event event = new Event(EventName.PRODUCT_UPDATED, username, product.getId(), new Timestamp(new Date().getTime()));
        eventRepository.save(event);
        return productRepository.save(product);
    }

    private void logSaveEventsOfNestedObjects(Product product, String username) {
        Organization organization = product.getManufacturer();
        Coordinates coordinates = product.getCoordinates();
        Person person = product.getOwner();
        Location location = person.getLocation();
        Address officialAddress = organization.getOfficialAddress();
        Address postalAddress = organization.getPostalAddress();
        Location town = officialAddress.getTown();
        Location postalTown = postalAddress.getTown();

        boolean orgHasId = organization.getId() != null;
        boolean coordsHasId = coordinates.getId() != null;
        boolean personHasId = person.getId() != null;
        boolean locationHasId = location.getId() != null;
        boolean officialAddressHasId = officialAddress.getId() != null;
        boolean postalAddressHasId = postalAddress.getId() != null;
        boolean townHasId = town.getId() != null;
        boolean postalTownHasId = postalTown.getId() != null;

        productRepository.save(product);
        productRepository.flush();

        if (!orgHasId) {
            Event event = new Event(EventName.ORGANIZATION_CREATED, username, organization.getId(), new Timestamp(new Date().getTime()));
            eventRepository.save(event);
        }
        if (!coordsHasId) {
            Event event = new Event(EventName.COORDINATE_CREATED, username, coordinates.getId(), new Timestamp(new Date().getTime()));
            eventRepository.save(event);
        }
        if (!personHasId) {
            Event event = new Event(EventName.PERSON_CREATED, username, person.getId(), new Timestamp(new Date().getTime()));
            eventRepository.save(event);
        }
        if (!locationHasId) {
            Event event = new Event(EventName.LOCATION_CREATED, username, location.getId(), new Timestamp(new Date().getTime()));
            eventRepository.save(event);
        }
        if (!officialAddressHasId) {
            Event event = new Event(EventName.ADDRESS_CREATED, username, officialAddress.getId(), new Timestamp(new Date().getTime()));
            eventRepository.save(event);
        }
        if (!postalAddressHasId) {
            Event event = new Event(EventName.ADDRESS_CREATED, username, postalAddress.getId(), new Timestamp(new Date().getTime()));
            eventRepository.save(event);
        }
        if (!townHasId) {
            Event event = new Event(EventName.LOCATION_CREATED, username, town.getId(), new Timestamp(new Date().getTime()));
            eventRepository.save(event);
        }
        if (!postalTownHasId) {
            Event event = new Event(EventName.LOCATION_CREATED, username, postalTown.getId(), new Timestamp(new Date().getTime()));
            eventRepository.save(event);
        }

        Event productEvent = new Event(EventName.PRODUCT_CREATED, username, product.getId(), new Timestamp(new Date().getTime()));
        eventRepository.save(productEvent);
    }

    public ImportHistory saveToImportHistory(String username, Long objectsCount) {
        ImportHistory importHistory = new ImportHistory();
        Optional<User> user = userRepository.findByUsername(username);
        user.ifPresent(importHistory::setUser);
        importHistory.setStatus(ImportStatus.RESOLVED);
        importHistory.setTimestamp(new Timestamp(new Date().getTime()));
        importHistory.setObjectCount(objectsCount);
        return importHistory;
    }

    @org.springframework.transaction.annotation.Transactional
    public void uploadProductsFromFile(ArrayList<Map<String, Object>> products, String username, ImportHistory importHistory) {
        products.forEach(product -> {
            Product savedProduct = new Product();
            objectMapper.setProductParamsFromMap(savedProduct, product);
            logSaveEventsOfNestedObjects(savedProduct, username);
        });
        System.out.println("Import to history resolved successfully");
        importHistory.setStatus(ImportStatus.RESOLVED);
    }

    @org.springframework.transaction.annotation.Transactional
    public Product saveProduct(ProductRequest productRequest, String username) {

        Product product = new Product();
        productParams(productRequest, product);

        Product savedProduct = productRepository.save(product);
        productRepository.flush();
        Event event = new Event(EventName.PRODUCT_CREATED, username, savedProduct.getId(), new Timestamp(new Date().getTime()));
        eventRepository.save(event);
        return savedProduct;
    }

    @org.springframework.transaction.annotation.Transactional
    public void deleteProduct(Long id, String username) {
        productRepository.deleteById(id);
        Event event = new Event(EventName.PRODUCT_DELETED, username, id, new Timestamp(new Date().getTime()));
        eventRepository.save(event);
    }

    private void productParams(ProductRequest productRequest, Product product) {
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setCoordinates(coordinatesRepository.getReferenceById(productRequest.getCoordinatesId()));
        product.setCreationDate(ZonedDateTime.now());
        product.setUnitOfMeasure(productRequest.getUnitOfMeasure());
        product.setManufacturer(organizationRepository.getReferenceById(productRequest.getManufacturerId()));
        if (productRequest.getOwnerId().isPresent()) {
            product.setOwner(personRepository.getReferenceById(productRequest.getOwnerId().get()));
        }
        product.setManufactureCost(productRequest.getManufactureCost());
        product.setRating(productRequest.getRating());
        product.setPartNumber(productRequest.getPartNumber());
    }

    public Page<ProductManaged> getProductList(int page, int size, String username, boolean isAdmin, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findProductsWithEditableFlag(username, EventName.PRODUCT_CREATED, isAdmin, pageable);
    }

    public List<ProductsByManufacturersCountResponse> getProductsGroupedByManufacturer() {
        var dbRes = productRepository.countProductsByManufacturer();
        List<ProductsByManufacturersCountResponse> products = new ArrayList<>();
        dbRes.forEach((product) -> {
            products.add(new ProductsByManufacturersCountResponse(organizationRepository.getReferenceById((Long) product[0]), (Long) product[1]));
        });
        return products;
    }

    public Long countObjectsByRating(int rating) {
        return productRepository.countProductsByRating(rating);
    }

    public Long countProductsByPartNumber(String partNumber) {
        return productRepository.countProductsByPartNumber(partNumber);
    }

    public List<Product> getProductsByManufacturer(int manufacturerId) {
        var dbRes = productRepository.getProductsByManufacturer(manufacturerId);
        List<Product> products = new ArrayList<>();
        dbRes.forEach((product) -> {
            products.add(new Product(Long.valueOf((Integer) product[0]), (String) product[1],
                    coordinatesRepository.getReferenceById((Long) product[7]), ZonedDateTime.now(),
                    UnitOfMeasure.valueOf((String) product[6]), organizationRepository.getReferenceById((Long) product[8]), (long) product[3], (float)((double) product[4]),
                    (int) product[5], (String) product[2], null));
        });
        return products;
    }

    public void reducePricesByPercent(double percent) {
        productRepository.reducePricesByPercentage(percent);
    }

}
