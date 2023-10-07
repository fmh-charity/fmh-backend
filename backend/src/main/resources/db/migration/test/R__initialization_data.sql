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

insert into profile (first_name, last_name, middle_name, email, date_of_birth)
values ('Николай', 'Смирнов', 'Петрович','login1@gmail.com', '01.15.1990'),
       ('Данил', 'Лебедев', 'Александрович', 'login2@gmail.com', '02.16.1991'),
       ('Егор', 'Горбунов', 'Богданович', 'login3@gmail.com', '03.17.1992'),
       ('Алия', 'Цветкова', 'Валерьяновна', 'login4@gmail.com', '04.18.1993'),
       ('Зоя', 'Прохорова', 'Альфредовна', 'login5@gmail.com', '05.19.1994');

insert into profile (first_name, last_name, middle_name, date_of_birth)
values ('PatientOnefirstname', 'PatientOnemiddlename', 'PatientOnelastname', now()),
       ('PatientTwofirstname', 'PatientTwomiddlename', 'PatientTwolastname', now()),
       ('PatientThreefirstname', 'PatientThreemiddlename', 'PatientThreelastname', now()),
       ('PatientFourfirstname', 'PatientFourmiddlename', 'PatientFourlastname', now()),
       ('PatientFivefirstname', 'PatientFivemiddlename', 'PatientFivelastname', now()),
       ('PatientSixfirstname', 'PatientSixmiddlename', 'PatientSixlastname', now());

insert into users (login, password, deleted, profile_id)
values ('login1', '$2a$10$/qkdAUtfdxMs.V5iil9xNO0Laa1uwdqDlDbIi.9X5I5.ieJ9nxk8G', false, 1),
       ('login2', '$2a$10$hwGTJx3p2yA0AnP.qxQCgey/w8pKn9YmGLtlV5w76O1n2c0bgDrDq', false, 2),
       ('login3', '$2a$10$zCwKmp.DN9bOb5tn.DVydOn1IidVmt7dgwjqefrLLkKpO7T7F18Hm', false, 3),
       ('login4', '$2a$10$dkp74SIWxF5XzutsqUkSMu7qcs/VXNlOHpaZQjrb6n2NIfs75Bll2', false, 4),
       ('login5', '$2a$10$NSeshtrQF4nWbNNazaREiuQVbNVReCvQH/KQo0qLj/RpeUl9yhhe2', false, 5);

insert into patient (profile_id, deleted, plan_date_in,
                     plan_date_out, fact_date_in, fact_date_out, status, room_id)
values (6, false, '01/01/2000', '01/01/2000', null, null, 'EXPECTED', 1),
       (7, false, '01/01/2000', '01/01/2000', null, null, 'EXPECTED', 1),
       (8, false, '01/01/2000', '01/01/2000', '01/01/2020', null, 'ACTIVE', 1),
       (9, false, '01/01/2000', '01/01/2000', '01/01/2020', null, 'ACTIVE', 1),
       (10, false, '01/01/2000', '01/01/2000', '01/01/2020', '01/01/2020', 'DISCHARGED', 1),
       (11, false, null, null, null, null, 'EXPECTED', null);

insert into wish (patient_id, title, description, creator_id, create_date, plan_execute_date,
                  fact_execute_date, status, deleted)
values (1, 'wish-title1', 'wish1-description', 1, now(), now(), null, 'OPEN', false),
       (2, 'wish-title2', 'wish2-description', 2, now() - INTERVAL '1 DAY', now() + INTERVAL '5 DAY', null,
        'IN_PROGRESS', false),
       (3, 'wish-title3', 'wish3-description', 3, now() - INTERVAL '2 DAY', now() + INTERVAL '4 DAY', null,
        'CANCELLED', false),
       (1, 'wish-title4', 'wish4-description', 4, now() - INTERVAL '3 DAY', now() + INTERVAL '3 DAY', now(),
        'EXECUTED', false),
       (5, 'wish-title5', 'wish5-description', 5, now() - INTERVAL '4 DAY', now() + INTERVAL '2 DAYS', null,
        'IN_PROGRESS', false),
       (1, 'wish-title6', 'wish6-description', 5, now() - INTERVAL '7 DAY', now() + INTERVAL '1 DAY', null,
        'OPEN', false),
       (1, 'wish-title7', 'wish7-description', 5, now() - INTERVAL '9 DAY', now() + INTERVAL '1 DAY', null,
        'OPEN', false),
       (1, 'wish-title8', 'wish8-description', 5, now() - INTERVAL '8 DAY', now() + INTERVAL '1 DAY', null,
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

insert into user_role (user_id, role_id)
values (1, 1),
       (1, 2),
       (2, 1),
       (3, 2),
       (4, 2),
       (5, 1);

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

insert into document (id, name, description, deleted, status, create_date, file_path, user_id)
values (1, 'document1', 'description1', false, 'NEW', '01/01/2023', '/documents/document1.doc', 1),
       (2, 'document2', 'description2', true, 'PUBLISHED', '01/02/2023', '/documents/document2.doc', 1),
       (3, 'document3', 'description3', false, 'PUBLISHED', '02/02/2023', '/documents/document3.doc', 2),
       (4, 'document4', 'description4', false, 'ARCHIVED', '01/02/2023', '/documents/document4.doc', 3),
       (5, 'document5', 'description5', false, 'PUBLISHED', '03/02/2023', '/documents/document5.doc', 4),
       (6, 'document6', 'description6', false, 'PUBLISHED', '03/02/2023', '/documents/document6.doc', 4),
       (7, 'document7', 'description7', false, 'PUBLISHED', '03/02/2023', '/documents/document7.doc', 4),
       (8, 'document8', 'description8', false, 'PUBLISHED', '03/02/2023', '/documents/document8.doc', 4),
       (9, 'document9', 'description9', false, 'PUBLISHED', '03/02/2023', '/documents/document9.doc', 4),
       (10, 'document10', 'description10', false, 'PUBLISHED', '03/02/2023', '/documents/document10.doc', 4),
       (11, 'document11', 'description11', false, 'PUBLISHED', '03/02/2023', '/documents/document11.doc', 4),
       (12, 'document12', 'description12', false, 'PUBLISHED', '03/02/2023', '/documents/document12.doc', 4),
       (13, 'document13', 'description13', false, 'PUBLISHED', '03/02/2023', '/documents/document13.doc', 4);

insert into user_role_claim(id, user_id, role_id, status, created_at, updated_at)
values (1, 3, 1, 'NEW', '03/02/2023', '03/02/2023'),
       (2, 4, 4, 'CONFIRMED', '01/02/2023', '03/02/2023');

insert into wish_executors (wish_id, executor_id, join_date, finish_date)
values (1, 1, now(), now() + interval '1 day'),
       (2, 1, now(), now() + interval '1 day'),
       (2, 2, now(), now() + interval '1 day'),
       (5, 3, now(), now() + interval '1 day');;