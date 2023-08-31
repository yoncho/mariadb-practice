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

-- sol1) subquery
select a.title, avg(b.salary) as avg_salary
from titles a, salaries b
where a.emp_no=b.emp_no
and a.to_date='9999-01-01'
and b.to_date='9999-01-01'
group by a.title
having avg_salary=(
	select min(avg_salary)
	from(select avg(b.salary) as avg_salary
		from titles a, salaries b
		where a.emp_no=b.emp_no
		and a.to_date='9999-01-01'
		and b.to_date='9999-01-01'
		group by a.title) as min_avg_salary
);
-- sol2) top-k limit
select a.title, avg(b.salary) as avg_salary
from titles a, salaries b
where a.emp_no=b.emp_no
and a.to_date='9999-01-01'
and b.to_date='9999-01-01'
group by a.title
order by avg_salary
limit 0, 1;

-- 3-2) 복수행 연산자: in, not in, 비교연산자 any, 비교연산자 all

-- any 사용법
-- 1. =any         : in
-- 2. >any, >=ani  : 최소값 
-- 3. <any, <=ani  : 최대값
-- 4. !=any, <>any : 부정

-- all 사용법
-- 1. =all: (x)
-- 2. >all, >=all : 최대값
-- 3. <all, <=all : 최소값
-- 4. !=all, <>all : 부정

-- 예제3: 현재, 급여가 50000 이상인 직원의 이름과 급여를 출력하세요. (급여 내림차순)
select e.emp_no as num, s.salary
from employees e, salaries s
where e.emp_no=s.emp_no
and s.to_date='9999-01-01'
and s.salary >= 50000
group by e.emp_no;

-- sol1) join 
select a.first_name, b.salary
from employees a, salaries b
where a.emp_no=b.emp_no
and b.to_date='9999-01-01'
and b.salary >= 50000
order by b.salary;

-- sol2) subquery
select a.emp_no, b.salary
from employees a, salaries b
where a.emp_no=b.emp_no
and b.to_date='9999-01-01'
and (a.emp_no, b.salary)=any(select a.emp_no, b.salary
							 from employees a, salaries b
							 where a.emp_no=b.emp_no
							 and b.to_date='9999-01-01'
							 and b.salary >= 50000) 
order by b.salary;

-- 문제4) 현재, 각 부서별로 최고 급여를 받고있는 직원의 이름과 연봉을 출력하세요.alter
select d.dept_name, e.first_name, max(salary) 
from departments d, dept_emp de, employees e, salaries s
where d.dept_no=de.dept_no and de.emp_no=e.emp_no and e.emp_no=s.emp_no
and de.to_date='9999-01-01' and s.to_date='9999-01-01'
group by d.dept_name;

select a.dept_no,  max(b.salary)
from dept_emp a, salaries b
where a.emp_no=b.emp_no
and a.to_date='9999-01-01' 
and b.to_date='9999-01-01'
group by a.dept_no;

-- sol1) where절 subquery in, 복수행/복수컬럼
select c.dept_name, a.first_name, b.dept_no,  d.salary
from employees a, dept_emp b, departments c, salaries d
where a.emp_no=b.emp_no and b.dept_no=c.dept_no and a.emp_no=d.emp_no
and b.to_date='9999-01-01' and d.to_date='9999-01-01'
and (b.dept_no, d.salary) in (select a.dept_no,  max(b.salary)
from dept_emp a, salaries b
where a.emp_no=b.emp_no
and a.to_date='9999-01-01' 
and b.to_date='9999-01-01'
group by a.dept_no);

-- sol2) from절 subquery, join
select  c.dept_name, a.first_name, e.deptNum,  e.max_salary
from employees a join dept_emp b on a.emp_no=b.emp_no
join departments c on b.dept_no=c.dept_no 
join salaries d on a.emp_no=d.emp_no
join (select a.dept_no as deptNum,  max(b.salary) as max_salary
from dept_emp a, salaries b
where a.emp_no=b.emp_no
and a.to_date='9999-01-01' 
and b.to_date='9999-01-01'
group by a.dept_no) e on e.deptNum=b.dept_no;

select c.dept_name, a.first_name, b.dept_no, e.max_dept_salary
from employees a, 
	 dept_emp b, 
	 departments c,
     salaries d,
    (select a.dept_no,  max(b.salary) as max_dept_salary
		from dept_emp a, salaries b
		where a.emp_no=b.emp_no
		and a.to_date='9999-01-01' 
		and b.to_date='9999-01-01'
		group by a.dept_no) e
where a.emp_no=b.emp_no 
and b.dept_no=c.dept_no
and a.emp_no=d.emp_no
and c.dept_no=e.dept_no
and b.to_date='9999-01-01' 
and d.to_date='9999-01-01'
and d.salary = e.max_dept_salary;
