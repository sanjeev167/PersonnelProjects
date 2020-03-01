-- Table: country_master

-- DROP TABLE country_master;

CREATE TABLE country_master
(
  id serial NOT NULL,
  country_name character varying(50),
  CONSTRAINT country_master_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE country_master
  OWNER TO postgres;

-- Table: state_master

-- DROP TABLE state_master;

CREATE TABLE state_master
(
  id serial NOT NULL,
  state_name character varying(50),
  country_id integer,
  CONSTRAINT state_master_pkey PRIMARY KEY (id),
  CONSTRAINT state_master_country_id_fkey FOREIGN KEY (country_id)
      REFERENCES country_master (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE state_master
  OWNER TO postgres;



-- Table: city_master

-- DROP TABLE city_master;

CREATE TABLE city_master
(
  id serial NOT NULL,
  city_name character varying(50),
  state_id integer,
  CONSTRAINT city_master_pkey PRIMARY KEY (id),
  CONSTRAINT city_master_state_id_fkey FOREIGN KEY (state_id)
      REFERENCES state_master (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE city_master
  OWNER TO postgres;
