package dev.serhats.hoodie.dto;

import dev.serhats.hoodie.domain.Post;
import lombok.Data;

@Data
public class PostView {
    private long id;
    private String text;
    private UserView user;
    private AddressView address;

    public PostView() {
    }

    public PostView(Post post) {
        this.id = post.getId();
        this.text = post.getText();
        this.user = new UserView(post.getUser());
        this.address = new AddressView(post.getAddress());
    }
}
