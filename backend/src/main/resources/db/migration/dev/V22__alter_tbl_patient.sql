insert into profile (first_name, last_name, middle_name, deleted, date_of_birth)
select p.first_name, p.last_name, p.middle_name, p.deleted, p.birth_date
from patient p, profile pf
where p.first_name != pf.first_name and p.last_name != pf.last_name and p.middle_name != pf.middle_name and
      p.deleted != pf.deleted and p.birth_date != pf.date_of_birth;

alter table patient
    add column profile_id int references profile;

update patient p set profile_id = (
    select id from profile pf
    where p.first_name = pf.first_name and p.last_name = pf.last_name and p.middle_name = pf.middle_name and
            p.deleted = pf.deleted and p.birth_date = pf.date_of_birth);