--
-- subquery
--

-- 1) select절의 컬럼 프로젝션의 서브쿼리, insert int t values((서브쿼리))
--    

-- 2) select의 from 절* : 서브쿼리의 결과를 table로 쓴다..!!
-- 
select a.n, a.s, a.r from (select now() as n, sysdate() as s, 3+1 as r from dual) a;

--
-- 3) select의 where 절(having 절)의 서브쿼리
--
-- 예제) 현재 Fai Bale이 근무하는 부서에서 근무하는 다른 직원의 사번, 이름을 출력

-- sol1) 비추..
select de.dept_no
from employees e, dept_emp de 
where e.emp_no=de.emp_no 
and de.to_date='9999-01-01'
and concat(first_name,' ',last_name)='Fai Bale';

-- 'd004'

select e.emp_no
from employees e, dept_emp de
where e.emp_no=de.emp_no
and de.to_date='9999-01-01'
and de.dept_no='d004';

-- sol2) subquery 사용
select e.emp_no
from employees e, dept_emp de
where e.emp_no=de.emp_no
and de.to_date='9999-01-01'
and de.dept_no=(
	select de.dept_no
	from employees e, dept_emp de 
	where e.emp_no=de.emp_no 
	and de.to_date='9999-01-01'
	and concat(first_name,' ',last_name)='Fai Bale'
);

-- 3-1) 단일행 연산자: =, >, <, >=, <=, <>, !=

-- 예제) 현재, 전체 사원의 평균 연봉보다 적은 급여를 받는 사원의 이름과 급여를 출력
select e.first_name, s.salary 
from employees e, salaries s
where e.emp_no=s.emp_no 
and s.to_date='9999-01-01'
and s.salary < (
	select avg(salary)
	from salaries
	where to_date='9999-01-01'
)
order by s.salary desc;


-- 예제2: 현재, 가장 적은 평균 급여의 직책과 급여를 출력
-- (step 1) 직책별 평균 급여



select a.title, avg(b.salary)
from titles a, salaries b
where a.emp_no=b.emp_no
and a.to_date='9999-01-01'
and b.to_date='9999-01-01'
group by a.title
having avg(b.salary)=(
	select min(avg_salary)
	from(select avg(b.salary) as avg_salary
		from titles a, salaries b
		where a.emp_no=b.emp_no
		and a.to_date='9999-01-01'
		and b.to_date='9999-01-01'
	group by a.title) as min_avg_salary
);

-- 3-2) 복수행 연산자: in



