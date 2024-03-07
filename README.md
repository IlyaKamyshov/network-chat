# Курсовой проект "Сетевой чат"

## Описание проекта

Я разработал два приложения для обмена текстовыми сообщениями по сети с помощью консоли (терминала) между двумя и более пользователями.

**Первое приложение - сервер чата**, ожидает подключения пользователей, получает сообщение от пользователя и отправляет его остальным участникам чата.

**Второе приложение - клиент чата**, подключается к серверу чата и осуществляет доставку и получение новых сообщений.

Все сообщения записываются в server.log на сервере и в client.log на клиентах. Файлы с логами дополняются при каждом запуске, а также при отправленном или полученном сообщении. Выход из чата осуществляется по команде /exit.

## Требования к серверу

- Установка порта для подключения клиентов через файл настроек (server.cfg);
- Возможность подключиться к серверу в любой момент и присоединиться к чату;
- Отправка новых сообщений клиентам;
- Запись всех отправленных через сервер сообщений с указанием имени пользователя и времени отправки.

## Требования к клиенту

- Выбор имени для участия в чате;
- Прочитать настройки приложения из файла настроек: адрес и номер порта сервера;
- Подключение к указанному в настройках серверу;
- Для выхода из чата нужно набрать команду выхода - “/exit”;
- Каждое сообщение участников должно записываться в текстовый файл - файл логирования. При каждом запуске приложения файл должен дополняться.

## Протокол обмена сообщениями

- Клиент и сервер будут общаться через сокетное соединение. Одна сторона будет записывать данные в сокет, а другая читать.
- Клиент после подключения к серверу и до попадания в чат должен представиться: клиент после запуска запрашивает у пользователя имя и незаметно для пользователя отправляет содержащее имя сообщение на сервер.
- Первое сообщение от клиента серверу будет именем пользователя.

## Реализация сервера

- Сервер поддерживает множество соединений с разными клиентами одновременно.
- Сервер создает серверное сокетное соединение.
- В цикле ожидает, когда какой-то клиент подключится к сокету.
- После подключения клиента сервер воссоздает клиентский сокет и в новом потоке (thread) созадет потоки ввода/вывода, т.е. для каждого клиента создается отдеьлный поток в котором происходит двухстороннее общение клиент-сервер.

## Реализация клиента

- Клиент создает два потока (thread): один для чтения сообщений, второй для отправки, поскольку цикл, в котором буду обрабатываться присланные сообщения, должен быть отделен от цикла, в котором читаются сообщения из консоли и отправляются на сервер для пересылки остальным клиентам.

## Тестирование

- после написания сервера проведен интеграционный тест с помощью telnet.
- после написания клиента проведен интеграционный тест сервер-клиент.
- проведен интеграционный тест сервера с несколькими клиентами.