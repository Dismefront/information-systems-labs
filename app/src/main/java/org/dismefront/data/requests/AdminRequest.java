package org.dismefront.data.requests;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.dismefront.data.shared.RequestStatus;

@Entity
@Table(name = "IS1_ADMIN_REQUEST")
@Data
public class AdminRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    RequestStatus status;

    @Column
    private Long userId;

}
