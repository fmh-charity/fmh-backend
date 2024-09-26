create table wish_executors
(
    id          serial primary key,
    wish_id     int not null references wish,
    executor_id int references users,
    join_date   timestamp not null,
    finish_date timestamp not null
);
comment on table wish_executors is 'Исполнители просьбы';
comment on column wish_executors.id is 'id в системе';
comment on column wish_executors.wish_id is 'id просьбы';
comment on column wish_executors.executor_id is 'id исполнителя';
comment on column wish_executors.join_date is 'Дата и время присоединения к выполнению просьбы';
comment on column wish_executors.finish_date is 'Дата и время исполнения просьбы';

alter table wish add column help_request boolean default false, drop column executor_id;
comment on column wish.help_request is 'флаг удаления'