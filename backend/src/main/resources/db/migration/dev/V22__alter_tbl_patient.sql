insert into profile(id, first_name, last_name, middle_name, deleted, date_of_birth)
select id, first_name, last_name, middle_name, deleted, birth_date
from patient;

alter table patient
    drop column first_name;

alter table patient
    drop column last_name;

alter table patient
    drop column middle_name;

alter table patient
    drop column birth_date;

alter table patient
    add column profile_id int references profile;

update patient set profile_id = (select id from profile where patient.id = profile.id)