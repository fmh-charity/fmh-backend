Feature: Сценарии использования справочником «Управление пользователями».
  1. Аутентификация пользователя
  2. Получение информации о пользователе по его идентификатору
  3. Редактирование карточки пользователя по его id

  Scenario Outline:
    Given Пользователь вводит логин "<login>" и пароль "<password>"
    When  Просматривает информацию о пользователе по "<userId>"
    And   Редактирует информацию о пользователе "<firstName>", "<lastName>", "<middleName>", "<dateOfBirth>", "<email>", "<roleIds>" по "<userId>"
    Then  Результат "<result>"

    Examples:
      | login   | password  | userId | result  | firstName            | lastName              | middleName             | dateOfBirth | email            | roleIds |
      | login1  | password1 | 1      | SUCCESS | Николай              | Смирнов               | Петрович               | 1990-01-15  | login1@gmail.com | 1, 2    |
      | login2  | password2 | 2      | SUCCESS | Данил                | Лебедев               | Александрович          | 1991-02-16  | login2@gmail.com | 1, 2    |
      | login1  | password1 | 3      | SUCCESS | Егор                 | Горбунов              | Богданович             | 1992-03-17  | email3@change.ru | 2       |
      | login2  | password2 | 4      | SUCCESS | Алия                 | Цветкова              | Валерьяновна           | 1993-03-18  | login4@gmail.com | 2       |
      | login5  | password5 | 5      | SUCCESS | ИзменённоеИмяПять    | ИзменённаяФамилияПять | ИзменённоеОтчествоПять | 1995-01-27  | email5@change.ru | 1, 2    |