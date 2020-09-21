package dev.serhats.hoodie.service;

import dev.serhats.hoodie.dto.UserLogin;
import dev.serhats.hoodie.dto.UserRegister;
import dev.serhats.hoodie.dto.UserView;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    UserView registerUser(UserRegister userRegister);

    String getAccessToken(UserLogin userLogin);

    UserView getUserById(long userId);
}
