package dev.serhats.hoodie.domain.repository;

import dev.serhats.hoodie.domain.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<Role> {
    Optional<Role> findByName(String name);
}
