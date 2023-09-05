select * from emaillist;

-- email list

-- insert

insert into emaillist values (null, 'cho', 'yon', 'yoncho@naver.com');
insert into emaillist values(null,'장','은영','gsjang0807@gmail.com');
insert into emaillist values(null, '김', '종혁', 'dawn@naver.com');
select * from emaillist;

-- findAll
select no, first_name, last_name, email from emaillist;

-- delete
delete from emaillist where email='yoncho@naver.com';