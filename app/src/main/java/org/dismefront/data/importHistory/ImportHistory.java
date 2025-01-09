package org.dismefront.data.importHistory;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dismefront.data.user.User;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IS2_IMPORT_HISTORY")
@Data
public class ImportHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private ImportStatus status;

    @JoinColumn
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @Column
    private Long objectCount;

    @Column
    private Timestamp timestamp;
}
