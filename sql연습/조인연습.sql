--
-- join
-- inner join/ outer join
-- join 조건은 전체 n개의 테이블이 있을 경우 n-1개의 join 조건이 생긴다.

-- innrt join
-- 예제1) 현재, 근무하고 있는 직원 이름과 직책을 모두 출력alter
select a.first_name, b.title
from employees a, titles b
where a.emp_no = b.emp_no	-- join 조건 
and b.to_date='9999-01-01'; -- row 선택 조건


-- 예제2) 현재, 근무하고 있는 직원 사번, 이름과 직책을 모두 출력하되 여성 엔지니어만 
select a.emp_no, a.first_name, b.title
from employees a, titles b
where a.emp_no=b.emp_no
and b.to_date='9999-01-01'
and a.gender='F'
and b.title='Engineer';
-- equijoin!! 

--
-- ANSI/ISO SQL 1999 JOIN 표준 문법
--

-- outer join 은 표준을 따라야하고,, inner join은 equijoin이 간략하게 해놓은거라 사용했었음..

-- 1) Join ~ On **중요!!
-- 예제: 현재, 직책별 평균 연봉을 큰 순서대로 출력
select t.title, avg(s.salary)
from titles t, salaries s
where t.emp_no=s.emp_no
and t.to_date='9999-01-01'
and s.to_date='9999-01-01'
group by t.title
order by avg(s.salary) desc;

select t.title, avg(s.salary)
from titles t 
inner join salaries s on t.emp_no=s.emp_no -- join 정석!  (join은 inner join에서 inner가 생략되어있음.)
where  t.to_date='9999-01-01'
and s.to_date='9999-01-01'
group by t.title
order by avg(s.salary) desc;

-- 2) Natural Join
-- join 대상이 되는 테이블들에 이름이 같은 공통 컬럼이 있는 경우 
-- 조인 조건을 명시하지않고 암묵적으로 조인이 된다.
-- 예제 : 현재 근무하고있는 직원의 이름과 직책을 출력
select e.first_name, t.title
from employees e join titles t on e.emp_no=t.emp_no
where t.to_date='9999-01-01';

select e.first_name, t.title
from employees e natural join titles t
where t.to_date='9999-01-01';


-- 3) Join ~ Using
-- Natural Join의 문제점 때문에 사용해야함...!!
-- 예제 : 현재 근무하고있는 직원의 직책과 연봉을 출력
select t.title, s.salary
from titles t natural join salaries s
where t.to_date='9999-01-01'
and s.to_date='9999-01-01';

-- 해결 1. join ~ using 사용
select t.title, s.salary
from titles t join salaries s using(emp_no)
where t.to_date='9999-01-01'
and s.to_date='9999-01-01';

-- 햐결 2. join ~ on 사용
select t.title, s.salary
from titles t join salaries s on t.emp_no=s.emp_no
where t.to_date='9999-01-01'
and s.to_date='9999-01-01'


