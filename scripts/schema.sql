-- CREATE TABLES --

create table waypoints
(
    id        bigserial primary key,
    altitude  decimal not null,
    longitude decimal not null,

    unique (altitude, longitude)
);

create table riders
(
    id       bigserial primary key,
    nickname text not null unique
);

create table vehicles
(
    id          bigserial primary key,
    location_id bigint not null references waypoints (id)
);

create table rides
(
    id          bigserial primary key,
    rider_id    bigint    not null references riders (id),
    vehicle_id  bigint    not null references vehicles (id),
    start_id    bigint    not null references waypoints (id),
    finish_id   bigint references waypoints (id),
    started_at  timestamp not null,
    finished_at timestamp,

    check ( finished_at is null or started_at <= finished_at )
);

create index on rides (rider_id);
create index on rides (vehicle_id);
create index on rides (started_at, finished_at);
