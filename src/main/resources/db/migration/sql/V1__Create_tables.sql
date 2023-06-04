DROP TABLE IF EXISTS people;
CREATE TABLE people(
id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
username varchar NOT NULL UNIQUE,
password varchar NOT NULL,
role varchar,
CONSTRAINT people_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS sensors;
CREATE TABLE sensors(
id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
username varchar NOT NULL UNIQUE,
password varchar NOT NULL,
role varchar,
CONSTRAINT sensors_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS measurements;
CREATE TABLE measurements(
id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
value real NOT NULL,
raining boolean NOT NULL,
"timestamp" timestamp without time zone NOT NULL,
sensor_name varchar NOT NULL,
sensor_id integer NOT NULL,
CONSTRAINT measurements_pkey PRIMARY KEY (id),
CONSTRAINT sensor_id_fkey FOREIGN KEY (sensor_id)
        REFERENCES sensors (id) MATCH SIMPLE
);