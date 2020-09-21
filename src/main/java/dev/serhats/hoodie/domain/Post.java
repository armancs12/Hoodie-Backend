package dev.serhats.hoodie.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Post extends BaseEntity {
    @Lob
    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(name = "posts_users_liked",
            joinColumns = @JoinColumn(name = "post_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false))
    private List<User> usersLiked = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;
}
