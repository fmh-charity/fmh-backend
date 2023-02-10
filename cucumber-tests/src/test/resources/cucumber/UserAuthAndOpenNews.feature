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
     And   Пользователь просматривает список доступных новостей "<pages>" "<elements>" "<publishDate>" "<newsCategoryId>" "<publishDateFrom>" "<publishDateTo>"
     And   Пользователь просматривает новость "<news>"
     Then  Результат "<result>"

     Examples:
      | login   | password  | token  | news | result   | pages | elements| publishDate | newsCategoryId | publishDateFrom | publishDateTo |
      | login1  | password1 | TOKEN  | 1    | SUCCESS  | 0     | 1       | true        | 1              | 2023-01-27      | 2023-01-28    |
      | login3  | password3 | TOKEN  | 3    | SUCCESS  | 2     | 10      | true        | 3              | 2023-01-26      | 2023-01-28    |
      | login2  | password2 | TOKEN  | 2    | SUCCESS  | 1     | 2       | false       | 2              | 2023-01-27      | 2023-01-28    |
      | login4  | password4 | TOKEN  | 2    | SUCCESS  | 3     | 100     | false       | 4              | 2023-01-28      | 2023-01-28    |
      | login5  | password5 | TOKEN  | 1    | SUCCESS  | 4     | 200     | true        | 5              | 2023-01-24      | 2023-01-28    |