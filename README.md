### Message broker Kafka.

Простой проект демонстрирующий работу брокера сообщений.

Описание:

В проекте два модуля:

- Client (Port 8082). Принимает входящее сообщение в виде параметра
  по Rest API (GET /send?message=YOUR_TEXT)
  и отправляет его с помощью брокера на Server .
  Сообщение GET /send?message=stat - запрашивает статистику.

- Server (Port 8081). Сохраняет входящие сообщения от клиента и ведет статистику.
  По запросу клиента выдает статистику с помощью брокера.

Используемые технологии:

- Spring Boot
- Maven
- Java 17
- Zookeeper
- Kafka

Запуск:

- Запустить Zookeeper + Kafka
- В корне проекта в консоле:
    - mvn clean install
    - java -jar ./Server/target/Server-1.0-SNAPSHOT.jar
    - java -jar ./Client/target/Client-1.0-SNAPSHOT.jar
