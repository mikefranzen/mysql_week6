use employees;
drop table if exists reservations;
drop table if exists users;
drop table if exists rooms;


create table users (user_id int not null primary key auto_increment, first_name varchar(50), last_name varchar(50));
create table rooms (room_id int not null primary key auto_increment, room_name varchar(50), occupancy int);
create table reservations (reservation_id int not null primary key auto_increment, user_id int, room_id int, reservation_date date, foreign key (user_id) references users (user_id), foreign key (room_id) references rooms (room_id));

insert into rooms (room_name, occupancy) values ('Camelback', 20), ('McDowwell', 30), ('Cactus', 25), ('Washngton', 40), ('Jefferson', 25);

\\ List my Reservations:
We must 1st ask which user are you
Enter first name:
Enter last name:
select user_id from users where first_name = ? and last_name = ?
select reservations.reservation_id, rooms.room_name, rooms.occupancy, reservations.reservation_date from rooms, reservations where rooms.room_id = reservations.room_id and reservations.user_id = ?;
where ? is the user_id


\\ Create a reservation:
We must 1st get their name, date required, and then list out the rooms available for them on that date
Enter first name:
Enter last name:
Enter date:
insert into users (first_name, last_name) values (? , ?);
select user_id from users where first_name = ? and last_name = ?;
Select Room:
select rooms.room_id, rooms.room_name, rooms.occupancy, reservations.reservation_date from rooms left outer join reservations on rooms.room_id = reservations.room_id where reservations.reservation_date is NULL or reservation_date <> ?;
Where ? Equals the date entered
Insert into reservations (user_id, room_id, reservation_date) values ( ?, ?, ?);

\\ Delete a reservation:
We must 1st ask which user are you
Enter first name:
Enter last name:
select user_id from users where first_name = ? and last_name = ?
select reservations.reservation_id, rooms.room_name, rooms.occupancy, reservations.reservation_date from rooms, reservations where rooms.room_id = reservations.room_id and reservations.user_id = ?;
where ? is the user_id
Select a reservation to delete:
delete from reservations where reservation_id = ?
where ? Is the reservation_id chosen from the list
