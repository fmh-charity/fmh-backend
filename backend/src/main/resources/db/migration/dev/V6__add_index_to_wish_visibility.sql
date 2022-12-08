create index wish_visibility_wish_id_index
    on wish_visibility (wish_id);
alter table wish_visibility
    add constraint wish_visibility_roles_id_fk
        foreign key (role_id) references roles;
alter table wish_visibility
    add constraint wish_visibility_wish_id_fk
        foreign key (wish_id) references wish;