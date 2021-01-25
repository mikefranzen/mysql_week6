use employees;
create table if not exists users (user_id int not null primary key auto_increment, first_name varchar(50), last_name varchar(50));
create table if not exists rooms (room_id int not null primary key auto_increment, room_name varchar(50) unique, occupancy int);
create table if not exists reservations (reservation_id int not null primary key auto_increment, user_id int, room_id int, reservation_date date, foreign key (user_id) references users (user_id), foreign key (room_id) references rooms (room_id));
insert into rooms (room_name, occupancy) values ('Camelback', 20), ('McDowwell', 30), ('Cactus', 25), ('Washington', 40), ('Jefferson', 25);

