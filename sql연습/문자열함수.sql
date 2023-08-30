--
-- 문자열 함수
--
-- uppder() 소문자 -> 대문자
select upper('seoul'), ucase('SeOuL') from dual;
select upper(first_name) from employees;

-- lower() 대문자 -> 소문자
select lower('SEOUL'), lcase('SeOuL') from dual;
select lower(first_name) from employees;

-- substring(문자열, index, length) 
select substring('hello world!', 1, 8) from dual;

-- 예제) employees 테이블에서 1989년에 입사한 직원들의 이름, 입사일 출력
select first_name, hire_date 
from employees 
where substring(hire_date, 1,4) = '1989'
order by hire_date desc;

-- lpad, rpad (정렬 함수) 
-- lpad(문자열, 총 자릿수, 빈칸을 채울 값) 오른쪽 정렬
select lpad('1234', 10,'x'), rpad('1234', 10, 'x') from dual;

-- 예제) 직원들의 월급을 오른쪽 정렬을 해라 (빈공간은 '*' 표시)
select lpad(salary, 10, '*') from salaries;

-- trim, ltrim, rtrim (공백 제거)
select 
concat('----', ltrim('   1234    '), '----'),
concat('----', rtrim('   1234    '), '----'),
concat('----', trim(leading 'x' from 'xxx1234xxx'), '----') ,
concat('----', trim(trailing 'x' from 'xxx1234xxx'), '----') ,
concat('----', trim(both 'x' from 'xxx1234xxx'), '----') 
from dual;

-- length() 길이 
select length('hello my world') from dual;

