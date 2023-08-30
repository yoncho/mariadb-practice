--
-- select 기본 연습
--
-- 예1) departments 테이블 모든 데이터 출력
select * from departments;

-- 프로젝션(projection)
-- 예2) employees 테이블에서 직원의 이름, 성별, 입사일 출력
select first_name as '이름', gender as '성', hire_date as '입사일' from employees;

-- distinct
-- 예3) titles 테이블에서 모든 직급을 출력해라
select title from titles;
-- 예4) titles 테이블에서 직급은 어떤 것들이 있는지 직급 이름을 한번씩만 출력해라
select distinct title from titles;


-- 
-- where 절
--
-- 예제1) 비교 연산자 : employees 테이블에서 1991년 이전 입사한 직원의 이름, 
select first_name, gender, hire_date
from employees 
where hire_date <='1990-12-31'
order by hire_date desc;

-- 예제2) 논리 연산자 : employees 테이블에서 1989년 이전 입사한 여직원의 이름
select first_name, gender, hire_date
from employees
where hire_date <'1989-01-01' and gender='F'
order by hire_date desc;

-- 예제3) in 연산자 : dept_emp 테이블에서 부서 번호가 d005 이거나 d009에 속한 사원의 이름, 
select emp_no, dept_no 
from dept_emp 
where dept_no='d005' or dept_no='d009';

select emp_no, dept_no 
from dept_emp 
where dept_no in ('d005','d009');

-- 예제4) like 검색: employees 테이블에서 1989년에 입사한 직원들의 이름, 입사일 출력
select first_name, hire_date 
from employees 
where hire_date like '1989-%'
order by hire_date desc;

-- between 사용 예제..
select first_name, hire_date 
from employees 
where hire_date between '1989-01-01' and '1989-12-31'
order by hire_date desc;

--
-- order by
--
-- 예제1) employees 테이블에서 이름, 성별, 입사일을 입사일 빠른 순으로 출력
select concat(first_name, ' ', last_name) as '이름', gender as '성', hire_date as '입사일' 
from employees
order by hire_date;

-- 예제2) salaries 테이블에서 2001년 월급이 가장 높은 순으로 사번 출력
select emp_no, salary, from_date ,to_date from salaries
where to_date like '2001%' or from_date like '2001%'
order by salary desc;

-- 예제3) 남자 직원의 이름,성별, 입사일을 선임순으로 출력
select first_name, gender, hire_date from employees where gender='m' order by hire_date;

-- 예제4) 직원들의 사번, 월급을 사번순으로 출력하되 같은 직원의 월급이 높은 순으로 반영
select emp_no, salary 
from salaries 
order by emp_no asc, salary desc; -- 여러 colum을 order by 할 수 있음...!