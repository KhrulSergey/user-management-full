package com.khsa.usermanagement.repository.mongo;

import com.khsa.usermanagement.domain.model.analytic.Customer;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@CacheConfig(cacheNames = "customersCache")
@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

    @Cacheable
    Optional<Customer> findByUsername(String username);
}
