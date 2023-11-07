Feature: 2. Сценарии использования заявок на выполнение.
  0. Аутентификация пользователя:  POST /authentication/login + body
  1. Поиск в списке заявок на выполнение: GET /wishes

  Scenario Outline:
    Given Пользователь вводит логин "<login>" и пароль "<password>"
    When  Получен "<token>"
    And   Получена информация по пользователю
    And   Пользователь просматривает список заявок на выполнение "<pages>" "<elements>" по фильтру "<searchValue>" "<sortDirection>" "<sortField>"
    Then  Результат "<result>"

    Examples:
      | login   | password  | token  | result   | pages | elements | searchValue          | sortDirection | sortField |
      | login1  | password1 | TOKEN  | SUCCESS  | 0     | 8        |                      | asc           | id        |
      | login3  | password3 | TOKEN  | SUCCESS  | 0     | 10       | open                 | desc          | status    |
      | login2  | password2 | TOKEN  | SUCCESS  | 0     | 8        | PatientThreelastname |               |           |
      | login4  | password4 | TOKEN  | SUCCESS  | 0     | 100      | Смирнов              | desc          | id        |
      | login5  | password5 | TOKEN  | SUCCESS  | 0     | 200      | IN_PROGRESS          | asc           | status    |