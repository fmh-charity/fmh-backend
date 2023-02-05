alter table users add column date_of_birth date;
alter table user_role drop column deleted;
alter table users add unique (login);
SELECT MAX(id)+1 FROM user_role_claim;
create sequence user_role_claim_id_seq minvalue 1;
alter table user_role_claim alter id set default nextval('user_role_claim_id_seq');
ALTER SEQUENCE user_role_claim_id_seq OWNED BY user_role_claim.id;
alter table user_role add unique (user_id, role_id)