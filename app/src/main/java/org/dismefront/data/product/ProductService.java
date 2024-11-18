package org.dismefront.data.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dismefront.api.product.ProductRequest;
import org.dismefront.data.coordinates.CoordinatesRepository;
import org.dismefront.data.event.Event;
import org.dismefront.data.event.EventName;
import org.dismefront.data.event.EventRepository;
import org.dismefront.data.organization.OrganizationRepository;
import org.dismefront.data.person.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CoordinatesRepository coordinatesRepository;
    private final PersonRepository personRepository;
    private final OrganizationRepository organizationRepository;
    private final EventRepository eventRepository;

    @Transactional
    public Product saveProduct(ProductRequest productRequest, String username) {

        Product product = new Product();
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

        Product savedProduct = productRepository.save(product);
        productRepository.flush();
        Event event = new Event(EventName.PRODUCT_CREATED, username, savedProduct.getId(), new Timestamp(new Date().getTime()));
        eventRepository.save(event);
        return savedProduct;
    }

    public Page<ProductManaged> getProductList(int page, int size, String username, boolean isAdmin) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findProductsWithEditableFlag(username, EventName.PRODUCT_CREATED, isAdmin, pageable);
    }

}
