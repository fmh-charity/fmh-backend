alter table admission
    alter column fact_date_in type date USING NULL,
    alter column fact_date_out type date USING NULL;

alter table patient add  column room_id int;