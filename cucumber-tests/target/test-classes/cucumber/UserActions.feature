Feature: Действия пользователя.
  1. Аутентификация:             POST /authentication/login + body
  2. Проверка полученного accessToken
  3. Информация по пользователю: GET  /fmh/authentication/userInfo
  4. Список доступных новостей:  GET  /news
  5. Просмотр новости:           GET  /news/{id}

   Scenario Outline:
     Given 1. Пользователь вводит логин "<login>" и пароль "<password>"
     When  2. получен "<token>"
     And   3. получена информация по пользователю
     And   4. пользователь просматривает список доступных новостей
     And   5. пользователь просматривает новость "<news>"
     Then  результат "<result>"

     Examples:
      | login   | password  | token         | news | result   |
      | login1  | password1 | TOKEN         | 1    | SUCCESS  |
#      | login3  | password3 | TOKEN         | 2    | SUCCESS  |
#      | login3  | password3 | TOKEN         | 3    | SUCCESS  |
#      | login3  | password3 | TOKEN         | 2    | ERROR  |