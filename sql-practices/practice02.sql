-- 문제 1. 
-- 최고임금(salary)과  최저임금을 “최고임금, “최저임금”프로젝션 타이틀로 함께 출력해보세요. 두 임금의 차이는 얼마인가요? 함께 -- “최고임금 – 최저임금”이란 타이틀로 출력해 -- 보세요.
select max(salary) as '최고임금', min(salary) as '최저임금', max(salary) - min(salary) as '임금 차이'
from salaries;
-- 문제2.
-- 마지막으로 신입사원이 들어온 날은 언제 입니까? -- 다음 형식으로 출력해주세요.
-- 예) 2014년 07월 10일
select date_format(max(a.hire_date), '%Y년 %m월 %d일') 
from employees a;
-- 문제3.
-- 가장 오래 근속한 직원의 입사일은 언제인가요? -- 다음 형식으로 출력해주세요.
-- 예) 2014년 07월 10일
select date_format(min(a.hire_date), '%Y년 %m월 %d일') 
from employees a, titles b
where a.emp_no = b.emp_no 
and b.to_date = '9999-01-01';

-- 문제4.
-- 현재 이 회사의 평균 연봉은 얼마입니까?
select avg(a.salary)
from salaries a, titles b
where a.emp_no = b.emp_no and b.to_date='9999-01-01';

-- 문제5.
-- 현재 이 회사의 최고/최저 연봉은 얼마입니까?
select max(a.salary) as '최고 연봉', min(a.salary) as '최저 연봉'
from salaries a, titles b
where a.emp_no = b.emp_no and b.to_date='9999-01-01';
-- 문제6.
-- 최고 어린 사원의 나이와 최 연장자의 나이는?
select period_diff(date_format(min(a.birth_date), '%y'), date_format(curdate(), '%y'))  as '최고 어린 사원', period_diff(date_format(max(a.birth_date), '%y'), date_format(curdate(), '%y')) as '최 연장자'
from employees a, titles b
where a.emp_no = b.emp_no and b.to_date='9999-01-01';

-- [+] 자체 문제 7. 
-- 부서별 최고 연장자와 최고 연봉자를 표시하시오.
select d.dept_name as '부서', max(s.salary) as '최고 연봉', period_diff(date_format(max(e.birth_date), '%y'), date_format(curdate(), '%y')) as '최고 연장자'
from employees e, dept_manager dm, departments d, salaries s
where e.emp_no = dm.emp_no and dm.dept_no = d.dept_no and s.emp_no = e.emp_no
group by d.dept_name;