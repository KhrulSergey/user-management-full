package com.khsa.usermanagement.service;

import com.khsa.usermanagement.domain.model.User;
import com.khsa.usermanagement.repository.UserRepository;
import com.khsa.usermanagement.repository.mongo.AccountRepository;
import com.khsa.usermanagement.repository.mongo.CustomerRepository;
import com.khsa.usermanagement.repository.mongo.TransactionRepository;
import com.khsa.usermanagement.util.UserComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


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
            LOG.info("save user: {}", user);
            User userSaved = userRepository.saveAndFlush(user);
            return userSaved;
        } else {
            LOG.warn("user: {} have constraint violations: {}", user, violations);
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
        LOG.info("getting user: {}", user);
        return user;
    }

    /**
     * Поиск пользователя из БД gj Kjubye
     *
     * @param login Логин пользователя для поиска
     * @return Данные о пользователе или null - если пользователь не найден
     */
    @Override
    public User findByLoginAndPass(String login, String password) {
        User user = userRepository.findByLoginAndPassword(login, password).orElse(null);
        LOG.info("find user by login and password: {}", user);
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
                LOG.info("user: {} is edited", user);
                updatedUser = userRepository.saveAndFlush(user);
            } else {
                LOG.warn("edited user: %o have constraint violations: {}", user, violations);
                throw new ConstraintViolationException(violations);
            }
        } else {
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            LOG.warn("user: {} is not exist");
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


    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        LOG.info("find user by login: {}", user);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    /**
     * Получение списка уникальных пользователей из БД
     * Тест разных способов поиска distinct
     *
     * @return Список имеющихся пользователей из БД
     */
    private List<User> listDistinct(Pageable pageable) {
        LOG.info("getting users: ");

        Page<User> userPage= userRepository.findAllByOrderByIdAsc(pageable);

        List<User> distinctUsers = userPage.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(c -> Arrays.asList(c.getName(), c.getUsername()),
                                Function.identity(), (a, b) -> a, LinkedHashMap::new),
                        m -> new ArrayList<>(m.values())));

        List<User> userList = userPage.get().sorted(new UserComparator()).collect(Collectors.toList());
        HashSet<Object> seen=new HashSet<>();
        userList.removeIf(user -> !seen.add(Arrays.asList(user.getName(), user.getUsername())));
        return distinctUsers;
    }
}
