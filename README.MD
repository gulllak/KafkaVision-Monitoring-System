### KafkaVision Monitoring System - приложение отслеживает метрики, отправляет их в очередь сообщений (Apache Kafka). Микросервис-потребитель сохраняет данные о метриках в базу данных.

_Система будет включать в себя **Producer** для отправки метрик, **Consumer** для их обработки и анализа, а также REST API для просмотра метрик._ 

#### В БД сохраняются следующие данные:
- Имя приложения в котором вызван метод
- Адерс эндпоита
- IP адрес пользователя обратившегося к эндпонту (можно получить из HttpServletRequest)
- Дата и время

### REST API для получения статистики:

Для простоты тестирования api, в корневой директории проекта находится файл **_KafkaVision.postman_collection.json_**, который можно импортировать в Postman.

#### Микросервис MetricsProducer
1. Тестовый запрос с использованием рандомных данных (для заполнения БД):
```
GET http://localhost:8080/metrics
```
Получаем ответ:
```
   Тестовые данные отправлены
   ```
2. Отправить json объект в Kafka:
```
POST http://localhost:8080/metrics
```
Получаем ответ в следующем формате:
```
Json сообщение отправлено в топик Kafka
   ```
#### Микросервис MetricsConsumer
3. Получение списка всех метрик:
```
GET http://localhost:9090/stats
```
Получаем ответ в следующем формате:
```
[
    {
        "id": 1,
        "app": "exampleApp",
        "uri": "/example/uri",
        "ip": "192.168.1.1",
        "timestamp": "2023-10-02T15:30:00"
    },
    {
        "id": 2,
        "app": "App2",
        "uri": "/login",
        "ip": "253.10.251.125",
        "timestamp": "2024-05-16T14:09:14.578949"
    },
    ...
]
   ```
4. Получение метрики по id:
```
GET http://localhost:9090/stats/{id}
```
Получаем ответ в следующем формате:
```
{
    "id": 1,
    "app": "exampleApp",
    "uri": "/example/uri",
    "ip": "192.168.1.1",
    "timestamp": "2023-10-02T15:30:00"
}
```
5. Получение статистики по каждому эндпоинту. Показывает сколько всего раз пользователи воспользовались эндпоинтом. 
Если установить параметр `unique=true`, то будут показаны только уникальные вызовы эндпоинта от пользователей.
```
GET http://localhost:9090/stats/hits?unique=true
```
Получаем ответ в следующем формате:
```
[
    {
        "app": "App1",
        "uri": "/booking/book/1",
        "hits": 40
    },
    {
        "app": "App2",
        "uri": "/login",
        "hits": 31
    },
    ...
]
```

## Как запустить

1. Склонировать проект.
    ```bash
   $ git clone https://github.com/gulllak/KafkaVision-Monitoring-System.git
   ```
2. Создать БД и прописать значиния в application.properties
    ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/***
   spring.datasource.username=***
   spring.datasource.password=***
   ```
3. Запустить проект локально.
4. Перейти в телеграм для тестирования бота.
5. Подробная документация по эндпоинтам доступна после запуска проекта локально по адресу http://localhost:8080/swagger-ui.html

### 🏄 Стек:
Java 21, SpringBoot 3, PostgreSQL, Liquibase, Maven, Spring AOP, SpringDoc OpenAPI.