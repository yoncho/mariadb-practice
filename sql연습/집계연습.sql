-- 1) 집계 쿼리 : select절에 통계(집계)함수 (avg, max, min, count, sum, stddev, ...)
select avg(salary), sum(salary) from salaries;

-- from -> where -> select .... -> order by 순으로 진행
-- 2) Select 절에 그룹(통계함수)있는 경우, 어떤 컬럼도 Select 절에 올 수 없다!
-- emp_no는 아무런 의미가 없다.
-- 오류!! mysql이 error로 인식하지 않음... sum하면 row가 1개만 생기는데 여러 row가 생길 수 있는 item을 select 절에 같이 배치할 순 없음.
select emp_no, avg(salary)
from salaries;

-- 3) Query 순서 
--		1. from : 테이블에서 접근
--		2. where : 조건에 맞는 row를 선택
--		3. projection : 집계(임시 테이블, 메모리 캐시)
--		4. 결과를 반환 : 출력

-- 예) 사번이 10060인 사원이 받은 평균 연봉은?
select avg(salary)
from salaries
where emp_no='10060';

-- 4) Group by : group by에 참여 컬럼은 projection이 가능하다: select절에 올 수 있다.
-- 예제4) 사원별 평균 연봉
select emp_no, avg(salary) as avg_salary
from salaries
group by emp_no
order by avg_salary desc;

-- 5) Having 
--  집계 결과(결과 테이블)에서 row를 선택해야하는 경우 (집계 이후에 조건 설정할 경우)
--  이미 where 절은 실행이 되었기 때문에 having 절에서 조건을 주어야 한다...!!
-- 예제) 평균 연봉이 60000달러 이상인 사원의 사번과 평균 연봉을 출력
select emp_no, avg(salary) as avg_salary
from salaries
group by emp_no
having avg_salary >= 60000;

-- 6) order by
-- order by는 항상 맨 마지막 출력 전에 한다.
select emp_no, avg(salary) as avg_salary
from salaries
group by emp_no
having avg_salary >= 60000
order by avg_salary asc;

-- 7) 주의해야할점
-- 예제 : 사번이 10060인 사원의 사번과 평균 급여, 급여 총합을 출력
select emp_no, avg(salary), sum(salary)
from salaries
group by emp_no
having emp_no = 10060;

select gender, count(*) 
from employees
group by gender;

