# Игра "Быки и коровы" с REST API

Этот проект представляет собой REST API для игры "Быки и коровы". Он позволяет пользователям регистрироваться, входить в систему, создавать игровые сессии и делать попытки угадать число.

## Содержание

- [Предварительные требования](#предварительные-требования)
- [Установка](#установка)
- [Запуск приложения](#запуск-приложения)
- [API Endpoints](#api-endpoints)

## Предварительные требования

Перед началом убедитесь, что у вас установлены следующие компоненты:

- Java Development Kit (JDK) 17 или выше
- Maven
- IDE (например, IntelliJ IDEA, Eclipse)
- База данных (например, MySQL, PostgreSQL)

## Установка

1. Клонируйте репозиторий:

   
    git clone https://github.com/klOmzy/demo.git
   
    cd demo
    
3. Настройте базу данных:

    - Создайте новую базу данных для проекта.
    - Обновите файл application.properties с конфигурацией вашей базы данных:

       
        spring.datasource.url=jdbc\:mysql://localhost:3306/your_database_name
        spring.datasource.username=your_database_username
        spring.datasource.password=your_database_password
        spring.jpa.hibernate.ddl-auto=update
        
4. Соберите проект:

   
    mvn clean install
    
## Запуск приложения

1. Запустите приложение:

   
    mvn spring-boot:run
    
2. Доступ к API:

    Приложение будет запущено на http://localhost:8080. Вы можете получить доступ к API endpoints с помощью инструмента, такого как Postman или cURL.

## API Endpoints

### Endpoints пользователя

- Регистрация нового пользователя:

   
    POST /register
    
    Тело запроса:

   
    {
        "login": "your_login",
        "password": "your_password"
    }
    
- Вход в систему:

   
    POST /login
    
    Тело запроса:

   
    {
        "login": "your_login",
        "password": "your_password"
    }
    
### Endpoints игровой сессии

- Создание новой игровой сессии:

   
    POST /game/create
    
    Тело запроса:

   
    {
        "number": "1234",
        "gameRules": "repeat",
        "session": "your_session_id"
    }
    
- Получение статуса игровой сессии:

   
    GET /game/{sessionId}/status
    
### Endpoints попыток

- Сделать попытку:

   
    POST /game/{sessionId}/attempt
    
    Тело запроса:

   
    {
        "number": "1234"
    }
