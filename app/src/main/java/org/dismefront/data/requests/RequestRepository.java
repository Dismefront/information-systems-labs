package org.dismefront.data.requests;

import org.dismefront.data.shared.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<AdminRequest, Long> {
    List<AdminRequest> findAllByStatus(RequestStatus status);
    List<AdminRequest> findAllByUserIdAndStatus(long userId, RequestStatus status);
}
