package com.khsa.usermanagement.service;

import com.khsa.usermanagement.domain.model.analytic.Account;
import com.khsa.usermanagement.domain.model.analytic.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AccountService {

    /**
     * Получение списка аккаунта из БД для указанного покупателя
     *
     * @return Список имеющихся аккаунтов из БД
     */
    Page<Account> list(Pageable pageable, Customer customer);

    /**
     * Добавление нового аккаунта в БД.
     *
     * @param account Добавляемый аккаунт
     * @return добавленный аккаунт
     */
    Account add(Account account);

    /**
     * Получение конкретного аккаунта из БД
     *
     * @param id Идентификатор аккаунта для поиска
     * @return Данные об аккаунте или null - если аккаунт не найден
     */
    Account get(String id);

    /**
     * Получение конкретного аккаунта из БД по ГУИД
     *
     * @param guid Уник. идентификатор аккаунта для поиска
     * @return Данные об аккаунте или null - если аккаунт не найден
     */
    Account getByGuid(Long guid);

    /**
     * Редактирование существующего пользователя в БД.
     *
     * @param account Обновленные данные для внесения в БД
     * @return отредактированный аккаунт
     */
    Account edit(Account account) ;

    /**
     * Удаление аккаунта в БД
     *
     * @param id Идентификатор аккаунта для удаления
     */
    void delete(String id);

}
