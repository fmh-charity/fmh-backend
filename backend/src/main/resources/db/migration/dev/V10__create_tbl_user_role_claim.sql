-- Создание таблицы заявок на роль
create table if not exists user_role_claim
(
    id         int PRIMARY KEY,
    user_id    int          NOT NULL references users(id),
    role_id    int          NOT NULL references roles(id),
    status     varchar      NOT NULL,
    created_at timestamp    NOT NULL,
    updated_at timestamp    NOT NULL
);

comment on table user_role_claim is 'Заявка на роль';
comment on column user_role_claim.id is 'Идентификатор записи';
comment on column user_role_claim.user_id is 'Идентификатор пользователя';
comment on column user_role_claim.role_id is 'Идентификатор роли';
comment on column user_role_claim.status is 'Статус заявки на роль';
comment on column user_role_claim.created_at is 'Время создания заявки на роль';
comment on column user_role_claim.updated_at is 'Время изменения заявки на роль';

