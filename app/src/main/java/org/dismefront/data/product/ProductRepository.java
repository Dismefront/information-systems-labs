package org.dismefront.data.product;

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
            "FROM Product p LEFT JOIN Event e ON e.entity_id = p.id AND e.name = :eventName")
    Page<ProductManaged> findProductsWithEditableFlag(@Param("username") String username, @Param("eventName") EventName eventName, @Param("isAdmin") boolean isAdmin, Pageable pageable);
}
