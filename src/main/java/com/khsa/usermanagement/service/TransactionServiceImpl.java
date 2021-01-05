package com.khsa.usermanagement.service;

import com.khsa.usermanagement.domain.model.analytic.Account;
import com.khsa.usermanagement.domain.model.analytic.Transaction;
import com.khsa.usermanagement.repository.UserRepository;
import com.khsa.usermanagement.repository.mongo.AccountRepository;
import com.khsa.usermanagement.repository.mongo.CustomerRepository;
import com.khsa.usermanagement.repository.mongo.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Реализация сервиса-прослойки между ВЕБ-контроллером и сервисом доступа к данным
 */
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private TransactionRepository transactionRepository;
    private Validator validator;

    @Autowired
    public TransactionServiceImpl(UserRepository userRepository,
                                  AccountRepository accountRepository,
                                  CustomerRepository customerRepository,
                                  TransactionRepository transactionRepository,
                                  Validator validator) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
        this.validator = validator;
    }


    /**
     * Получение списка транзакций из БД для указанных аккаунтов
     *
     * @return Список имеющихся транзакций из БД
     */
    @Override
    public Page<Transaction> list(Pageable pageable, List<Account> account) {
        LOG.info("getting transactions: ");
        return transactionRepository.findAllByAccountIdIn(
                account.stream().map(Account::getAccountId).collect(Collectors.toList()),
                pageable);
    }

    /**
     * Добавление новой транзакции в БД.
     *
     * @param transaction Добавляемая транзакция
     * @return добавленная транзакция
     */
    @Override
    public Transaction add(Transaction transaction) {
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        if (violations.isEmpty()) {
            LOG.info("save transaction: %s", transaction);
            Transaction savedTransaction = transactionRepository.save(transaction);
            return savedTransaction;
        } else {
            LOG.warn("transaction: %s have constraint violations: %s", transaction, violations);
            throw new ConstraintViolationException(violations);
        }
    }

    /**
     * Получение конкретной транзакции из БД
     *
     * @param id Идентификатор аккаунта для поиска
     * @return Данные об аккаунте или null - если аккаунт не найден
     */
    @Override
    public Transaction get(String id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        LOG.info("getting transaction: %s", transaction);
        return transaction;
    }

    /**
     * Редактирование существующего пользователя в БД.
     *
     * @param transaction Обновленные данные для внесения в БД
     * @return отредактированный аккаунт
     */
    @Override
    public Transaction edit(Transaction transaction) {
        Transaction updatedTransaction;
        if (transactionRepository.existsById(transaction.getId())) {
            Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
            if (violations.isEmpty()) {
                LOG.info("transaction: %s is edited", transaction);
                updatedTransaction = transactionRepository.save(transaction);
            } else {
                LOG.warn("edited transaction: %o have constraint violations: %s", transaction, violations);
                throw new ConstraintViolationException(violations);
            }
        } else {
            Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
            LOG.warn("transaction: %s is not exist");
            throw new ConstraintViolationException(violations);
        }
        return updatedTransaction;
    }

    /**
     * Удаление аккаунта в БД
     *
     * @param id Идентификатор аккаунта для удаления
     */
    @Override
    public void delete(String id) {
        transactionRepository.deleteById(id);
        LOG.info("delete transaction by id: %d", id);
    }
}
