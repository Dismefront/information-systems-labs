package org.dismefront.data.person;

import java.util.Optional;

import org.dismefront.data.event.EventName;
import org.dismefront.data.product.ProductManaged;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
  Optional<Person> findByName(String name);

  @Query("SELECT new org.dismefront.data.person.PersonManaged(p.id, p.name, p.eyeColor, p.hairColor, p.location, p.height, p.nationality, " +
          "CASE WHEN e.actor = :username OR :isAdmin = true THEN true ELSE false END) " +
          "FROM Person p LEFT JOIN Event e ON e.entityId = p.id AND e.name = :eventName")
  Page<PersonManaged> findPersonsWithEditableFlag(@Param("username") String username, @Param("eventName") EventName eventName, @Param("isAdmin") boolean isAdmin, Pageable pageable);
}
