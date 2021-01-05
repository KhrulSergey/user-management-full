package com.khsa.usermanagement.service;

import com.khsa.usermanagement.domain.model.User;
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
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private TransactionRepository transactionRepository;
    private Validator validator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
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
     * Получение списка пользователей из БД
     *
     * @return Список имеющихся пользователей из БД
     */
    @Override
    public Page<User> list(Pageable pageable) {
        LOG.info("getting users: ");
        return userRepository.findAllByOrderByIdAsc(pageable);
    }

    /**
     * Добавление нового пользователя в БД.
     *
     * @param user Добавляемый пользователь
     * @return добавленный пользователь
     */
    @Override
    public User add(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (violations.isEmpty()) {
            LOG.info("save user: %s", user);
            User userSaved = userRepository.saveAndFlush(user);
            return userSaved;
        } else {
            LOG.warn("user: %s have constraint violations: %s", user, violations);
            throw new ConstraintViolationException(violations);
        }
    }

    /**
     * Получение конкретного пользователя из БД
     *
     * @param id Идентификатор пользователя для поиска
     * @return Данные о пользователе или null - если пользователь не найден
     */
    @Override
    public User get(long id) {
        User user = userRepository.findById(id).orElse(null);
        LOG.info("getting user: %s", user);
        return user;
    }

    /**
     * Редактирование существующего пользователя в БД.
     *
     * @param user Обновленные данные для внесения в БД
     * @return отредактированный пользователь
     */
    @Override
    public User edit(User user) {
        User updatedUser;
        if (userRepository.existsById(user.getId())) {
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            if (violations.isEmpty()) {
                LOG.info("user: %s is edited", user);
                updatedUser = userRepository.saveAndFlush(user);
            } else {
                LOG.warn("edited user: %o have constraint violations: %s", user, violations);
                throw new ConstraintViolationException(violations);
            }
        } else {
            //TODO решить вопрос с валидацией
//            ConstraintViolation<User> violation = ConstraintViolationImpl.forBeanValidation(null,
//                    null, null, "User is not exist",
//                    User.class, user, null, user, null, null,
//                    null, null);
//         throw new ConstraintViolationException(Collections.singleton(violations));
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            LOG.warn("user: %s is not exist");
            throw new ConstraintViolationException(violations);
        }
        return updatedUser;
    }

    /**
     * Удаление пользователя в БД
     *
     * @param id Идентификатор пользователя для удаления
     */
    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
        LOG.info("delete user by id: %d", id);
    }


}
