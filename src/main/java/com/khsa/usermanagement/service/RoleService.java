package com.khsa.usermanagement.service;

import com.khsa.usermanagement.domain.model.Role;

import java.util.List;


public interface RoleService {

    /**
     * Получение списка ролей из БД
     *
     * @return Список имеющихся ролей из БД
     */
    List<Role> list();

    /**
     * Добавление новой роли в БД.
     *
     * @param role Добавляемая роли
     * @return добавленная роль
     */
    Role add(Role role);

    /**
     * Получение конкретной роли из БД
     *
     * @param id Идентификатор роли для поиска
     * @return Данные о роли или null - если роли не найдена
     */
    Role get(long id);

    /**
     * Редактирование существующей роли в БД.
     *
     * @param role Обновленные данные для внесения в БД
     * @return отредактированная роль
     */
    Role edit(Role role) ;

    /**
     * Удаление роли в БД
     *
     * @param id Идентификатор роли для удаления
     */
    void delete(long id);

}
