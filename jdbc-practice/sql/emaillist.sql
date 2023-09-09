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

desc user;
insert into user values (null,"둘리","dooly@naver.com",password(1234),'male',current_date());
select * from user;
delete from user;

select * from guestbook;
update guestbook 
set name="yoncho", password=password('12345')
 where no=1;

-- login
select no, name 
from user 
where email='gogildong@naver.com' 
and password=password('1234');

select * from user;
delete from user;

select * from guestbook;
delete from guestbook;
