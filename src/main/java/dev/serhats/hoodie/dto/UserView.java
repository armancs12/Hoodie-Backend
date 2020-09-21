package dev.serhats.hoodie.dto;

import dev.serhats.hoodie.domain.User;
import lombok.Data;

@Data
public class UserView {
    private long id;
    private String firstName;
    private String lastName;
    private String profilePictureUrl;
    private String username;
    private AddressView currentAddress;

    public UserView() {
    }

    public UserView(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.profilePictureUrl = user.getProfilePictureUrl();
        this.username = user.getUsername();
        this.currentAddress = new AddressView(user.getCurrentAddress());
    }
}
