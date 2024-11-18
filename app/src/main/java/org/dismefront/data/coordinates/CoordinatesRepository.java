package org.dismefront.data.coordinates;

import org.dismefront.data.event.EventName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
    @Query("SELECT new org.dismefront.data.coordinates.CoordinatesManaged(c.id, c.x, c.y, " +
            "CASE WHEN e.actor = :username OR :isAdmin = true THEN true ELSE false END) " +
            "FROM Coordinates c LEFT JOIN Event e ON e.entity_id = c.id AND e.name = :eventName")
    Page<CoordinatesManaged> findCoordinatesWithEditableFlag(@Param("username") String username, @Param("eventName") EventName eventName, @Param("isAdmin") boolean isAdmin, Pageable pageable);
}
