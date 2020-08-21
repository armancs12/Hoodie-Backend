package dev.serhats.hoodie.domain.repository;

import dev.serhats.hoodie.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<Entity extends BaseEntity> extends JpaRepository<Entity, Long> {
}
