
/*
существующую колонку даты рождения переименовать в birth_date_old,
добавить новую колонку для даты рождения birth_date с типом данных date,
добавить запрос на заполнение данных из birth_date_old в birth_date
с преобразованием timestamp в date(birth_date_old::date)
*/
ALTER TABLE patient RENAME COLUMN birth_date TO birth_date_old;
ALTER TABLE patient ADD COLUMN birth_date date;
UPDATE patient SET birth_date = birth_date_old::date;
