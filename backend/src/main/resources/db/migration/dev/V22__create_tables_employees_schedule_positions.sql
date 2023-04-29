create table if not exists positions
(
    id            serial primary key,
    position_name varchar
);
comment on table positions is 'Должности';
comment on column positions.id is 'id в системе';
comment on column positions.position_name is 'Наименование должности';

create table if not exists employees
(
    id                  serial primary key,
    positions_id        int     not null references positions,
    description         varchar,
    active              boolean not null,
    schedule_type       varchar,
    work_start_time     time,
    work_end_time       time,
    schedule_start_date date,
    profile_id          int     not null references profile
);
comment on table employees is 'Сотрудники';
comment on column employees.id is 'id в системе';
comment on column employees.positions_id is 'id должности';
comment on column employees.description is 'Описание';
comment on column employees.active is 'флаг активности сотрудника (применяется при автоматическом заполнении графика)';
comment on column employees.schedule_type is 'Тип графика работы';
comment on column employees.work_start_time is 'Начало времени работы по графику';
comment on column employees.work_end_time is 'Окончание времени работы ро графику';
comment on column employees.schedule_start_date is 'Дата начала расчета графика (применяется для скользящих графиков работы)';
comment on column employees.profile_id is 'id профиля';

create table if not exists schedule
(
    id              serial primary key,
    employee_id     int         not null references employees,
    week            int         not null,
    month           varchar(20) not null,
    year            int         not null,
    date            date        not null,
    work_start_time time,
    work_end_time   time,
    employment_type varchar     not null
);
comment on table schedule is 'График работы';
comment on column schedule.id is 'id в системе';
comment on column schedule.employee_id is 'id сотрудника';
comment on column schedule.month is 'Месяц';
comment on column schedule.year is 'Год';
comment on column schedule.date is 'Дата';
comment on column schedule.work_start_time is 'Время начала работы';
comment on column schedule.work_end_time is 'Время окончания работы';
comment on column schedule.employment_type is 'Тип занятости';

create index employee_month_year_index
    on schedule (employee_id, month, year);
create index week_year_index
    on schedule (week, year);