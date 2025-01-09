package org.dismefront.data.product;

import jakarta.persistence.Tuple;
import org.dismefront.data.event.Event;
import org.dismefront.data.event.EventName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT new org.dismefront.data.product.ProductManaged(p.id, p.name, p.coordinates, p.creationDate, p.unitOfMeasure, " +
            "p.manufacturer, p.price, p.manufactureCost, p.rating, p.partNumber, p.owner, " +
            "CASE WHEN e.actor = :username OR :isAdmin = true THEN true ELSE false END) " +
            "FROM Product p LEFT JOIN Event e ON e.entityId = p.id AND e.name = :eventName")
    Page<ProductManaged> findProductsWithEditableFlag(@Param("username") String username, @Param("eventName") EventName eventName, @Param("isAdmin") boolean isAdmin, Pageable pageable);

    @Query(value = "SELECT manufacturer_id, object_count FROM get_objects_count_by_manufacturer()", nativeQuery = true)
    List<Object[]> countProductsByManufacturer();

    @Query(value = "SELECT count_objects_by_rating(?1)", nativeQuery = true)
    Long countProductsByRating(int rating);

    @Query(value = "SELECT * FROM count_objects_by_partnumber(?1)", nativeQuery = true)
    Long countProductsByPartNumber(String partNumber);

    @Query(value = "SELECT * FROM get_products_by_manufacturer(?1)", nativeQuery = true)
    List<Object[]> getProductsByManufacturer(int partNumber);

    @Query(value = "SELECT reduce_prices_by_percentage(?1)", nativeQuery = true)
    void reducePricesByPercentage(double percentage);
}
