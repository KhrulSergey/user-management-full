package com.khsa.usermanagement.service;

import com.khsa.usermanagement.domain.model.analytic.Customer;


public interface CustomerService {

    /**
     * Добавление нового покупателя в БД.
     *
     * @param customer Добавляемый покупатель
     * @return добавленный покупатель
     */
    Customer add(Customer customer);

    /**
     * Получение конкретного покупателя из БД
     *
     * @param id Идентификатор покупателя для поиска
     * @return Данные о аккаунте или null - если покупатель не найден
     */
    Customer get(String id);

    /**
     * Получение конкретного покупателя из БД
     *
     * @param username Имя покупателя для поиска
     * @return Данные о аккаунте или null - если покупатель не найден
     */
    Customer getByUserName(String username);

    /**
     * Редактирование существующего покупателя в БД.
     *
     * @param customer Обновленные данные для внесения в БД
     * @return отредактированный покупатель
     */
    Customer edit(Customer customer) ;

    /**
     * Удаление покупателя в БД
     *
     * @param id Идентификатор покупателя для удаления
     */
    void delete(String id);

}
