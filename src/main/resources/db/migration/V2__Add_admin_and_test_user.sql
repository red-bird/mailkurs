insert into users (id, username, password, active)
values (nextval('users_id_seq'), 'admin', 'admin', true);

insert into user_role (user_id, roles)
values (1, 'ADMIN');

insert into users (id, username, password, active)
values (nextval('users_id_seq'), 'user', 'pass', true);

insert into user_role (user_id, roles)
values (2, 'USER');