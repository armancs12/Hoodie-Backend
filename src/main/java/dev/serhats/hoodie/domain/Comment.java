package dev.serhats.hoodie.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Comment extends BaseEntity {
    @Lob
    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
