select version(), current_date(), now() from dual;

-- 수학 함수도 사용 가능...!! (사칙연산...)
select sin(pi()/4), 1+2, abs(4) from dual;

-- 대소문자 구분 안함
select PI() FrOM DuaL; 

-- TABLE 생성 : DDL
CREATE TABLE pet(
	name varchar(100),
    owner varchar(20),
    species varchar(20),
    gender char(1),
    birth date,
    death date
);

-- shema 확인
show tables;
describe pet;
desc pet; -- table 구조 표시

-- TABLE 삭제 (DDL)
drop table pet;
show tables;


-- INSERT [C] (DML)
insert into pet values ('ssong','yoncho', 'cat', 'm', '2017.12.11', null);

-- SELECT [R] (DML)
select * from pet;

-- UPDATE [U] (DML)
update pet set name='ssong2' where name='ssong';

-- DELETE [D] (DML)
delete from pet where name='ssong2';



-- LOAD DATA (외부 파일 읽어오기) URL
load data local infile 'C:\\Users\\joyon\\Pet.txt' into table pet;

-- select 연습 문제
-- 문) bowser의 주인 이름
select owner from pet where name='Bowser';

