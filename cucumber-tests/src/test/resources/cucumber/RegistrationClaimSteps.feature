Feature: 2. Сценарии использования над разделом Заявки на регистрацию.
  0. Аутентификация пользователя:  POST /authentication/login + body
  5. Поиск в списке Пользователей: GET /users

  Scenario Outline:
    Given Пользователь вводит логин "<login>" и пароль "<password>"
    When  Просматривает список пользователей "<pages>" "<elements>" по фильтрам "<text>" "<roleIds>" "<confirmed>"

    Examples:
      | login   | password  | pages | elements | text             | roleIds | confirmed |
      | login1  | password1 | 0     | 8        | Горбунов         |         |           |
      | login2  | password2 | 0     | 1        | Николай          |         |           |
      | login2  | password2 | 0     | 4        | Валерьяновна     |         | true      |
      | login1  | password1 | 0     | 8        | login2@gmail.com | 2       |           |
      | login5  | password5 | 0     | 200      |                  | 1       | true      |