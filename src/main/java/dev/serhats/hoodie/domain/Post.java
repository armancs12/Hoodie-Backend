package dev.serhats.hoodie.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Post extends BaseEntity {
    @Lob
    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;
}
