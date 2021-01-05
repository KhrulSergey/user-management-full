package com.khsa.usermanagement.repository;


import com.khsa.usermanagement.domain.model.Role;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * Интерфейс доступа к данным сущности "Роль" из БД
 */
@CacheConfig(cacheNames = "roleCache")
public interface RoleRepository extends JpaRepository<Role, Long>,
            JpaSpecificationExecutor<Role> {

    @Cacheable
    @Override
    Optional<Role> findById(Long aLong);
}
