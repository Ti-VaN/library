# Библиотека книг с уведомлениями через RabbitMQ

Простое RESTful API для управления библиотекой книг, интегрированное с RabbitMQ для отправки уведомлений о действиях с книгами.

## Возможности приложения

- **Создание, обновление, просмотр и удаление книг.**
- **Отправка уведомлений в очередь RabbitMQ при добавлении, изменении и удалении книги.**
- **Обработка уведомлений с помощью слушателя, который реагирует на изменения данных.**

## Компоненты приложения

- **API для книг:** контроллер и сервис, обеспечивающие CRUD-операции для книг.
- **RabbitMQ:** сообщения об изменениях отправляются в очередь `bookQueue`.
- **Слушатель RabbitMQ:** автоматически обрабатывает сообщения из очереди и реагирует на них, выполняя нужные действия.

## Запуск приложения

1. Включите панель управления RabbitMQ:
   ```
   rabbitmq-plugins enable rabbitmq_management
   ```
   Проверить работу можно по адресу: http://localhost:15672 (логин: guest, пароль: guest).
   
 2. Поменяйте настройки подключения к БД в файле **application.properties**:
```properties
spring.datasource.username=username
spring.datasource.password=password
```

3. Запустите приложение в IntelliJ IDE
   
**Примеры запросов:**
**Создание книги:**
```
POST http://localhost:8080/api/books

json
{
    "title": "Thinking in Java",
    "author": "Bruce Eckel",
    "published": "2006-01-31"
}
```
**Получение списка всех книг**
```
GET http://localhost:8080/api/books
```
**Обновление книги**
```
PUT http://localhost:8080/api/books/{id}

json
{
    "title": "Thinking in Java 2",
    "author": "Bruce Eckel",
    "published": "2009-01-31"
}
```
**Удаление книги**
```
DELETE http://localhost:8080/api/books/{id}
```
## Используемые технологии
- Java 17
- Spring Boot
- Spring Data JPA 
- RabbitMQ для очередей сообщений
