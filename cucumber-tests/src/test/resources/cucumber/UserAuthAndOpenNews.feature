Feature: Действия пользователя.
  1. Аутентификация:             POST /authentication/login + body
  2. Проверка полученного accessToken
  3. Информация по пользователю: GET  /fmh/authentication/userInfo
  4. Список доступных новостей:  GET  /news
  5. Просмотр новости:           GET  /news/{id}

  Scenario Outline:
    Given Пользователь вводит логин "<login>" и пароль "<password>"
    When  Получен "<token>"
    And   Получена информация по пользователю
    And   Пользователь просматривает список доступных новостей
    And   Пользователь просматривает новость "<news>"
    Then  Результат "<result>"

    Examples:
      | login  | password  | token | news | result  |
      | login1 | password1 | TOKEN | 1    | SUCCESS |
      | login2 | password2 | TOKEN | 2    | SUCCESS |
      | login3 | password3 | TOKEN | 3    | SUCCESS |
      | login4 | password4 | TOKEN | 2    | SUCCESS |
      | login5 | password5 | TOKEN | 1    | SUCCESS |
