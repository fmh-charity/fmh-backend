# О проекте:

Backend сервис для приложения «Мобильный хоспис»

**Технологии:** Java, Spring Boot, Postgres

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
