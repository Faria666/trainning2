create table request(
requestId serial not null,
val1 double precision,
val2 double precision,
op varchar,
primary key(idr)
);

create table answer(
answerId serial not null,
op varchar,
res double precision,
dat varchar,
primary key(ida)
);
