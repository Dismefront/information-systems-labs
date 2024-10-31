package org.dismefront.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface EntityRepository extends JpaRepository<Entity, Long> {
}
