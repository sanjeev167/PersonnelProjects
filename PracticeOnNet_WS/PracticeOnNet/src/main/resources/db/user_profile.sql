
-- Table: user_address

-- DROP TABLE user_address;

CREATE TABLE user_address
(
  id serial NOT NULL,
  land_mark character varying(50),
  pin_code integer,
  street_address character varying(50),
  user_id integer,
  CONSTRAINT user_address_pkey PRIMARY KEY (id),
  CONSTRAINT user_address_user_id_fkey FOREIGN KEY (user_id)
      REFERENCES user_reg (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_address
  OWNER TO postgres;



-- Table: user_personel_details

-- DROP TABLE user_personel_details;

CREATE TABLE user_personel_details
(
  id serial NOT NULL,
  dob date,
  first_name character varying(50),
  gender character(1),
  height character(10),
  last_name character varying(50),
  phone integer,
  weight character(10),
  user_id integer,
  CONSTRAINT user_personel_details_pkey PRIMARY KEY (id),
  CONSTRAINT user_personel_details_user_id_fkey FOREIGN KEY (user_id)
      REFERENCES user_reg (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_personel_details
  OWNER TO postgres;

-- Table: app_group



