package org.dismefront.data.event;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="IS1_EVENT")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private EventName name;

    @Column
    private String actor;

    @Column
    private Long entity_id;

    @Column
    private Timestamp timestamp;

    public Event(EventName name, String actor, Long entity_id, Timestamp timestamp) {
        this.name = name;
        this.actor = actor;
        this.entity_id = entity_id;
        this.timestamp = timestamp;
    }

    public Event() {

    }
}
