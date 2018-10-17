create table request(
requestId serial not null,
value1 double precision,
value2 double precision,
operation varchar,
primary key(requestId)
);

create table answer(
answerId serial not null,
operation varchar,
result double precision,
date varchar,
primary key(answerId)
);
