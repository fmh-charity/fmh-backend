Feature: Сценарии использования справочником «Документы».
  1. Аутентификация пользователя
  2. Просмотр списка информации о опубликованных документах находящихся не в архиве
  3. Возвращает список документов для администратора

  Scenario Outline:
    Given Пользователь вводит логин "<login>" и пароль "<password>"
    When  Просматривает список информации о документах
    And   Возвращает список документов для администратора
    Then  Результат "<result>"

    Examples:
      | login   | password  | result  |
      | login1  | password1 | SUCCESS |
      | login2  | password2 | SUCCESS |
      | login1  | password1 | SUCCESS |
      | login2  | password2 | SUCCESS |
      | login5  | password5 | SUCCESS |