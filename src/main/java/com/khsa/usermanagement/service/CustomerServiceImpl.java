package com.khsa.usermanagement.service;

import com.khsa.usermanagement.domain.model.analytic.Customer;
import com.khsa.usermanagement.repository.UserRepository;
import com.khsa.usermanagement.repository.mongo.AccountRepository;
import com.khsa.usermanagement.repository.mongo.CustomerRepository;
import com.khsa.usermanagement.repository.mongo.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private TransactionRepository transactionRepository;
    private Validator validator;

    @Autowired
    public CustomerServiceImpl(UserRepository userRepository,
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
     * Добавление нового покупателя в БД.
     *
     * @param customer Добавляемый покупатель
     * @return добавленный покупатель
     */
    @Override
    public Customer add(Customer customer) {
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        if (violations.isEmpty()) {
            LOG.info("save customer: %s", customer);
            Customer savedCustomer = customerRepository.save(customer);
            return savedCustomer;
        } else {
            LOG.warn("customer: %s have constraint violations: %s", customer, violations);
            throw new ConstraintViolationException(violations);
        }
    }

    /**
     * Получение конкретного покупателя из БД
     *
     * @param id Идентификатор покупателя для поиска
     * @return Данные о аккаунте или null - если покупатель не найден
     */
    @Override
    public Customer get(String id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        LOG.info("getting customer: %s", customer);
        return customer;
    }

    /**
     * Получение конкретного покупателя из БД
     *
     * @param username Имя покупателя для поиска
     * @return Данные о аккаунте или null - если покупатель не найден
     */
    @Override
    public Customer getByUserName(String username) {
        Customer customer = customerRepository.findByUsername(username).orElse(null);
        LOG.info("getting customer: %s", customer);
        return customer;
    }

    /**
     * Редактирование существующего покупателя в БД.
     *
     * @param customer Обновленные данные для внесения в БД
     * @return отредактированный покупатель
     */
    @Override
    public Customer edit(Customer customer) {
        Customer updatedCustomer;
        if (customerRepository.existsById(customer.getId())) {
            Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
            if (violations.isEmpty()) {
                LOG.info("customer: %s is edited", customer);
                updatedCustomer = customerRepository.save(customer);
            } else {
                LOG.warn("edited customer: %o have constraint violations: %s", customer, violations);
                throw new ConstraintViolationException(violations);
            }
        } else {
            Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
            LOG.warn("customer: %s is not exist");
            throw new ConstraintViolationException(violations);
        }
        return updatedCustomer;
    }

    /**
     * Удаление покупателя в БД
     *
     * @param id Идентификатор покупателя для удаления
     */
    @Override
    public void delete(String id) {
        customerRepository.deleteById(id);
        LOG.info("delete customer by id: %d", id);
    }
}
