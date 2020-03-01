
-- Table: user_reg

-- DROP TABLE user_reg;

CREATE TABLE user_reg
(
  id serial NOT NULL,
  email character varying(50),
  password character varying(10),
  CONSTRAINT user_reg_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_reg
  OWNER TO postgres;


-- Table: app_role

-- DROP TABLE app_role;

CREATE TABLE app_role
(
  id serial NOT NULL,
  role_name character varying(50),
  CONSTRAINT app_role_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE app_role
  OWNER TO postgres;

-- Table: app_group

-- DROP TABLE app_group;

CREATE TABLE app_group
(
  id serial NOT NULL,
  department_name character varying(50),
  group_name character varying(50),
  CONSTRAINT app_group_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE app_group
  OWNER TO postgres;



-- Table: app_group_role

-- DROP TABLE app_group_role;

CREATE TABLE app_group_role
(
  id serial NOT NULL,
  app_group_id integer,
  app_role_id integer,
  CONSTRAINT app_group_role_pkey PRIMARY KEY (id),
  CONSTRAINT app_group_role_app_group_id_fkey FOREIGN KEY (app_group_id)
      REFERENCES app_group (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT app_group_role_app_role_id_fkey FOREIGN KEY (app_role_id)
      REFERENCES app_role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE app_group_role
  OWNER TO postgres;


-- Table: user_group

-- DROP TABLE user_group;

CREATE TABLE user_group
(
  id serial NOT NULL,
  user_id integer,
  app_group_id integer,
  CONSTRAINT user_group_pkey PRIMARY KEY (id),
  CONSTRAINT user_group_app_group_id_fkey FOREIGN KEY (app_group_id)
      REFERENCES app_group (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_group_user_id_fkey FOREIGN KEY (user_id)
      REFERENCES user_reg (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_group
  OWNER TO postgres;



