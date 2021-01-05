package com.khsa.usermanagement.service;

import com.khsa.usermanagement.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    /**
     * Получение списка пользователей из БД
     *
     * @return Список имеющихся пользователей из БД
     */
    Page<User> list(Pageable pageable);

    /**
     * Добавление нового пользователя в БД.
     *
     * @param user Добавляемый пользователь
     * @return добавленный пользователь
     */
    User add(User user);

    /**
     * Получение конкретного пользователя из БД
     *
     * @param id Идентификатор пользователя для поиска
     * @return Данные о пользователе или null - если пользователь не найден
     */
    User get(long id);

    /**
     * Редактирование существующего пользователя в БД.
     *
     * @param user Обновленные данные для внесения в БД
     * @return отредактированный пользователь
     */
    User edit(User user) ;

    /**
     * Удаление пользователя в БД
     *
     * @param id Идентификатор пользователя для удаления
     */
    void delete(long id);

}
