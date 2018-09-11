create table requests(
idr int,
val1 double precision,
val2 double precision,
op varchar,
primary key(idr)
);

create table answers(
ida int,
op varchar,
res double precision,
dat varchar,
primary key(ida)
);