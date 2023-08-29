-- select 연습

-- 예1) departments 테이블 모든 데이터 출력
select * from departments;

-- 예2) 프로젝션(projection) 해당 colum만 출력 - employees 테이블에서 직원의 이름, 성별, 입사일 출력
select first_name as '이름', gender as '성', hire_date as '입사일' from employees;

-- 예3) distinct - titles 테이블에서 모든 직급을 출력해라
select distinct title from titles;

