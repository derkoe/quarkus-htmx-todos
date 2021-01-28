create table TODOS (
    ID uuid not null,
    TITLE varchar(100) not null,
    COMPLETED boolean not null default false
);
