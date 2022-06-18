Feature: Сценарии «Блоки».
  1. Аутентификация пользователя
  2. Просмотр списка блоков
  3. Создание блока
  4. Просмотр блока
  5. Редактирование блока
  6. Удаление блока

  Scenario Outline:
    Given Пользователь вводит логин "<login>" и пароль "<password>"
    When  Просматривает список блоков
    And   Создает новый блок: "<name>", "<comment>"
    And   Просматривает блок: <id>
    And   Редактирует созданный блок: "<edited name>", "<edited comment>", <id>
    And   Удаляет созданный блок: <id>
    Then  Результат "<result>"

    Examples:
      | login  | password  | name   | comment     | edited name | edited comment      | result  | id |
      | login1 | password1 | Block1 | new patient | Block-1     | new patient arrived | SUCCESS | 1  |
      | login2 | password2 | Block2 | new patient | Block-2     | new patient arrived | SUCCESS | 2  |
      | login3 | password3 | Block3 | new patient | Block-3     | new patient arrived | SUCCESS | 3  |
      | login4 | password4 | Block4 | new patient | Block-4     | new patient arrived | SUCCESS | 4  |
      | login5 | password5 | Block5 | new patient | Block-5     | new patient arrived | SUCCESS | 5  |
