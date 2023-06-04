
create database if not exists javaBig;
use tb_user;
create table tb_user(
    username varchar(20) not null primary key ,
    password varchar(20) not null,
    score int not null default 0
);
insert into tb_user values('admin','123456',10);