package org.dismefront.data.product;

import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dismefront.api.product.ProductRequest;
import org.dismefront.api.product.ProductsByManufacturersCountResponse;
import org.dismefront.data.coordinates.CoordinatesRepository;
import org.dismefront.data.event.Event;
import org.dismefront.data.event.EventName;
import org.dismefront.data.event.EventRepository;
import org.dismefront.data.organization.OrganizationRepository;
import org.dismefront.data.person.PersonRepository;
import org.dismefront.data.shared.UnitOfMeasure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CoordinatesRepository coordinatesRepository;
    private final PersonRepository personRepository;
    private final OrganizationRepository organizationRepository;
    private final EventRepository eventRepository;

    @Transactional
    public Product updateProduct(ProductRequest productRequest, String username, Long product_id) {
        Product product = productRepository.getReferenceById(product_id);
        productParams(productRequest, product);

        Event event = new Event(EventName.PRODUCT_UPDATED, username, product.getId(), new Timestamp(new Date().getTime()));
        eventRepository.save(event);
        return productRepository.save(product);
    }

    @Transactional
    public Product saveProduct(ProductRequest productRequest, String username) {

        Product product = new Product();
        productParams(productRequest, product);

        Product savedProduct = productRepository.save(product);
        productRepository.flush();
        Event event = new Event(EventName.PRODUCT_CREATED, username, savedProduct.getId(), new Timestamp(new Date().getTime()));
        eventRepository.save(event);
        return savedProduct;
    }

    @Transactional
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
