
# Thymeleaf for User and Transaction (billing) Management.

Web application that allows you:
 1. manage users (create, view the list and details, edit and delete) with RESTful services,
 2. login/register in the system with using PostgreSQL data
 3. see the list of transaction from NoSQL (MongoDB)

Tags: #java, #springboot, #hibernate, #jpa, #springSecurity, #mvc, #thymeleaf, #postgreSQL, #mongodb

Project use: JDK14, MongoDB Atlas Cluster, Remote PostgreSQL server.
---

### Configuration works for start of project:

1. Use src/resources/application.properties for configure connection to DB MongoDB, PostgreSQL or use existed demo access.
2. Set "VM options"  `-Djdk.tls.client.protocols=TLSv1.2`
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
Тэги: #java, #springboot, #hibernate, #jpa, #springSecurity, #mvc, #thymeleaf, #postgreSQL, #mongodb

В проекте используется: JDK14, MongoDB Atlas Cluster, удаленный сервер PostgreSQL.


### Конфигурация работает для запуска проекта:

1. Используйте src / resources / application.properties для настройки подключения к БД MongoDB, PostgreSQL или используйте существующий демонстрационный доступ.
2. Установите "VM options" `-Djdk.tls.client.protocols = TLSv1.2`
![](info_images/RunDebug Configurations.jpg?raw=true)

### Основные особенности

1. Использование соединений с PostgreSQL и MongoDB в одном проекте Java Spring.
2. Шаблоны Thymeleaf для веб-страниц с возможностью выбора нескольких языков
3. REST API для управления сущностями.
4. Сложные зависимости и связи между моделями (сущностями).
5. Генератор паролей (длина 64 бита, метод SHA512, с использованием статической соли).
6. Enum (пол) с конвертером в модели "Пользователь" для работы с Spring WEB и Hibernate

