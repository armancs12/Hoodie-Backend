package dev.serhats.hoodie.dto;

import lombok.Data;

@Data
public class CommentCreate {
    private long postId;
    private String text;
}
