insert into users (login, password, first_name, last_name, middle_name, phone_number, email, deleted)
values ('login1', '$2a$10$/qkdAUtfdxMs.V5iil9xNO0Laa1uwdqDlDbIi.9X5I5.ieJ9nxk8G', 'Петр', 'Смирнов', 'Петрович',
        '+79999999999',
        'login1@gmail.com', false),
       ('login2', '$2a$10$hwGTJx3p2yA0AnP.qxQCgey/w8pKn9YmGLtlV5w76O1n2c0bgDrDq', 'Данил', 'Иванов', 'Данилович',
        '+79999999999',
        'login2@gmail.com', false),
       ('login3', '$2a$10$zCwKmp.DN9bOb5tn.DVydOn1IidVmt7dgwjqefrLLkKpO7T7F18Hm', 'Егор', 'Петров', 'Егорович',
        '+79999999999',
        'login3@gmail.com', false),
       ('login4', '$2a$10$dkp74SIWxF5XzutsqUkSMu7qcs/VXNlOHpaZQjrb6n2NIfs75Bll2', 'Дмитрий', 'Сидоров', 'Дмитриевич',
        '+79999999999',
        'login4@gmail.com', false),
       ('login5', '$2a$10$NSeshtrQF4nWbNNazaREiuQVbNVReCvQH/KQo0qLj/RpeUl9yhhe2', 'Тест', 'Тестов', 'Тестович',
        '+79999999999',
        'login5@gmail.com', false);

insert into user_role (user_id, role_id, deleted)
values (1, 1, false),
       (1, 2, false),
       (2, 1, false),
       (3, 2, false),
       (4, 2, false),
       (5, 1, false);