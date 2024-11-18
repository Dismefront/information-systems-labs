package org.dismefront.data.address;

import org.dismefront.data.event.EventName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT new org.dismefront.data.address.AddressManaged(a.id, a.zipCode, a.town, " +
            "CASE WHEN e.actor = :username OR :isAdmin = true THEN true ELSE false END) " +
            "FROM Address a LEFT JOIN Event e ON e.entity_id = a.id AND e.name = :eventName")
    Page<AddressManaged> findAddressesWithEditableFlag(@Param("username") String username, @Param("eventName") EventName eventName, @Param("isAdmin") boolean isAdmin, Pageable pageable);
}
