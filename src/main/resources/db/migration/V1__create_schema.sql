create sequence patient_seq;
CREATE TABLE patient
(
    id          int not null primary key default nextval('patient_seq'),
    first_name  varchar(100),
    middle_name varchar(100),
    last_name   varchar(100),
    birthday    date
);

comment on table patient is 'пациент';

comment on column patient.id is 'Ид в системе';
comment on column patient.first_name is 'Фамилия';
comment on column patient.middle_name is 'Имя';
comment on column patient.last_name is 'Отчество';
comment on column patient.birthday is 'дата рождения';

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
    status_id         int     not null,
    comment           varchar
);

comment on table note is 'записки';
comment on column note.id is 'Ид в системе';
comment on column note.patient_id is 'Ид в таблице Пациент';
comment on column note.description is 'описание';
comment on column note.creator_id is 'автор';
comment on column note.executor_id is 'исполнитель';
comment on column note.create_date is 'дата создания';
comment on column note.plan_execute_time is 'плановое время исполнителя';
comment on column note.fact_execute_time is 'фактическое время исполнения';
comment on column note.status_id is 'статус';
comment on column note.comment is 'комментарий';

create sequence user_seq;
create table users
(
    id           int not null primary key default nextval('user_seq'),
    login        varchar,
    password     varchar,
    first_name   varchar(100),
    middle_name  varchar(100),
    last_name    varchar(100),
    phone_number varchar,
    email        varchar,
    status_id    int not null
);

comment on table users is 'пользователи';
comment on column users.id is 'Ид в системе';
comment on column users.login is 'логин';
comment on column users.password is 'пароль';
comment on column users.first_name is 'Фамилия';
comment on column users.middle_name is 'имя';
comment on column users.last_name is 'отчество';
comment on column users.phone_number is 'тел. номер';
comment on column users.email is 'почта';
comment on column users.status_id is 'статус';

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
    comment           varchar,
    status_id         int     not null
);
comment on table claim is 'заявки';
comment on column claim.id is 'Ид в системе';
comment on column claim.description is 'описание';
comment on column claim.creator_id is 'автор';
comment on column claim.executor_id is 'исполнитель';
comment on column claim.create_data is 'дата создания';
comment on column claim.plan_execute_date is 'плановое время исполнителя';
comment on column claim.fact_execute_date is 'фактическое время исполнения';
comment on column claim.comment is 'комментарий';
comment on column claim.status_id is 'статус';

create sequence advertisement_seq;
create table advertisement
(
    id          int not null primary key default nextval('advertisement_seq'),
    creator_id  int,
    description varchar,
    title       varchar,
    create_data date,
    status_id   int not null
);
comment on table advertisement is 'новости';
comment on column advertisement.id is 'Ид в системе';
comment on column advertisement.creator_id is 'автор';
comment on column advertisement.description is 'описание';
comment on column advertisement.title is 'заголовок';
comment on column advertisement.create_data is 'дата создания';
comment on column advertisement.status_id is 'статус';

create table status
(
    id   int not null primary key,
    name varchar,
    code varchar
);

comment on table status is 'статус';
comment on column status.id is 'Ид в системе';
comment on column status.name is 'наименование';
comment on column status.code is 'код';

-- TODO свериться с  ER
create sequence admission_seq;
create table admission
(
    id         int  not null primary key default nextval('admission_seq'),
    patient_id int  not null,
    date_from  date not null,
    date_to    date,
    fact_in    boolean,
    fact_out   boolean,
    comment    varchar
);

create table role
(
    id   int not null primary key,
    name varchar,
    code varchar
);

create sequence block_seq;
create table block
(
    id      int not null primary key default nextval('block_seq'),
    name    varchar,
    symbol  bytea,
    comment varchar,
    deleted boolean
);
comment on table block is 'блоки';
comment on column block.id is 'Ид в системе';
comment on column block.name is 'наименование блока';
comment on column block.symbol is '???';
comment on column block.comment is '????';
comment on column block.deleted is 'флаг удаления';

create sequence nurseStation_seq;
create table nurseStation
(
    id      int not null primary key default nextval('nurseStation_seq'),
    name    varchar,
    comment varchar,
    deleted boolean
);
comment on table nurseStation is 'посты';
comment on column nurseStation.id is 'Ид в системе';
comment on column nurseStation.name is 'наименование';
comment on column nurseStation.comment is '????';
comment on column nurseStation.deleted is 'флаг удаления';

create sequence room_seq;
create table room
(
    id                int not null primary key default nextval('room_seq'),
    block_id          int,
    nurse_station_id  int,
    max_capacity      int,
    current_occupancy int,
    room_name         varchar,
    comment           varchar,
    deleted           boolean
);
comment on table room is 'палаты';
comment on column room.id is 'Ид в системе';
comment on column room.block_id is 'блок';
comment on column room.nurse_station_id is 'станция нянечек';
comment on column room.max_capacity is 'макс вместимость';
comment on column room.current_occupancy is 'текущая заполненность';
comment on column room.room_name is 'наименование';
comment on column room.comment is 'Комментарии';
comment on column room.deleted is 'флаг удаления';




