package dev.serhats.hoodie.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;

@MappedSuperclass
@Data
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, updatable = false)
    private Time createdTime;

    @Column
    private Time updatedTime;
}
