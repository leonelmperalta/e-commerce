CREATE DATABASE "E_COMMERCE"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

\connect e_commerce;

CREATE SCHEMA IF NOT EXISTS public
    AUTHORIZATION pg_database_owner;

COMMENT ON SCHEMA public
    IS 'standard public schema';

GRANT USAGE ON SCHEMA public TO PUBLIC;

GRANT ALL ON SCHEMA public TO pg_database_owner;

CREATE TABLE IF NOT EXISTS public."CART"
(
    "DNI" bigint NOT NULL,
    "IS_SPECIAL" boolean NOT NULL,
    "CREATION_DATE" date NOT NULL,
    CONSTRAINT "CART_pkey" PRIMARY KEY ("DNI")
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."CART"
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public."PRODUCT"
(
    "ID" bigint NOT NULL,
    "CART_DNI" bigint NOT NULL,
    "NAME" "char"[] NOT NULL,
    "UNIT_PRICE" real NOT NULL,
    "QUANTITY" integer NOT NULL,
    "BUY_DATE" date NOT NULL,
    CONSTRAINT "PRODUCT_pkey" PRIMARY KEY ("ID")
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."PRODUCT"
    OWNER to postgres;

ALTER TABLE IF EXISTS public."PRODUCT"
    ADD CONSTRAINT "PRODUCT_CART_FK" FOREIGN KEY ("CART_DNI")
        REFERENCES public."CART" ("DNI") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;
