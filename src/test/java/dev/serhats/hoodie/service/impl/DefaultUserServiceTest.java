package dev.serhats.hoodie.service.impl;

import dev.serhats.hoodie.domain.Address;
import dev.serhats.hoodie.domain.User;
import dev.serhats.hoodie.domain.repository.RoleRepository;
import dev.serhats.hoodie.domain.repository.UserRepository;
import dev.serhats.hoodie.dto.Geolocation;
import dev.serhats.hoodie.dto.UserRegister;
import dev.serhats.hoodie.dto.UserView;
import dev.serhats.hoodie.exception.EntityNotValidException;
import dev.serhats.hoodie.exception.NoEntityFoundException;
import dev.serhats.hoodie.service.AddressService;
import dev.serhats.hoodie.service.JWTService;
import dev.serhats.hoodie.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultUserServiceTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final RoleRepository roleRepository = mock(RoleRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
    private final AddressService addressService = mock(AddressService.class);
    private final JWTService jwtService = mock(JWTService.class);

    private final UserService userService = new DefaultUserService(
            userRepository, roleRepository, passwordEncoder, authenticationManager, addressService, jwtService
    );

    @Test
    void loadUserByUsernameWithValidUsername() {
        //Arrange
        User user = new User();
        user.setUsername("adam");
        user.setPassword("password");
        when(userRepository.findByUsername("adam")).thenReturn(Optional.of(user));

        //Act
        UserDetails userDetails = userService.loadUserByUsername("adam");

        //Assert
        assertEquals("adam", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
    }

    @Test
    void loadUserByUsernameWithNotValidUsername() {
        //Arrange
        when(userRepository.findByUsername("adam")).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("adam");
        });
    }

    @Test
    void registerUserWithValidDto() {
        //Arrange
        UserRegister userRegister = new UserRegister();
        userRegister.setFirstName("Adam");
        userRegister.setLastName("Smith");
        userRegister.setUsername("adam");
        userRegister.setPassword("Password1.");
        userRegister.setGeolocation(new Geolocation(41, 28));
        when(addressService.getFromGeolocation(userRegister.getGeolocation())).thenReturn(new Address());

        //Act & Arrange
        assertDoesNotThrow(() -> {
            userService.registerUser(userRegister);
        });
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void registerUserWithNotValidFirstName() {
        //Arrange
        UserRegister userRegister = new UserRegister();
        userRegister.setFirstName("");
        userRegister.setLastName("Smith");
        userRegister.setUsername("adam");
        userRegister.setPassword("Password1.");
        userRegister.setGeolocation(new Geolocation(41, 28));
        when(addressService.getFromGeolocation(userRegister.getGeolocation())).thenReturn(new Address());

        //Act & Arrange
        Exception exception = assertThrows(EntityNotValidException.class, () -> {
            userService.registerUser(userRegister);
        });
        assertEquals("First Name field of User entity is not valid!", exception.getMessage());
    }

    @Test
    void registerUserWithNotValidLastName() {
        //Arrange
        UserRegister userRegister = new UserRegister();
        userRegister.setFirstName("Adam");
        userRegister.setLastName("");
        userRegister.setUsername("adam");
        userRegister.setPassword("Password1.");
        userRegister.setGeolocation(new Geolocation(41, 28));
        when(addressService.getFromGeolocation(userRegister.getGeolocation())).thenReturn(new Address());

        //Act & Arrange
        Exception exception = assertThrows(EntityNotValidException.class, () -> {
            userService.registerUser(userRegister);
        });
        assertEquals("Last Name field of User entity is bot valid!", exception.getMessage());
    }

    @Test
    void registerUserWithNotValidUsername() {
        //Arrange
        UserRegister userRegister = new UserRegister();
        userRegister.setFirstName("Adam");
        userRegister.setLastName("Smith");
        userRegister.setUsername("a");
        userRegister.setPassword("Password.");
        userRegister.setGeolocation(new Geolocation(41, 28));
        when(addressService.getFromGeolocation(userRegister.getGeolocation())).thenReturn(new Address());

        //Act & Arrange
        Exception exception = assertThrows(EntityNotValidException.class, () -> {
            userService.registerUser(userRegister);
        });
        assertEquals("Username field of User entity is bot valid!", exception.getMessage());
    }

    @Test
    void registerUserWithNotValidPasswordMin8Size() {
        //Arrange
        UserRegister userRegister = new UserRegister();
        userRegister.setFirstName("Adam");
        userRegister.setLastName("Smith");
        userRegister.setUsername("a");
        userRegister.setPassword("Pass12.");
        userRegister.setGeolocation(new Geolocation(41, 28));
        when(addressService.getFromGeolocation(userRegister.getGeolocation())).thenReturn(new Address());

        //Act & Arrange
        Exception exception = assertThrows(EntityNotValidException.class, () -> {
            userService.registerUser(userRegister);
        });
        assertEquals("Password field of User entity is bot valid!", exception.getMessage());
    }

    @Test
    void registerUserWithNotValidPasswordNoUppercase() {
        //Arrange
        UserRegister userRegister = new UserRegister();
        userRegister.setFirstName("Adam");
        userRegister.setLastName("Smith");
        userRegister.setUsername("a");
        userRegister.setPassword("password1.");
        userRegister.setGeolocation(new Geolocation(41, 28));
        when(addressService.getFromGeolocation(userRegister.getGeolocation())).thenReturn(new Address());

        //Act & Arrange
        Exception exception = assertThrows(EntityNotValidException.class, () -> {
            userService.registerUser(userRegister);
        });
        assertEquals("Password field of User entity is bot valid!", exception.getMessage());
    }

    @Test
    void registerUserWithNotValidPasswordNoLowercase() {
        //Arrange
        UserRegister userRegister = new UserRegister();
        userRegister.setFirstName("Adam");
        userRegister.setLastName("Smith");
        userRegister.setUsername("a");
        userRegister.setPassword("PASSWORD1.");
        userRegister.setGeolocation(new Geolocation(41, 28));
        when(addressService.getFromGeolocation(userRegister.getGeolocation())).thenReturn(new Address());

        //Act & Arrange
        Exception exception = assertThrows(EntityNotValidException.class, () -> {
            userService.registerUser(userRegister);
        });
        assertEquals("Password field of User entity is bot valid!", exception.getMessage());
    }

    @Test
    void registerUserWithNotValidPasswordNoSpecialChar() {
        //Arrange
        UserRegister userRegister = new UserRegister();
        userRegister.setFirstName("Adam");
        userRegister.setLastName("Smith");
        userRegister.setUsername("a");
        userRegister.setPassword("Password");
        userRegister.setGeolocation(new Geolocation(41, 28));
        when(addressService.getFromGeolocation(userRegister.getGeolocation())).thenReturn(new Address());

        //Act & Assert
        Exception exception = assertThrows(EntityNotValidException.class, () -> {
            userService.registerUser(userRegister);
        });
        assertEquals("Password field of User entity is bot valid!", exception.getMessage());
    }

    @Test
    void getUserByIdWithValidId() {
        //Arrange
        User user = new User();
        user.setId(1);
        user.setFirstName("Adam");
        user.setLastName("Smith");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //Act
        UserView userView = userService.getUserById(1L);

        //Assert
        assertEquals("Adam", user.getFirstName());
        assertEquals("Smith", user.getLastName());
    }

    @Test
    void getUserByIdWithNotValidId() {
        //Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        //Act & Assert
        Exception exception = assertThrows(NoEntityFoundException.class, () -> {
            userService.getUserById(1);
        });
        assertEquals("No User entity found!", exception.getMessage());

    }
}
