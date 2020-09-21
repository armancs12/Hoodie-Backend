package dev.serhats.hoodie.dto;

import lombok.Data;

@Data
public class UserRegister {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String profilePictureUrl;
    private Geolocation geolocation;
}
