package org.dismefront.data.location;

import org.dismefront.data.event.EventName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("SELECT new org.dismefront.data.location.LocationManaged(l.id, l.x, l.y, l.z, " +
            "CASE WHEN e.actor = :username OR :isAdmin = true THEN true ELSE false END) " +
            "FROM Location l LEFT JOIN Event e ON e.entity_id = l.id AND e.name = :eventName")
    Page<LocationManaged> findLocationsWithEditableFlag(@Param("username") String username, @Param("eventName") EventName eventName, @Param("isAdmin") boolean isAdmin, Pageable pageable);
}
