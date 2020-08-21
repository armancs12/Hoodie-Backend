package dev.serhats.hoodie.domain.repository;

import dev.serhats.hoodie.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByUsername(String username);
}
