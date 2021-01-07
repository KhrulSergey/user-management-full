package com.khsa.usermanagement.service;

import com.khsa.usermanagement.domain.model.Role;
import com.khsa.usermanagement.repository.RoleRepository;
import com.khsa.usermanagement.repository.UserRepository;
import com.khsa.usermanagement.repository.mongo.AccountRepository;
import com.khsa.usermanagement.repository.mongo.CustomerRepository;
import com.khsa.usermanagement.repository.mongo.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private RoleRepository roleRepository;
    private Validator validator;


    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository,
                           AccountRepository accountRepository,
                           CustomerRepository customerRepository,
                           TransactionRepository transactionRepository,
                           Validator validator) {
        this.roleRepository = roleRepository;
        this.validator = validator;
    }

    /**
     * Получение списка ролей из БД
     *
     * @return Список имеющихся ролей из БД
     */
    @Override
    public List<Role> list() {
        return roleRepository.findAll();
    }

    /**
     * Добавление новой роли в БД.
     *
     * @param role Добавляемая роли
     * @return добавленная роль
     */
    @Override
    public Role add(Role role) {
        return null;
    }

    /**
     * Получение конкретной роли из БД
     *
     * @param id Идентификатор роли для поиска
     * @return Данные о роли или null - если роли не найдена
     */
    @Override
    public Role get(long id) {
        return null;
    }

    /**
     * Редактирование существующей роли в БД.
     *
     * @param role Обновленные данные для внесения в БД
     * @return отредактированная роль
     */
    @Override
    public Role edit(Role role) {
        return null;
    }

    /**
     * Удаление роли в БД
     *
     * @param id Идентификатор роли для удаления
     */
    @Override
    public void delete(long id) {

    }
}
