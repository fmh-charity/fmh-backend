
insert into block (id, name, comment, deleted)
values (1, 'block1', 'block1_comment', false),
       (2, 'block2', 'block2_comment', false),
       (3, 'block3', 'block3_comment', true);

insert into nurse_station (id, name, comment, deleted)
values (1, 'nurse-station1', 'nurse-station1-comment', false),
       (2, 'nurse-station2', 'nurse-station2-comment', false),
       (3, 'nurse-station3', 'nurse-station3-comment', true);

insert into room (id, name, block_id, nurse_station_id, max_occupancy, comment, deleted)
values (1, 'room1', 1, 1, 10, 'room1-comment', false),
       (2, 'room2', 1, 1, 10, 'room2-comment', false),
       (3, 'room3', 2, 2, 10, 'room3-comment', false),
       (4, 'room4', 2, 2, 10, 'room4-comment', false);

insert into patient (first_name, middle_name, last_name, birth_date, current_admission_id, deleted)
values ('Patient1-firstname', 'Patient1-middlename', 'Patient1-lastname', now(), null, false),
       ('Patient2-firstname', 'Patient2-middlename', 'Patient2-lastname', now(), null, false),
       ('Patient3-firstname', 'Patient3-middlename', 'Patient3-lastname', now(), null, false),
       ('Patient4-firstname', 'Patient4-middlename', 'Patient4-lastname', now(), null, false),
       ('Patient5-firstname', 'Patient5-middlename', 'Patient5-lastname', now(), null, false);

insert into admission (patient_id, plan_date_in, plan_date_out, fact_date_in, fact_date_out, status, room_id,
                       comment, deleted)
values (1, '01/01/2000', '01/01/2000', null, null, 'EXPECTED', 1, 'admission1-comment', false),
       (2, '01/01/2000', '01/01/2000', null, null, 'EXPECTED', 1, 'admission2-comment', false),
       (3, '01/01/2000', '01/01/2000', '01/01/2020', null, 'ACTIVE', 1, 'admission3-comment', false),
       (4, '01/01/2000', '01/01/2000', '01/01/2020', null, 'ACTIVE', 1, 'admission4-comment', false),
       (5, '01/01/2000', '01/01/2000', '01/01/2020', '01/01/2020', 'DISCHARGED', 1, 'admission5-comment', false);

-- СЛОЖНО
update patient set current_admission_id = 1 where id=1;
update patient set current_admission_id = 2 where id=2;
update patient set current_admission_id = 3 where id=3;
update patient set current_admission_id = 4 where id=4;
update patient set current_admission_id = 5 where id=5;

insert into users (id, login, password, first_name, last_name, middle_name, phone_number, email, deleted)
values (1, 'user1-login', 'user1-password', 'user1-firstname', 'user1-lasttname', 'user1-middletname', 'user1-phonenumber', 'user1-email', false),
       (2, 'user2-login', 'user2-password', 'user2-firstname', 'user2-lasttname', 'user2-middletname', 'user2-phonenumber', 'user2-email', false),
       (3, 'user3-login', 'user3-password', 'user3-firstname', 'user3-lasttname', 'user3-middletname', 'user3-phonenumber', 'user3-email', false),
       (4, 'user4-login', 'user4-password', 'user4-firstname', 'user4-lasttname', 'user4-middletname', 'user4-phonenumber', 'user2-email', false),
       (5, 'user5-login', 'user5-password', 'user5-firstname', 'user5-lasttname', 'user5-middletname', 'user5-phonenumber', 'user5-email', false);

insert into note (patient_id, description, creator_id, executor_id, create_date, plan_execute_time,
                  fact_execute_time, status, comment, deleted)
values (1, 'note1-description', 1, 1, now(), now(), now(), 'active', 'note1-comment', false),
       (2, 'note2-description', 2, 2, now(), now(), now(), 'active', 'note1-comment', false),
       (3, 'note3-description', 3, 3, now(), now(), now(), 'active', 'note1-comment', false),
       (4, 'note4-description', 4, 4, now(), now(), now(), 'executed', 'note1-comment', false),
       (5, 'note5-description', 5, 5, now(), now(), now(), 'active', 'note1-comment', true);





