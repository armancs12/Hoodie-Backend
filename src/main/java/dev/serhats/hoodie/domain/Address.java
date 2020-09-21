package dev.serhats.hoodie.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Address extends BaseEntity {
    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String county;

    @Column(nullable = false)
    private String neighbourhood;

    @OneToMany(mappedBy = "currentAddress")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "address")
    private List<Post> posts = new ArrayList<>();
}
