package com.khsa.usermanagement.repository.mongo;

import com.khsa.usermanagement.domain.model.analytic.Account;
import com.khsa.usermanagement.domain.model.analytic.Transaction;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@CacheConfig(cacheNames = "transactionsCache")
@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

//    @Cacheable
//    Page<Transaction> findAllByAccountOrderByIdAsc(Account account, Pageable pageable);

    @Cacheable
    Page<Transaction> findAllByAccountIdIn(List<Long> account, Pageable pageable);

    @Cacheable
    List<Transaction> findAllByAccountIdOrderByIdAsc(Long account);
}
