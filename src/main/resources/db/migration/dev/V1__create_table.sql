create sequence block_seq;
create table block
(
    id      int not null primary key default nextval('block_seq'),
    name    varchar,
    comment varchar,
    deleted boolean
);
comment on table block is 'блоки';
comment on column block.id is 'id в системе';
comment on column block.name is 'наименование блока';
comment on column block.comment is 'комментарий';
comment on column block.deleted is 'флаг удаления';


create sequence nurseStation_seq;
create table nurse_station
(
    id      int not null primary key default nextval('nurseStation_seq'),
    name    varchar,
    comment varchar,
    deleted boolean
);
comment on table nurse_station is 'посты';
comment on column nurse_station.id is 'id в системе';
comment on column nurse_station.name is 'наименование';
comment on column nurse_station.comment is 'комментарий';
comment on column nurse_station.deleted is 'флаг удаления';


create sequence room_seq;
create table room
(
    id               int not null primary key default nextval('room_seq'),
    name             varchar,
    block_id         int,
    nurse_station_id int,
    max_occupancy    int,
    comment          varchar,
    deleted          boolean
);
comment on table room is 'палаты';
comment on column room.id is 'id в системе';
comment on column room.name is 'наименование';
comment on column room.block_id is 'id блока';
comment on column room.nurse_station_id is 'id поста';
comment on column room.max_occupancy is 'макс вместимость';
comment on column room.comment is 'Комментарии';
comment on column room.deleted is 'флаг удаления';


create sequence admission_seq;
create table admission
(
    id            int  not null primary key default nextval('admission_seq'),
    patient_id    int  not null,
    plan_date_in  date not null,
    plan_date_out date,
    fact_date_in  date,
    fact_date_out date,
    status        varchar,
    room_id       int,
    comment       varchar,
    deleted       boolean
);
comment on table admission is 'госпитализации';
comment on column admission.id is 'id в системе';
comment on column admission.patient_id is 'id пациента';
comment on column admission.plan_date_in is 'Планируемая дата поступления';
comment on column admission.plan_date_out is 'Планируемая дата выписки';
comment on column admission.fact_date_in is 'Фактическая  дата поступления';
comment on column admission.fact_date_out is 'Фактическая дата выписки';
comment on column admission.status is 'статуса госпитализации';
comment on column admission.room_id is 'id палаты';
comment on column admission.comment is 'комментарий';
comment on column admission.deleted is 'флаг удаления';


create sequence patient_seq;
CREATE TABLE patient
(
    id                   int not null primary key default nextval('patient_seq'),
    first_name           varchar(100),
    last_name            varchar(100),
    middle_name          varchar(100),
    birth_date           date,
    current_admission_id int,
    deleted              boolean
);
comment on table patient is 'пациент';
comment on column patient.id is 'id в системе';
comment on column patient.first_name is 'имя';
comment on column patient.middle_name is 'отчество';
comment on column patient.last_name is 'фамилия';
comment on column patient.birth_date is 'дата рождения';
comment on column patient.current_admission_id is 'id актуальной госпитализации';
comment on column patient.deleted is 'флаг удаления';


create sequence note_seq;
create table note
(
    id                int     not null primary key default nextval('note_seq'),
    patient_id        int     not null,
    description       varchar not null,
    creator_id        int     not null,
    executor_id       int,
    create_date       timestamp,
    plan_execute_time timestamp,
    fact_execute_time timestamp,
    status            varchar not null,
    comment           varchar,
    deleted           boolean
);
comment on table note is 'записки';
comment on column note.id is 'id в системе';
comment on column note.patient_id is 'id в таблице Пациент';
comment on column note.description is 'описание';
comment on column note.creator_id is 'id автора';
comment on column note.executor_id is 'id исполнителя';
comment on column note.create_date is 'дата создания';
comment on column note.plan_execute_time is 'плановое время исполнения';
comment on column note.fact_execute_time is 'фактическое время исполнения';
comment on column note.status is 'статус';
comment on column note.comment is 'комментарий';
comment on column note.deleted is 'флаг удаления';


