use employees;
drop table if exists reservations;
drop table if exists users;
drop table if exists rooms;


create table users (user_id int not null primary key auto_increment, first_name varchar(50), last_name varchar(50));
create table rooms (room_id int not null primary key auto_increment, room_name varchar(50), occupancy int);
create table reservations (reservation_id int not null primary key auto_increment, user_id int, room_id int, foreign key (user_id) references users (user_id), foreign key (room_id) references rooms (room_id));
