ALTER TABLE block alter column name set  not null ;
CREATE UNIQUE INDEX block_name_index ON block (name);