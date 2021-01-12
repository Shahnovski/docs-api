create sequence hibernate_sequence start 80 increment 1;

create table doc (
    id int8 not null,
    doc_client_name varchar(50) not null,
    doc_creation_date timestamp not null,
    doc_deadline_date timestamp not null,
    doc_file_content oid not null,
    doc_file_name varchar(20) not null,
    doc_name varchar(20) not null,
    doc_status varchar(255) not null,
    doc_worker_name varchar(50) not null,
    user_id int8 not null,
    primary key (id)
);

create table user_roles (
    user_id int8 not null,
    roles varchar(255)
);

create table users (
    id int8 not null,
    password varchar(255),
    username varchar(255),
    active boolean not null,
    primary key (id)
);

alter table if exists doc
    add constraint doc_user_fk
    foreign key (user_id) references users;

alter table if exists user_roles
    add constraint user_roles_user_fk
    foreign key (user_id) references users;