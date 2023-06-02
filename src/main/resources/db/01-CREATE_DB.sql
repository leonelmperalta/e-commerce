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

create table cart
(
    dni           bigint                not null
        primary key
        unique,
    is_special    boolean               not null,
    creation_date date                  not null,
    closed        boolean default false not null,
    close_date    date
);

alter table cart
    owner to postgres;

create table product
(
    id         bigserial
        constraint "PRODUCT_pkey"
            primary key,
    name       text    not null,
    unit_price real    not null,
    quantity   integer not null,
    cart_dni   bigint  not null
        constraint product_cart_fk
            references cart
);

alter table product
    owner to postgres;