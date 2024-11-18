package org.dismefront.data.organization;

import org.dismefront.data.event.EventName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    @Query("SELECT new org.dismefront.data.organization.OrganizationManaged(o.id, o.name, o.officialAddress, o.annualTurnover, o.employeesCount, o.fullName, o.postalAddress, " +
            "CASE WHEN e.actor = :username OR :isAdmin = true THEN true ELSE false END) " +
            "FROM Organization o LEFT JOIN Event e ON e.entity_id = o.id AND e.name = :eventName")
    Page<OrganizationManaged> findOrganizationsWithEditableFlag(@Param("username") String username, @Param("eventName") EventName eventName, @Param("isAdmin") boolean isAdmin, Pageable pageable);
}