-- insert into block (id, name, comment, deleted)
-- values (1, 'block1', 'block1_comment', false),
--        (2, 'block2', 'block2_comment', false),
--        (3, 'block3', 'block3_comment', true);
--
-- insert into nurse_station (id, name, comment, deleted)
-- values (1, 'nurse-station1', 'nurse-station1-comment', false),
--        (2, 'nurse-station2', 'nurse-station2-comment', false),
--        (3, 'nurse-station3', 'nurse-station3-comment', true);
--
-- insert into room (id, name, block_id, nurse_station_id, max_occupancy, comment, deleted)
-- values (1, 'room1', 1, 1, 10, 'room1-comment', false),
--        (2, 'room2', 1, 1, 10, 'room2-comment', false),
--        (3, 'room3', 2, 2, 10, 'room3-comment', false),
--        (4, 'room4', 2, 2, 10, 'room4-comment', false);
--
-- insert into patient (id, first_name, middle_name, last_name, birth_date, current_admission_id, deleted)
-- values (1, 'Patient1-firstname', 'Patient1-middlename', 'Patient1-lastname', now(), null, false),
--        (2, 'Patient2-firstname', 'Patient2-middlename', 'Patient2-lastname', now(), null, false),
--        (3, 'Patient3-firstname', 'Patient3-middlename', 'Patient3-lastname', now(), null, false),
--        (4, 'Patient4-firstname', 'Patient4-middlename', 'Patient4-lastname', now(), null, false),
--        (5, 'Patient5-firstname', 'Patient5-middlename', 'Patient5-lastname', now(), null, false);
--
-- insert into admission (id, patient_id, plan_date_in, plan_date_out, fact_date_in, fact_date_out, status, room_id,
--                        comment, deleted)
-- values (1, 1, '01/01/2000', '01/01/2000', null, null, 'EXPECTED', 1, 'admission1-comment', false),
--        (2, 2, '01/01/2000', '01/01/2000', null, null, 'EXPECTED', 1, 'admission2-comment', false),
--        (3, 3, '01/01/2000', '01/01/2000', '01/01/2020', null, 'ACTIVE', 1, 'admission3-comment', false),
--        (4, 4, '01/01/2000', '01/01/2000', '01/01/2020', null, 'ACTIVE', 1, 'admission4-comment', false),
--        (5, 5, '01/01/2000', '01/01/2000', '01/01/2020', '01/01/2020', 'DISCHARGED', 1, 'admission5-comment', false);
--
-- -- СЛОЖНО
-- update patient set current_admission_id = 1 where id=1;
-- update patient set current_admission_id = 2 where id=2;
-- update patient set current_admission_id = 3 where id=3;
-- update patient set current_admission_id = 4 where id=4;
-- update patient set current_admission_id = 5 where id=5;
--
-- insert into users (id, login, password, first_name, last_name, middle_name, phone_number, email, deleted)
-- values (1, 'user1-login', 'user1-password', 'user1-firstname', 'user1-lasttname', 'user1-middletname', 'user1-phonenumber', 'user1-email', false),
--        (2, 'user2-login', 'user2-password', 'user2-firstname', 'user2-lasttname', 'user2-middletname', 'user2-phonenumber', 'user2-email', false),
--        (3, 'user3-login', 'user3-password', 'user3-firstname', 'user3-lasttname', 'user3-middletname', 'user3-phonenumber', 'user3-email', false),
--        (4, 'user4-login', 'user4-password', 'user4-firstname', 'user4-lasttname', 'user4-middletname', 'user4-phonenumber', 'user2-email', false),
--        (5, 'user5-login', 'user5-password', 'user5-firstname', 'user5-lasttname', 'user5-middletname', 'user5-phonenumber', 'user5-email', false);
--
-- insert into note (id, patient_id, description, creator_id, executor_id, create_date, plan_execute_time,
--                   fact_execute_time, status, comment, deleted)
-- values (1, 1, 'note1-description', 1, 1, now(), now(), now(), 'active', 'note1-comment', false),
--        (2, 2, 'note2-description', 2, 2, now(), now(), now(), 'active', 'note1-comment', false),
--        (3, 3, 'note3-description', 3, 3, now(), now(), now(), 'active', 'note1-comment', false),
--        (4, 4, 'note4-description', 4, 4, now(), now(), now(), 'executed', 'note1-comment', false),
--        (5, 5, 'note5-description', 5, 5, now(), now(), now(), 'active', 'note1-comment', true);


