insert into news_category (name, deleted)
values ('ОБЪЯВЛЕНИЕ', false),
       ('ДЕНЬ РОЖДЕНИЯ', false),
       ('ЗАРПЛАТА', false),
       ('ПРОФСОЮЗ', false),
       ('ПРАЗДНИК', false),
       ('МАССАЖ', false),
       ('БЛАГОДАРНОСТЬ', false),
       ('НУЖНА ПОМОЩЬ', false);
insert into users (login, password, first_name, last_name, middle_name, phone_number, email, deleted)
values ('login1', 'password1', 'Николай', 'Смирнов', 'Петрович', '+72186536987', 'login1@gmail.com', false),
       ('login2', 'password2', 'Данил', 'Лебедев', 'Александрович', '+71254793526', 'login2@gmail.com', false),
       ('login3', 'password3', 'Егор', 'Горбунов', 'Богданович', '+79632589647', 'login3@gmail.com', false),
       ('login4', 'password4', 'Алия', 'Цветкова', 'Валерьяновна', '+75745698521', 'login4@gmail.com', false),
       ('login5', 'password5', 'Зоя', 'Прохорова', 'Альфредовна', '+74582136098', 'login5@gmail.com', false);

insert into user_role (user_id, role_id, deleted)
values (1, 1, false),
       (1, 2, false),
       (2, 1, false),
       (3, 2, false),
       (4, 2, false),
       (5, 1, false);