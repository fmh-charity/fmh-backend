insert into profile (id, first_name, last_name, middle_name, date_of_birth)
values (34, 'PatientOnefirstname', 'PatientOnemiddlename', 'PatientOnelastname', now()),
       (35, 'PatientTwofirstname', 'PatientTwomiddlename', 'PatientTwolastname', now()),
       (36, 'PatientThreefirstname', 'PatientThreemiddlename', 'PatientThreelastname', now()),
       (37, 'PatientFourfirstname', 'PatientFourmiddlename', 'PatientFourlastname', now()),
       (38, 'PatientFivefirstname', 'PatientFivemiddlename', 'PatientFivelastname', now()),
       (39, 'PatientSixfirstname', 'PatientSixmiddlename', 'PatientSixlastname', now());
update patient
set profile_id=34,
    first_name='PatientOnefirstname',
    last_name='PatientOnemiddlename',
    middle_name='PatientOnelastname',
    deleted= false,
    plan_date_in='01/01/2000',
    plan_date_out='01/01/2000',
    fact_date_in=null,
    fact_date_out=null,
    status='EXPECTED',
    room_id=1
where id = 1;
update patient
set profile_id=35,
    first_name='PatientTwofirstname',
    last_name='PatientTwomiddlename',
    middle_name='PatientTwolastname',
    deleted= false,
    plan_date_in='01/01/2000',
    plan_date_out='01/01/2000',
    fact_date_in=null,
    fact_date_out=null,
    status='EXPECTED',
    room_id=1
where id = 2;
update patient
set profile_id=36,
    first_name='PatientThreefirstname',
    last_name='PatientThreemiddlename',
    middle_name='PatientThreelastname',
    deleted= false,
    plan_date_in='01/01/2000',
    plan_date_out='01/01/2000',
    fact_date_in='01/01/2020',
    fact_date_out=null,
    status='ACTIVE',
    room_id=1
where id = 3;
update patient
set profile_id=37,
    first_name='PatientFourfirstname',
    last_name='PatientFourmiddlename',
    middle_name='PatientFourlastname',
    deleted= false,
    plan_date_in='01/01/2000',
    plan_date_out='01/01/2000',
    fact_date_in='01/01/2020',
    fact_date_out=null,
    status='ACTIVE',
    room_id=1
where id = 4;
update patient
set profile_id=38,
    first_name='PatientFivefirstname',
    last_name='PatientFivemiddlename',
    middle_name='PatientFivelastname',
    deleted= false,
    plan_date_in='01/01/2000',
    plan_date_out='01/01/2000',
    fact_date_in='01/01/2020',
    fact_date_out='01/01/2020',
    status='DISCHARGED',
    room_id=1
where id = 5;
update patient
set profile_id=39,
    first_name='PatientSixfirstname',
    last_name='PatientSixmiddlename',
    middle_name='PatientSixlastname',
    deleted= false,
    plan_date_in=null,
    plan_date_out=null,
    fact_date_in=null,
    fact_date_out=null,
    status='EXPECTED',
    room_id=null
where id = 6;

DELETE
FROM patient
WHERE id >= 7;