create sequence user_seq;
create table users
(
    id           int not null primary key default nextval('user_seq'),
    login        varchar,
    password     varchar,
    first_name   varchar(100),
    last_name    varchar(100),
    middle_name  varchar(100),
    phone_number varchar,
    email        varchar,
--    status_id    int not null, -- в диаграмме этого поля нет
    deleted      boolean
);
comment on table users is 'пользователи';
comment on column users.id is 'id в системе';
comment on column users.login is 'логин';
comment on column users.password is 'пароль';
comment on column users.first_name is 'имя';
comment on column users.middle_name is 'отчество';
comment on column users.last_name is 'фамилия';
comment on column users.phone_number is 'тел. номер';
comment on column users.email is 'почта';
-- comment on column users.status_id is 'статус';--в диаграмме этого поля нет
comment on column users.deleted is 'флаг удаления';


create sequence claim_seq;
create table claim
(
    id                int     not null primary key default nextval('claim_seq'),
    description       varchar not null,
    creator_id        int     not null,
    executor_id       int,
    create_data       date,
    plan_execute_date date,
    fact_execute_date date,
    status            int     not null,
    comment           varchar,
    deleted           boolean
);
comment on table claim is 'заявки';
comment on column claim.id is 'id в системе';
comment on column claim.description is 'описание';
comment on column claim.creator_id is 'id автора';
comment on column claim.executor_id is 'id исполнителя';
comment on column claim.create_data is 'дата создания';
comment on column claim.plan_execute_date is 'плановое время исполнителя';
comment on column claim.fact_execute_date is 'фактическое время исполнения';
comment on column claim.status is 'статус';
comment on column claim.comment is 'комментарий';
comment on column claim.deleted is 'флаг удаления';


create sequence advertisement_seq;
create table advertisement
(
    id          int not null primary key default nextval('advertisement_seq'),
    creator_id  int,
    title       varchar,
    description varchar,
    create_data date,
    deleted     boolean
);
comment on table advertisement is 'новости';
comment on column advertisement.id is 'id в системе';
comment on column advertisement.creator_id is 'id автора';
comment on column advertisement.description is 'описание';
comment on column advertisement.title is 'заголовок';
comment on column advertisement.create_data is 'дата создания';
comment on column claim.deleted is 'флаг удаления';


alter table admission add foreign key (patient_id) references patient;
alter table admission add foreign key (room_id) references room;
alter table advertisement add foreign key (creator_id) references users;
alter table claim add foreign key (creator_id) references users;
alter table claim add foreign key (executor_id) references users;
alter table note add foreign key (creator_id) references users;
alter table note add foreign key (executor_id) references users;
alter table note add foreign key (patient_id) references patient;
alter table patient add foreign key (current_admission_id) references admission;
alter table room add foreign key (block_id) references block;
alter table room add foreign key (nurse_station_id) references nurse_station;


-- ДАЛЬШЕ НЕ ПОКА НЕ ТРОГАЛ !
-- CREATE TYPE admissions_status AS ENUM ('Discharged', 'Active', 'Expected');
-- create sequence admissions_status_seq;
-- create table adm_status
-- (
--     id      int not null primary key default nextval('admissions_status_seq'),
--     name    admissions_status,
--     code    varchar,
--     deleted boolean
-- );
-- comment on table adm_status is 'статусы госпитализации';
-- comment on column adm_status.id is 'id в системе';
-- comment on column adm_status.name is 'название статуса в enum';
-- comment on column adm_status.code is 'код статуса';
-- comment on column adm_status.deleted is 'флаг удаления';
--
--
-- CREATE TYPE role_u AS ENUM ('Администратор', 'Медицинский работник', 'Волонтёр', 'Сотрудник АХЧ');
-- create sequence role_seq;
-- create table role
-- (
--     id      int not null primary key default nextval('role_seq'),
--     name    role_u,
--     code    varchar,
--     deleted boolean
-- );
-- comment on table role is 'роли';
-- comment on column role.id is 'id в системе';
-- comment on column role.name is 'название роли в enum';
-- comment on column role.code is 'код роли';
-- comment on column role.deleted is 'флаг удаления';
--
--
-- create sequence user_role_seq;
-- create table user_role
-- (
--     id      int not null primary key default nextval('user_role_seq'),
--     user_id int,
--     role_id int,
--     deleted boolean
-- );
-- comment on table user_role is 'роли пользователя';
-- comment on column user_role.id is 'id в системе';
-- comment on column user_role.user_id is 'id пользователя';
-- comment on column user_role.role_id is 'id роли';
-- comment on column role.deleted is 'флаг удаления';