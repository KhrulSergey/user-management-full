package com.khsa.usermanagement.repository.mongo;

import com.khsa.usermanagement.domain.model.analytic.Account;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@CacheConfig(cacheNames = "customersCache")
@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

    @Cacheable
    Page<Account> findAllByOrderByIdAsc(Pageable pageable);

    @Cacheable
    Optional<Account> findByAccountId(Long accountId);

}
