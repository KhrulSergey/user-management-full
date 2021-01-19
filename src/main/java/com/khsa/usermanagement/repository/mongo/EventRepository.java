package com.khsa.usermanagement.repository.mongo;

import com.khsa.usermanagement.domain.model.analytic.Account;
import com.khsa.usermanagement.domain.model.analytic.Event;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@CacheConfig(cacheNames = "eventsCache")
@Repository
public interface EventRepository extends MongoRepository<Event, String> {

    @Cacheable
    Page<Event> findAllByOrderByIdAsc(Pageable pageable);

}
