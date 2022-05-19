-- create sequence hibernate_sequence start 1 increment 1;

create table user_role
(
    user_id int8 not null,
    roles   varchar(255) not null
);

create table users
(
    id              bigserial not null,
    active          boolean not null,
    password        varchar(255) not null,
    username        varchar(255) not null unique,
    primary key (id)
);

alter table if exists user_role
    add constraint userrole_user_fk
    foreign key (user_id) references users;