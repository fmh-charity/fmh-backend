alter table verification_token alter column id type varchar(64) using id::varchar(64);
alter table verification_token alter column id drop default;
update verification_token set id = token;
alter table verification_token drop column token;
drop sequence verification_token_id_seq;