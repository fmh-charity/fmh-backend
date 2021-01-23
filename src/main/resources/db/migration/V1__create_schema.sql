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
	id          int not null primary key default nextval('note_seq'),
	text        varchar not null,
	patient_id  int not null,
	author_id   int not null,
	doer_id     int,
	date_create date,
	date_update date,
	date_end    date,
	comment     varchar
);

comment on table note is 'записки';

create sequence employee_seq;
create table employee
(
	id          int not null primary key default nextval('employee_seq'),
	first_name  varchar(100),
	middle_name varchar(100),
	last_name   varchar(100)
);

comment on table employee is 'пользователи';

create sequence claim_seq;
create table claim
(
	id          int not null primary key default nextval('claim_seq'),
	text        varchar not null,
	author_id   int not null,
	doer_id     int,
	date_create date,
	date_update date,
	date_end    date,
	comment     varchar
);

create sequence advertisement_seq;
create table advertisement
(
	id          int not null primary key default nextval('advertisement_seq'),
	author_id   int,
	description varchar,
	title       varchar,
	date_create date
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

