-- subquery 위주의 혼합 문제입니다.

-- 문제1.
-- 현재 평균 연봉보다 많은 월급을 받는 직원은 몇 명이나 있습니까
select count(*) as '평균 이상 연봉자'
from salaries 
where to_date='9999-01-01'
and salary >= (select avg(salary)
from salaries
where to_date='9999-01-01');


-- 문제2. (생략)
-- 현재, 각 부서별로 최고의 급여를 받는 사원의 사번, 이름, 부서, 연봉을 조회하세요. 단 조회결과는 연봉의 내림차순으로 정렬되어 나타나야 합니다. 

select e.emp_no as '사번', concat(e.first_name,' ',e.last_name) as '이름',d.dept_name as '부서명' ,s.salary as '최고연봉'
from employees e, departments d, dept_emp de, salaries s,
		(select de.dept_no as s_dept_no, max(s.salary) as s_max_salary
		from dept_emp de, salaries s
		where de.emp_no=s.emp_no
		and s.to_date='9999-01-01' and de.to_date='9999-01-01'
		group by de.dept_no) 
		as max_dept
where e.emp_no=de.emp_no 
and d.dept_no=de.dept_no 
and  s.emp_no=e.emp_no 
and max_dept.s_dept_no=de.dept_no 
and s.to_date='9999-01-01' 
and de.to_date='9999-01-01'
and s.salary=max_dept.s_max_salary;
 
-- 문제3. where x... 크다 조건... from절에 join 하는 방식...!!
-- 현재, 자신의 부서 평균 급여보다 연봉(salary)이 많은 사원의 사번, 이름과 연봉을 조회하세요 

select 
	e.emp_no, 
    concat(e.first_name,' ',e.last_name) as '이름', 
    s.salary, 
    d.avg_salary
from 
	employees e, 
    dept_emp de, 
    salaries s, 
    (select 
		de.dept_no as dept_num, 
        avg(s.salary) as avg_salary 
		from dept_emp de, salaries s
        where de.emp_no=s.emp_no
		and de.to_date='9999-01-01'
		and s.to_date='9999-01-01'
		group by de.dept_no) as d
where e.emp_no=de.emp_no 
and e.emp_no=s.emp_no 
and d.dept_num=de.dept_no
and d.avg_salary <= s.salary
and s.to_date='9999-01-01' 
and de.to_date='9999-01-01';

-- 문제4. (중요!) join 문제
-- 현재, 사원들의 사번, 이름, 매니저 이름, 부서 이름으로 출력해 보세요.

select e.emp_no as '사번', concat(e.first_name,' ',e.last_name) as '이름', manager.manager_dept_name
from employees e, (select dm.emp_no as manager_emp_no, d.dept_name as manager_dept_name
				   from departments d join dept_manager dm on dm.dept_no=d.dept_no
				   where dm.to_date='9999-01-01') as manager
where manager.manager_emp_no=e.emp_no;

-- 문제5.
-- 현재, 평균연봉이 가장 높은 부서의 사원들의 사번, 이름, 직책, 연봉을 조회하고 연봉 순으로 출력하세요.

select e.emp_no as '사번', concat(e.first_name,' ',e.last_name) as '이름', t.title as '직책', s.salary as '연봉'
from employees e, dept_emp de, salaries s, titles t
where e.emp_no=de.emp_no and e.emp_no=s.emp_no and t.emp_no=e.emp_no
and t.to_date='9999-01-01' and s.to_date='9999-01-01' and de.to_date='9999-01-01'
and de.dept_no=(select de.dept_no
				from dept_emp de, salaries s
				where de.emp_no=s.emp_no
				and de.to_date='9999-01-01'
				and s.to_date='9999-01-01'
				group by de.dept_no
				having avg(s.salary)=(select max(d_max_salary)
									  from (select de.dept_no as d_dept_no, avg(s.salary) as d_max_salary
									  from dept_emp de, salaries s
									  where de.emp_no=s.emp_no
									  and de.to_date='9999-01-01'
									  and s.to_date='9999-01-01'
									  group by de.dept_no)
				as avg_salary))
order by s.salary;
-- 문제6.
-- 평균 연봉이 가장 높은 부서는? ex) 총무 20000 출력

select d.dept_name, avg(s.salary) as avg_salary
from dept_emp de, salaries s, departments d
where de.emp_no=s.emp_no
and d.dept_no = de.dept_no
and de.to_date='9999-01-01'
and s.to_date='9999-01-01'
group by de.dept_no
having avg_salary=(select max(d_max_salary)
				   from (select de.dept_no as d_dept_no, avg(s.salary) as d_max_salary
						 from dept_emp de, salaries s
						 where de.emp_no=s.emp_no
						 and de.to_date='9999-01-01'
						 and s.to_date='9999-01-01'
						 group by de.dept_no) 
				   as avg_salary);
-- 문제7.
-- 평균 연봉이 가장 높은 직책? ex) enginner 40000

select t.title, avg(s.salary) as avg_salary
from titles t, salaries s
where t.emp_no=s.emp_no
group by t.title 
having avg_salary = (select max(avg_salary)
					from (select t.title, avg(s.salary) as avg_salary	
					from titles t, salaries s
					where t.emp_no=s.emp_no
					group by t.title) as d);

-- 문제8. (순수 join)
-- 현재 자신의 매니저보다 높은 연봉을 받고 있는 직원은?
-- 부서이름, 사원이름, 연봉, 매니저 이름, 메니저 연봉 순으로 출력합니다.

select 
	m.m_dept_no as '부서 코드',
    m.m_dept_name as '부서이름',
    e.emp_no as '사번',
    concat(e.first_name,' ',e.last_name) as '이름', 
    s.salary as '연봉', 
    m.m_name as '매니저 이름' , 
    m.m_salary as '매니저 연봉'
from employees e 
join dept_emp de on e.emp_no=de.emp_no
join departments d on de.dept_no=d.dept_no
join salaries s on e.emp_no=s.emp_no
join (select dm.dept_no as m_dept_no, concat(e.first_name,' ',e.last_name) as m_name, s.salary as m_salary, d.dept_name as m_dept_name
	  from dept_manager dm, salaries s, employees e, departments d
	  where dm.emp_no=s.emp_no
      and e.emp_no=s.emp_no 
      and d.dept_no = dm.dept_no
	  and dm.to_date='9999-01-01' 
      and s.to_date='9999-01-01' 
      group by dm.dept_no) 
as m
on de.dept_no=m.m_dept_no
where de.to_date='9999-01-01' and s.to_date='9999-01-01'
and s.salary > m.m_salary
order by m.m_dept_no;
