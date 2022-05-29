Feature: Сценарии использования справочником «Палаты».
  1. Просмотр списка палат
  2. Создание палаты
  3. Просмотр карточки палаты
  4. Редактирование палаты
  5. Удаление палаты

  Scenario Outline:
    Given Пользователь вводит логин "<login>" и пароль "<password>"
    When  Просматривает список палат
    And   Создает новую палату: "<name>", "<block>", "<nurse station>", "<max occupancy>", "<comment>"
    And   Просматривает карточку созданной палаты
    And   Редактирует созданную палату: "<edited name>", "<edited block>", "<edited nurse station>", "<edited max occupancy>", "<edited comment>"
    And   Удаляет созданную палату
#    Then  Результат "<result>"

    Examples:
      | login   | password  | name     | block | nurse station | max occupancy   | comment  | edited name | edited block | edited nurse station | edited max occupancy | edited comment |
      | login1  | password1 | Палата 1 | 1     | 1             | 10              | Тест     | Палата №31  | 3            | 3                    | 15                   | Изменен        |
      | login2  | password2 | Палата 2 | 1     | 1             | 10              | Тест     | Палата №32  | 3            | 3                    | 15                   | Изменен        |
      | login3  | password3 | Палата 3 | 2     | 2             | 8               | Тест     | Палата №21  | 2            | 2                    | 15                   | Изменен        |
      | login4  | password4 | Палата 4 | 2     | 2             | 8               | Тест     | Палата №22  | 2            | 2                    | 15                   | Изменен        |
      | login5  | password5 | Палата 5 | 3     | 3             | 5               | Тест     | Палата №11  | 1            | 1                    | 15                   | Изменен        |
