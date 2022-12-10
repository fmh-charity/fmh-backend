ALTER TABLE patient RENAME COLUMN birth_date TO birth_date_old;
ALTER TABLE patient ADD COLUMN birth_date date;
UPDATE patient SET birth_date = birth_date_old::date