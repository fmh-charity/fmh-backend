ALTER TABLE roles ADD COLUMN IF NOT EXISTS description varchar;
ALTER TABLE roles ADD COLUMN IF NOT EXISTS need_confirm boolean;
