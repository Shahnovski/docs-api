insert into users (id, username, password)
values (2, 'user', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.');
--password: 123456

insert into user_roles (user_id, roles)
values (2, 'USER');