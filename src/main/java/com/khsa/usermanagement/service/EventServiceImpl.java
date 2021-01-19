package com.khsa.usermanagement.service;

import com.khsa.usermanagement.domain.model.analytic.Account;
import com.khsa.usermanagement.domain.model.analytic.Event;
import com.khsa.usermanagement.repository.UserRepository;
import com.khsa.usermanagement.repository.mongo.AccountRepository;
import com.khsa.usermanagement.repository.mongo.CustomerRepository;
import com.khsa.usermanagement.repository.mongo.EventRepository;
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

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Service
public class EventServiceImpl implements EventService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

    private EventRepository eventRepository;
    private Validator validator;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, Validator validator) {
        this.eventRepository = eventRepository;
        this.validator = validator;
    }

    /**
     * Получение списка аккаунта из БД для указанного покупателя
     *
     * @return Список имеющихся аккаунтов из БД
     */
    @Override
    public Page<Event> list(Pageable pageable, Event event) {
        LOG.info("getting events: ");
        return eventRepository.findAllByOrderByIdAsc(pageable);
    }

    /**
     * Добавление нового аккаунта в БД.
     *
     * @param event Добавляемый аккаунт
     * @return добавленный аккаунт
     */
    @Override
    public Event add(Event event) {
        Set<ConstraintViolation<Event>> violations = validator.validate(event);
        if (violations.isEmpty()) {
            LOG.info("save event: {}", event);
            return eventRepository.save(event);
        } else {
            LOG.warn("event: {} have constraint violations: {}", event, violations);
            throw new ConstraintViolationException(violations);
        }
    }

    /**
     * Получение конкретного аккаунта из БД
     *
     * @param id Идентификатор аккаунта для поиска
     * @return Данные об аккаунте или null - если аккаунт не найден
     */
    @Override
    public Event get(String id) {
        return null;
    }

    /**
     * Удаление аккаунта в БД
     *
     * @param id Идентификатор аккаунта для удаления
     */
    @Override
    public void delete(String id) {

    }
}
