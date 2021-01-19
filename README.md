
# Thymeleaf for User and Transaction (billing) Management.

Web application that allows you:
 1. manage users (create, view the list and details, edit and delete) with RESTful services,
 2. login/register in the system with using PostgreSQL data
 3. see the list of transaction from NoSQL (MongoDB)
 4. Write and read event stream from kafka

Tags: #java, #springboot, #hibernate, #jpa, #springSecurity, #mvc, #thymeleaf, #postgreSQL, #mongodb, #kafka

Project uses: JDK14, MongoDB Atlas Cluster, Remote PostgreSQL server, Local Kafka Server.
---

### Configuration for start of project:

1. Use src/resources/application.properties for configure connection to DB MongoDB, PostgreSQL or use existed demo access.
2. Use external kafka server or local server, e.g with docker from wurstmeister image 
Fill with some value `YOUR_BROKER_HOST_PUBLIC_IP` at `KAFKA_ADVERTISED_LISTENERS: INSIDE://kafka:9093,OUTSIDE://YOUR_BROKER_HOST_PUBLIC_IP:9092` 
in ``kafka_conf/docker-compose.yml`` file to call kafka from external hosts. Then start:
```sh
$ docker-compose -f .kafka_conf/docker-compose.yml up -d
```
Move `src/main/java/com/khsa/kafka` to `src/main/java/com/khsa/usermanagement/kafka` to enable auto assembly Kafka-Classes by Spring-Boot
3. Set "VM options"  `-Djdk.tls.client.protocols=TLSv1.2`

![](info_images/RunDebug Configurations.jpg?raw=true)

### Sugar 

1. PostgreSQL and MongoDB connection in one Java Spring project.
2. Thymeleaf templates for web pages with multi language option
3. REST API for manage entities.
4. Complex dependencies and connections between Domain models.
5. Password generator (64 bit lenght, SHA512 method, using static Salt).
6. Enum (gender) in "User" model with converter for stable working with Spring WEB and Hibernate

---
# Thymeleaf для управления пользователями и транзакциями (биллингом).

Веб-приложение позволяет:
 1. Управлять пользователями (создавать, просматривать список и детали, редактировать и удалять) с помощью служб RESTful,
 2. Авторизоваться / зарегистрироваться в системе с использованием данных из PostgreSQL и кешированием пароля
 3. Посмотреть веб-страницу со списком транзакций из NoSQL (MongoDB)
Тэги: #java, #springboot, #hibernate, #jpa, #springSecurity, #mvc, #thymeleaf, #postgreSQL, #mongodb, #kafka

В проекте используется: JDK14, MongoDB Atlas Cluster, удаленный сервер PostgreSQL, Local Kafka Server.


### Конфигурация для запуска проекта:

1. Используйте src / resources / application.properties для изменения настроек подключения к БД MongoDB, PostgreSQL или используйте существующий демонстрационный доступ.
2. Необходимо использовать внешний или локальный сервер kafka, например с докером из образа wurstmeister
   Заполните некоторым значением `YOUR_BROKER_HOST_PUBLIC_IP` в` KAFKA_ADVERTISED_LISTENERS: INSIDE: // kafka: 9093, OUTSIDE: // YOUR_BROKER_HOST_PUBLIC_IP: 9092`
   в файле ``kafka_conf/docker-compose.yml`` для вызова kafka с внешних хостов. Для запуска образов выполните команду:
   ```sh
   $ docker-compose -f .kafka_conf/docker-compose.yml up -d
   ```
   Переместите `src/main/java/com/khsa/kafka` в `src/main/java/com/khsa/usermanagement/kafka`, чтобы включить автоматическую сборку Классов-Кафки с помощью Spring-Boot.
3. Установите "VM options" `-Djdk.tls.client.protocols = TLSv1.2`
![](info_images/RunDebug Configurations.jpg?raw=true)

### Основные особенности

1. Использование соединений с PostgreSQL и MongoDB в одном проекте Java Spring.
2. Шаблоны Thymeleaf для веб-страниц с возможностью выбора нескольких языков
3. REST API для управления сущностями.
4. Сложные зависимости и связи между моделями (сущностями).
5. Генератор паролей (длина 64 бита, метод SHA512, с использованием статической соли).
6. Enum (пол) с конвертером в модели "Пользователь" для работы с Spring WEB и Hibernate

### Управление пользователями
Используется БД Postgres, с таблицами:
users - пользователи, 
roles - поли, 
user_roles - связка пользователей и ролей

Сервис работает с форматом JSON и имеет следующий набор методов:
 - GET http://localhost:8080/user/list
 - GET http://localhost:8080/user/get/{id}
 - DELETE http://localhost:8080/user/delete/{id}
 - POST http://localhost:8080/user/add
 - PUT http://localhost:8080/user/edit/{id}
 
 Коллекция POSTMAN запросов находится в файле UserManagementLocalHost.postman_collection.json (временно неактуально)
