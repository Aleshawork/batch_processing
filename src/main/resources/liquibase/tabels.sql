drop table if exists CLIENT cascade;
 create table CLIENT (
       id int8 not null,
        name varchar(255),
        primary key (id)
    )