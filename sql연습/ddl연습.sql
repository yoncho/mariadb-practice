-- ddl
drop table member;
create table member(
	no int not null auto_increment, 
    email varchar(200) not null,
    passworkd varchar(64) not null,
    name varchar(100) not null,
    department varchar(100),
    primary key(no)
);

desc member;

-- column add
alter table member add column certificateNumber char(13) not null;
desc member;

-- column drop
alter table member drop column certificateNumber;
desc member;

-- 위치 지정... after...
alter table member add column certificateNumber char(13) not null after email;
desc member;

alter table member change column department dept varchar(200) not null;
desc member;

alter table member add column self_intro text;
desc member;

alter table member drop column certificateNumber;
desc memeber;


--
-- dml
-- 

-- insert/delete/update + transaction..?
