-- ddl
drop table member;
create table member(
	no int not null auto_increment, 
    email varchar(200) not null,
    password varchar(64) not null,
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
desc member;



--
-- dml
-- 

-- insert/delete/update + transaction..?

insert 
into member(no, email, password, name, dept, self_intro)
values (null, 'yoncho@yoncho.com', password('1234'), 'yoncho2', '개발팀', null);


-- update 
update member 
set email='yoncho22@naver.com', name='choyon'
where no = 4;

select * from member;

-- delete
delete from member where no=4;
select * from member;

-- transaction begin
set autocommit=0;
select @@autocommit from dual;

insert 
into member(no, email, password, name, dept, self_intro)
values (null, 'yoncho@yoncho.com', password('1234'), 'yoncho4', '개발팀', null);

select no, email, dept from member;
commit;
select no, email, dept from member;