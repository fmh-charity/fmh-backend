Feature: Сценарии использования справочником «Управление пользователями».
  1. Аутентификация пользователя
  2. Получение информации о пользователе по его идентификатору

  Scenario Outline:
    Given Пользователь вводит логин "<login>" и пароль "<password>"
    When  Просматривает информацию о пользователе по "<userId>"
    Then  Результат "<result>"

    Examples:
      | login   | password  | userId | result  |
      | login1  | password1 | 1      | SUCCESS |
      | login2  | password2 | 2      | SUCCESS |
      | login1  | password1 | 3      | SUCCESS |
      | login2  | password2 | 4      | SUCCESS |
      | login5  | password5 | 5      | SUCCESS |