alter table wish add column if not exists execution_initiator_id int default null references users;
