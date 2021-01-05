package com.khsa.usermanagement.service;

import com.khsa.usermanagement.domain.model.analytic.Account;
import com.khsa.usermanagement.domain.model.analytic.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TransactionService {

    /**
     * Получение списка транзакций из БД для указанных аккаунтов
     * @return Список имеющихся аккаунтов из БД
     */
    Page<Transaction> list(Pageable pageable, List<Account> account);

    /**
     * Добавление новой транзакции в БД.
     *
     * @param transaction Добавляемая транзакция
     * @return добавленная транзакция
     */
    Transaction add(Transaction transaction);

    /**
     * Получение конкретной транзакции из БД
     *
     * @param id Идентификатор аккаунта для поиска
     * @return Данные об аккаунте или null - если аккаунт не найден
     */
    Transaction get(String id);

    /**
     * Редактирование существующего пользователя в БД.
     *
     * @param transaction Обновленные данные для внесения в БД
     * @return отредактированный аккаунт
     */
    Transaction edit(Transaction transaction) ;

    /**
     * Удаление аккаунта в БД
     *
     * @param id Идентификатор аккаунта для удаления
     */
    void delete(String id);

}
