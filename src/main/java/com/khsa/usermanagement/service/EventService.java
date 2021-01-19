package com.khsa.usermanagement.service;

import com.khsa.usermanagement.domain.model.analytic.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EventService {

    /**
     * Получение списка аккаунта из БД для указанного покупателя
     *
     * @return Список имеющихся аккаунтов из БД
     */
    Page<Event> list(Pageable pageable, Event event);

    /**
     * Добавление нового аккаунта в БД.
     *
     * @param event Добавляемый аккаунт
     * @return добавленный аккаунт
     */
    Event add(Event event);

    /**
     * Получение конкретного аккаунта из БД
     *
     * @param id Идентификатор аккаунта для поиска
     * @return Данные об аккаунте или null - если аккаунт не найден
     */
    Event get(String id);

    /**
     * Удаление аккаунта в БД
     *
     * @param id Идентификатор аккаунта для удаления
     */
    void delete(String id);

}
