alter table admission
    rename column date_from to plan_date_in;
alter table admission
    rename column date_to to plan_date_out;
alter table admission
    rename column fact_in to fact_date_in;
alter table admission
    rename column fact_out to fact_date_out;


alter table admission
    alter column fact_date_in type date USING NULL,
    alter column fact_date_out type date USING NULL;

alter table patient add  column room_id int;

