-- FILL TABLES WITH DEV DATA --

truncate table rides restart identity;

insert into waypoints (altitude, longitude) values (49.98757713700418, 36.230671038618496);

insert into waypoints (altitude, longitude) values (50.00418617378455, 36.23775431349188);

insert into vehicles (location_id) values (1);

insert into riders (nickname) values ('pilot');
