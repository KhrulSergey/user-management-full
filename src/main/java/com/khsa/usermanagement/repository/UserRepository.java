package com.khsa.usermanagement.repository;


import com.khsa.usermanagement.domain.model.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Интерфейс доступа к данным сущности "Пользователи" из БД
 */
@CacheConfig(cacheNames = "usersCache")
public interface UserRepository extends JpaRepository<User, Long>,
        JpaSpecificationExecutor<User> {


    @Cacheable
    Page<User> findAllByOrderByIdAsc(Pageable pageable);

    @Cacheable
    @Override
    Optional<User> findById(Long id);

    @Cacheable
    @Override
    <S extends User> S saveAndFlush(S entity);

    @Override
    void deleteById(Long id);

    @Cacheable
    @Query("select u from User u where u.username=:username AND u.password=:password")
    Optional<User> findByLoginAndPassword(@Param("username") String username, @Param("password") String password);

}

