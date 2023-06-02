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
    id            bigserial
        primary key
        unique,
    dni           bigint                not null,
    is_special    boolean               not null,
    creation_date date                  not null,
    closed        boolean default false not null,
    close_date    date,
    amount        real,
    discount      real,
    final_amount  integer
);

alter table cart
    owner to postgres;


create table product
(
    id         bigserial
        constraint "PRODUCT_pkey"
            primary key,
    cart_id    integer not null
        constraint product_cart_id_fk
            references cart,
    name       text    not null,
    unit_price real    not null,
    quantity   integer not null
);

alter table product
    owner to postgres;