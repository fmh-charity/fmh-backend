create table document
(
    id                serial primary key,
    name              varchar    not null,
    description       varchar,
    deleted           boolean,
    status            varchar not null,
    create_date       timestamp,
    file_path varchar not null,
    user_id int
);
comment on table document is 'документы';
comment on column document.id is 'id в системе';
comment on column document.name is 'имя документа';
comment on column document.description is 'описание';
comment on column document.deleted is 'флаг удаления';
comment on column document.status is 'статус';
comment on column document.create_date is 'дата создания';
comment on column document.file_path is 'ссылка на документ';
comment on column document.user_id is 'id автора';

alter table document
    add foreign key (user_id) references users;