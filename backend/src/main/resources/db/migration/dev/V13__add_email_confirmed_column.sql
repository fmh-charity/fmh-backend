alter table users
add column if not exists email_confirmed  boolean not null default false ;
