create table profile
(
    id              serial primary key,
    first_name      varchar(100),
    last_name       varchar(100),
    middle_name     varchar(100),
    email           varchar,
    deleted         boolean          default false,
    date_of_birth   date,
    email_confirmed boolean not null default false
);

insert into profile(id, first_name, last_name, middle_name, email, deleted, date_of_birth, email_confirmed)
select id, first_name, last_name, middle_name, email, deleted, date_of_birth, email_confirmed
from users;

alter table users
    drop column first_name;

alter table users
    drop column last_name;

alter table users
    drop column middle_name;

alter table users
    drop column phone_number;

alter table users
    drop column email;

alter table users
    drop column email_confirmed;

alter table users
    drop column date_of_birth;

alter table users
    add column profile_id int references profile;

update users set profile_id = (select id from profile where users.id = profile.id)
