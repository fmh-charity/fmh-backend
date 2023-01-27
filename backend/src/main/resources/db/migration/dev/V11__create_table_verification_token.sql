create table verification_token
(
    id          serial primary key,
    user_id     int references users not null,
    token       varchar(64) not null,
    expiry_date timestamp not null
);