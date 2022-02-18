# О проекте:

Backend сервис для приложения «Мобильный хоспис»

**Технологии:** Java, Spring Boot, Postgres

# Первичная установка

1. Скачать проект с гитхаба https://github.com/fmh-charity/fmh-backend
2. Обновить мавен зависимости
3. Добавить в конфигурацию идеи environment variables DB_PORT=5400;DB_HOST=localhost;DB_USER=postgres;DB_PASS=123;DB_NAME=FMH_DB
4. Установить докер(десктоп версию под Win or Mac)
5. запустить локальный файл компоуз для подняти бд либо через idea либо в терминале `docker-compose -f docker-compose-env-only.yml up`
6. под профилем dev сделать clean package
7. запустить Application
8. Прогнать скрипт в бд test_users.sql для создания тестовых пользователей


# Тестирование эндпойнтов через scratch:

POST http://localhost:8080/fmh/authentication/login
Content-Type: application/json

{
"login": "login1",
"password": "password1"
}

# Локальный запуск:

## Вариант 1, backend сервис и окружение в докере

1. Собрать docker image: `docker build -t fmh_back:1.0`  
1. Выполнить в корне `docker-compose up`
1. Перейти в Swagger - `http://localhost:8080/fmh/swagger-ui/#/` 

## Вариант 2, backend запустить локально, а окружение в докере

1. Выполнить в корне `docker-compose -f docker-compose-env-only.yml up`
1. Установить переменные окружения (env vars): `DB_PORT=5400;DB_HOST=localhost;DB_USER=postgres;DB_PASS=123`
1. Запустить backend сервис
1. Перейти в Swagger - `http://localhost:8080/fmh/swagger-ui/#/`
