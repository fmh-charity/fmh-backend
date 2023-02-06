--Добавить роли с кодами ROLE_VOLUNTEER(Волонтёр), ROLE_VOLUNTEER_COORDINATOR (Координатора волонтёров),
--ROLE_PATIENT (Пациент), ROLE_GUEST (Гость)
--Добавить признак isNeedConfirmation = true на всех ролях кроме гостя
insert into roles (name, deleted, description, need_confirm)
values ('ROLE_VOLUNTEER', false, 'Волонтёр', true),
       ('ROLE_VOLUNTEER_COORDINATOR', false, 'Координатор волонтёров', true),
       ('ROLE_PATIENT', false, 'Пациент', true),
       ('ROLE_GUEST', false, 'Гость', false);

update roles
set description = 'Администратор', need_confirm = true
where name = 'ROLE_ADMINISTRATOR';

update roles
set description = 'Медицинский работник', need_confirm = true
where name = 'ROLE_MEDICAL_WORKER';
