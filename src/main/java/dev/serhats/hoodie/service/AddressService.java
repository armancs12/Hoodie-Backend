package dev.serhats.hoodie.service;

import dev.serhats.hoodie.domain.Address;
import dev.serhats.hoodie.domain.Post;
import dev.serhats.hoodie.domain.User;
import dev.serhats.hoodie.dto.Geolocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressService {
    Address getFromGeolocation(Geolocation geolocation);

    Page<User> getUsers(long addressId, Pageable pageable);

    Page<Post> getPosts(long addressId, Pageable pageable);
}
