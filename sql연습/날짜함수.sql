--
-- 날짜 함수
--

-- curdate(),  current_date 상수
select curdate(), current_date from dual;

-- now() vs sysdate();
select now(), sysdate() from dual;
select now(), sleep(2), sysdate() from dual;

-- date_format 
-- 2023년 08월 30일 11시 37분 57초
select date_format(now(), '%Y년 %m월 %d일 %H시간 %i분 %s초') from dual;

-- period_diff
-- formatting 조건 : yymm, YYYYmm
-- 예) 근무 개월 수 (2023년 기준)
select first_name, hire_date, period_diff(date_format(curdate(), '%y%m'), date_format(hire_date, '%y%m')) as month
from employees;
select period_diff() from dual;

-- date_add (=adddate), date_sub(=subdate)  [대상, interval 숫자 type]
-- 날짜를 date 타입의 칼럼이나 값에 type(year, month, day)의 표현식으로 더하거나 뺄 수 있다.
-- 예) 각 사원의 근속 년수가 5년이 되는 날에 휴가보내준다면 각 사원들의 근속 휴가 날짜는?
 select first_name, hire_date, date_add(hire_date, interval 5 year) as '휴가 가는 날'
 from employees;
 
-- cast
select '12345' + 10, cast('12345' as int) + 10 from dual;
select date_format(cast('2023-08-30' as date), '%y년 %m월 %d일') from dual; 
select cast(cast(1-2 as unsigned) as signed) from dual;
select cast(cast(1-2 as unsigned) as integer) from dual;

-- type
-- 문자: varchar(SIZE), char(SIZE), text, CLOB (char Large object)
--  varchar vs char : varchar는 늘려주거나 줄일 수 있다..! char는 고정!
-- 정수 : tiny, medium, int(signed, integer), unsigned int, big int\
-- 실수 : float, double
-- 시간 : date, datetime
-- LOB : CLOB, BLOB(Binary)



 