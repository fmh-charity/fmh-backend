alter table patient
    add if not exists room_id int;
alter table patient
    add if not exists plan_date_in date;
alter table patient
    add if not exists plan_date_out date;
alter table patient
    add if not exists fact_date_in date;
alter table patient
    add if not exists fact_date_out date;
alter table patient
    add if not exists status varchar not null default 'EXPECTED';

comment on column patient.room_id is 'id палаты';
comment on column patient.plan_date_in is 'Планируемая дата поступления';
comment on column patient.plan_date_out is 'Планируемая дата выписки';
comment on column patient.fact_date_in is 'Фактическая  дата поступления';
comment on column patient.fact_date_out is 'Фактическая дата выписки';
comment on column patient.status is 'Статус пациента';

do
$$
    begin
        if not exists(select 1 from pg_constraint where conname = 'patient_room_id_fkey') then
            alter table patient
                add constraint patient_room_id_fkey
                    foreign key (room_id) references room (id);
        end if;
    end;
$$;

update patient
set (room_id, plan_date_in, plan_date_out, fact_date_in, fact_date_out, status) =
        (select room_id, plan_date_in::date, plan_date_out::date, fact_date_in::date, fact_date_out::date, status::date
         from admission
         where admission.id = patient.current_admission_id);

drop table admission cascade;
alter table patient
    drop column current_admission_id;