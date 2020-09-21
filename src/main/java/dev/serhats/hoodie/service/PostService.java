package dev.serhats.hoodie.service;

import dev.serhats.hoodie.dto.PostCreate;
import dev.serhats.hoodie.dto.PostView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostService {
    PostView create(PostCreate postCreate);

    PostView update(long postId, PostCreate postCreate);

    PostView delete(long postId);

    Page<PostView> getPostsByUser(long userId, Pageable pageable);
}
