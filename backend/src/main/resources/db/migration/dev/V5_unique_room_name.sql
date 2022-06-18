ALTER TABLE room alter column name set  not null ;
CREATE UNIQUE INDEX room_name_index ON room (name);
