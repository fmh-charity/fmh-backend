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
values ('login1', '$2a$10$/qkdAUtfdxMs.V5iil9xNO0Laa1uwdqDlDbIi.9X5I5.ieJ9nxk8G', 'Николай', 'Смирнов', 'Петрович',
        '+72186536987',
        'login1@gmail.com', false),
       ('login2', '$2a$10$hwGTJx3p2yA0AnP.qxQCgey/w8pKn9YmGLtlV5w76O1n2c0bgDrDq', 'Данил', 'Лебедев', 'Александрович',
        '+71254793526',
        'login2@gmail.com', false),
       ('login3', '$2a$10$zCwKmp.DN9bOb5tn.DVydOn1IidVmt7dgwjqefrLLkKpO7T7F18Hm', 'Егор', 'Горбунов', 'Богданович',
        '+79632589647',
        'login3@gmail.com', false),
       ('login4', '$2a$10$dkp74SIWxF5XzutsqUkSMu7qcs/VXNlOHpaZQjrb6n2NIfs75Bll2', 'Алия', 'Цветкова', 'Валерьяновна',
        '+75745698521',
        'login4@gmail.com', false),
       ('login5', '$2a$10$NSeshtrQF4nWbNNazaREiuQVbNVReCvQH/KQo0qLj/RpeUl9yhhe2', 'Зоя', 'Прохорова', 'Альфредовна',
        '+74582136098',
        'login5@gmail.com', false);

insert into user_role (user_id, role_id, deleted)
values (1, 1, false),
       (1, 2, false),
       (2, 1, false),
       (3, 2, false),
       (4, 2, false),
       (5, 1, false);