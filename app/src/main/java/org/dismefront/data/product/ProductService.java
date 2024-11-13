package org.dismefront.data.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dismefront.data.coordinates.CoordinatesRepository;
import org.dismefront.data.organization.OrganizationRepository;
import org.dismefront.data.person.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CoordinatesRepository coordinatesRepository;
    private final PersonRepository personRepository;
    private final OrganizationRepository organizationRepository;

    @Transactional
    public Product saveProduct(ProductRequest productRequest) {
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
        return productRepository.save(product);
    }

}
