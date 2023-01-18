Feature: Сценарии использования справочником «Документы».
  1. Аутентификация пользователя
  2. Просмотр списка опубликованных документов находящихся не в архиве

  Scenario Outline:
    Given Пользователь вводит логин "<login>" и пароль "<password>"
    When  Просматривает список документов
    Then  Результат "<result>"

    Examples:
      | login   | password  | result  |
      | login1  | password1 | SUCCESS |
      | login2  | password2 | SUCCESS |
      | login1  | password1 | SUCCESS |
      | login2  | password2 | SUCCESS |
      | login5  | password5 | SUCCESS |