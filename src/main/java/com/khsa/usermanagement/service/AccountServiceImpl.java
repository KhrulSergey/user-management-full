package com.khsa.usermanagement.service;

import com.khsa.usermanagement.domain.model.analytic.Account;
import com.khsa.usermanagement.domain.model.analytic.Customer;
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
import java.util.Set;

/**
 * Реализация сервиса-прослойки между ВЕБ-контроллером и сервисом доступа к данным
 */
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private TransactionRepository transactionRepository;
    private Validator validator;

    @Autowired
    public AccountServiceImpl(UserRepository userRepository,
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
     * Получение списка аккаунта из БД для указанного покупателя
     *
     * @return Список имеющихся аккаунтов из БД
     */
    @Override
    public Page<Account> list(Pageable pageable, Customer customer) {
        LOG.info("getting accounts: ");
        return accountRepository.findAllByOrderByIdAsc(pageable);
    }

    /**
     * Добавление нового аккаунта в БД.
     *
     * @param account Добавляемый аккаунт
     * @return добавленный аккаунт
     */
    @Override
    public Account add(Account account) {
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        if (violations.isEmpty()) {
            LOG.info("save account: %s", account);
            Account savedAccount = accountRepository.save(account);
            return savedAccount;
        } else {
            LOG.warn("account: %s have constraint violations: %s", account, violations);
            throw new ConstraintViolationException(violations);
        }
    }

    /**
     * Получение конкретного аккаунта из БД
     *
     * @param id Идентификатор аккаунта для поиска
     * @return Данные об аккаунте или null - если аккаунт не найден
     */
    @Override
    public Account get(String id) {
        Account account = accountRepository.findById(id).orElse(null);
        LOG.info("getting account: %s", account);
        return account;
    }

    /**
     * Получение конкретного аккаунта из БД по ГУИД
     *
     * @param guid Уник. идентификатор аккаунта для поиска
     * @return Данные об аккаунте или null - если аккаунт не найден
     */
    @Override
    public Account getByGuid(Long guid) {
        Account account = accountRepository.findByAccountId(guid).orElse(null);
        LOG.info("getting account by guid: %s", account);
        return account;
    }

    /**
     * Редактирование существующего пользователя в БД.
     *
     * @param account Обновленные данные для внесения в БД
     * @return отредактированный аккаунт
     */
    @Override
    public Account edit(Account account) {
        Account updatedAccount;
        if (accountRepository.existsById(account.getId())) {
            Set<ConstraintViolation<Account>> violations = validator.validate(account);
            if (violations.isEmpty()) {
                LOG.info("account: %s is edited", account);
                updatedAccount = accountRepository.save(account);
            } else {
                LOG.warn("edited account: %o have constraint violations: %s", account, violations);
                throw new ConstraintViolationException(violations);
            }
        } else {
            Set<ConstraintViolation<Account>> violations = validator.validate(account);
            LOG.warn("account: %s is not exist");
            throw new ConstraintViolationException(violations);
        }
        return updatedAccount;
    }

    /**
     * Удаление аккаунта в БД
     *
     * @param id Идентификатор аккаунта для удаления
     */
    @Override
    public void delete(String id) {
        accountRepository.deleteById(id);
        LOG.info("delete account by id: %d", id);
    }
}
