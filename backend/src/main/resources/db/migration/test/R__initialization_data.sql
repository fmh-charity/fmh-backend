insert into block (name, comment, deleted)
values ('block1', 'block1_comment', false),
       ('block2', 'block2_comment', false),
       ('block3', 'block3_comment', true);

insert into nurse_station (name, comment, deleted)
values ('nurse-station1', 'nurse-station1-comment', false),
       ('nurse-station2', 'nurse-station2-comment', false),
       ('nurse-station3', 'nurse-station3-comment', true);

insert into room (name, block_id, nurse_station_id, max_occupancy, comment, deleted)
values ('room1', 1, 1, 10, 'room1-comment', false),
       ('room2', 1, 1, 10, 'room2-comment', false),
       ('room3', 2, 2, 10, 'room3-comment', false),
       ('room4', 2, 2, 10, 'room4-comment', false);

insert into patient (first_name, middle_name, last_name, birth_date, deleted, plan_date_in,
                     plan_date_out, fact_date_in, fact_date_out, status, room_id)
values ('PatientOnefirstname', 'PatientOnemiddlename', 'PatientOnelastname', now(), false, '01/01/2000', '01/01/2000', null, null, 'EXPECTED', 1),
       ('PatientTwofirstname', 'PatientTwomiddlename', 'PatientTwolastname', now(), false, '01/01/2000', '01/01/2000', null, null, 'EXPECTED', 1),
       ('PatientThreefirstname', 'PatientThreemiddlename', 'PatientThreelastname', now(), false, '01/01/2000', '01/01/2000', '01/01/2020', null, 'ACTIVE', 1),
       ('PatientFourfirstname', 'PatientFourmiddlename', 'PatientFourlastname', now(), false, '01/01/2000', '01/01/2000', '01/01/2020', null, 'ACTIVE', 1),
       ('PatientFivefirstname', 'PatientFivemiddlename', 'PatientFivelastname', now(), false, '01/01/2000', '01/01/2000', '01/01/2020', '01/01/2020', 'DISCHARGED', 1),
       ('PatientSixfirstname', 'PatientSixmiddlename', 'PatientSixlastname', now(), false, null, null, null, null, 'EXPECTED', null);

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


insert into wish (patient_id, title, description, creator_id, executor_id, create_date, plan_execute_date,
                  fact_execute_date, status, deleted)
values (1, 'wish-title1', 'wish1-description', 1, null, now(), now(), null, 'OPEN', false),
       (2, 'wish-title2', 'wish2-description', 2, 2, now() - INTERVAL '1 DAY', now() + INTERVAL '5 DAY', null,
        'IN_PROGRESS', false),
       (3, 'wish-title3', 'wish3-description', 3, 3, now() - INTERVAL '2 DAY', now() + INTERVAL '4 DAY', null,
        'CANCELLED', false),
       (1, 'wish-title4', 'wish4-description', 4, 4, now() - INTERVAL '3 DAY', now() + INTERVAL '3 DAY', now(),
        'EXECUTED', false),
       (5, 'wish-title5', 'wish5-description', 5, null, now() - INTERVAL '4 DAY', now() + INTERVAL '2 DAYS', null,
        'OPEN', false),
       (1, 'wish-title6', 'wish6-description', 5, null, now() - INTERVAL '7 DAY', now() + INTERVAL '1 DAY', null,
        'OPEN', false),
       (1, 'wish-title7', 'wish7-description', 5, null, now() - INTERVAL '9 DAY', now() + INTERVAL '1 DAY', null,
        'OPEN', false),
       (1, 'wish-title8', 'wish8-description', 5, null, now() - INTERVAL '8 DAY', now() + INTERVAL '1 DAY', null,
        'OPEN', false);

insert into wish_comment (wish_id, description, creator_id, create_date)
values (1, 'wishComment1-description', 1, now()),
       (1, 'wishComment2-description', 1, now()),
       (1, 'wishComment3-description', 1, now()),
       (2, 'wishComment4-description', 2, now()),
       (2, 'wishComment5-description', 2, now()),
       (2, 'wishComment6-description', 2, now()),
       (3, 'wishComment7-description', 3, now()),
       (3, 'wishComment8-description', 3, now()),
       (3, 'wishComment9-description', 3, now()),
       (4, 'wishComment10-description', 4, now());

insert into claim (title, description, creator_id, executor_id, create_date, plan_execute_date, fact_execute_date,
                   status, deleted)
values ('title1', 'claim1-description', 1, null, now(), now() + INTERVAL '4 DAYS', null, 'OPEN', false),
       ('title2', 'claim2-description', 2, 2, now() - INTERVAL '1 DAY', now() + INTERVAL '2 DAY', null, 'IN_PROGRESS',
        false),
       ('title3', 'claim3-description', 3, 3, now(), now(), now(), 'EXECUTED', false),
       ('title4', 'claim4-description', 4, 4, now(), now() + INTERVAL '2 DAYS', null, 'IN_PROGRESS', false),
       ('title5', 'claim5-description', 5, 5, now(), now() + INTERVAL '3 DAYS', null, 'IN_PROGRESS', false);

insert into claim_comment (claim_id, description, creator_id, create_date)
values (1, 'claim1-description', 1, now() - INTERVAL '4 DAYS'),
       (1, 'claim2-description', 2, now() - INTERVAL '1 DAY'),
       (2, 'claim3-description', 3, now()),
       (3, 'claim4-description', 5, now() - INTERVAL '2 DAYS'),
       (3, 'claim5-description', 5, now());


insert into news (news_category_id, title, description, creator_id, create_date, publish_date, publish_enabled, deleted)
values (1, 'news-title1', 'news-description1', 1, now() - INTERVAL '8 DAY', now(), true, false),
       (2, 'news-title2', 'news-description2', 1, now() - INTERVAL '7 DAY', now() - INTERVAL '7 DAY', true, false),
       (3, 'news-title3', 'news-description3', 1, now() - INTERVAL '6 DAY', now() - INTERVAL '6 DAY', true, false),
       (4, 'news-title4', 'news-description4', 1, now() - INTERVAL '5 DAY', now() - INTERVAL '5 DAY', true, false),
       (5, 'news-title5', 'news-description5', 1, now() - INTERVAL '4 DAY', now() - INTERVAL '4 DAY', true, false),
       (6, 'news-title6', 'news-description6', 1, now() - INTERVAL '3 DAY', now() - INTERVAL '3 DAY', true, false),
       (7, 'news-title7', 'news-description7', 1, now() - INTERVAL '2 DAY', now() - INTERVAL '2 DAY', true, false),
       (8, 'news-title8', 'news-description8', 1, now() - INTERVAL '1 DAY', now() - INTERVAL '1 DAY', true, false),
       (8, 'news-title9', 'news-description9', 1, now() - INTERVAL '1 DAY', now() - INTERVAL '1 HOUR', false, false);


insert into user_role (user_id, role_id, deleted)
values (1, 1, false),
       (1, 2, false),
       (2, 1, false),
       (3, 2, false),
       (4, 2, false),
       (5, 1, false);

insert into wish_visibility (wish_id, role_id, deleted)
values (1, 1, false),
       (1, 2, false),
       (2, 1, false),
       (2, 2, false),
       (3, 1, false),
       (4, 1, false),
       (5, 1, false),
       (6, 1, false),
       (6, 2, false),
       (7, 2, false),
       (8, 1, false),
       (8, 2, false);