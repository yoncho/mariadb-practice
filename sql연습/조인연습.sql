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
