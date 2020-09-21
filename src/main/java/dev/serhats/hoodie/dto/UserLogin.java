package dev.serhats.hoodie.dto;

import lombok.Data;

@Data
public class UserLogin {
    private String username;
    private String password;
    private boolean willRemember;
}
