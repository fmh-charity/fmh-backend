create sequence patient_seq;
CREATE TABLE patient (
    id          int not null primary key default nextval('patient_seq'),
    first_name  varchar(100),
    middle_name varchar(100),
    last_name   varchar(100),
    birthday    date
);

comment on table patient is 'пациент';

create sequence note_seq;
create table note
(
	id                  int not null primary key default nextval('note_seq'),
	patient_id          int not null,
	description         varchar not null,
	creator_id          int not null,
	executor_id         int,
	create_date         timestamp,
	plan_execute_time   timestamp,
	fact_execute_time   timestamp,
	status_id           int not null,
	comment             varchar
);

comment on table note is 'записки';

create sequence user_seq;
create table users
(
	id              int not null primary key default nextval('user_seq'),
	login           varchar,
	password        varchar,
	first_name      varchar(100),
	middle_name     varchar(100),
	last_name       varchar(100),
	phone_number    varchar,
	email           varchar,
	status_id       int not null
);

comment on table users is 'пользователи';

create sequence claim_seq;
create table claim
(
	id                  int not null primary key default nextval('claim_seq'),
	description         varchar not null,
	creator_id          int not null,
	executor_id         int,
	create_data         date,
	plan_execute_date   date,
	fact_execute_date   date,
	comment             varchar,
	status_id           int not null
);

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

create table status
(
	id          int not null primary key,
	name        varchar,
	code        varchar
);

create sequence admission_seq;
create table admission
(
	id          int not null primary key default nextval('admission_seq'),
	patient_id  int not null,
	date_from   date not null,
	date_to     date,
	fact_in     boolean,
	fact_out    boolean,
	comment     varchar
);
comment on table admission is 'госпитализация';

create table role
(
	id          int not null primary key,
	name        varchar,
	code        varchar
);
