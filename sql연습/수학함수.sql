--
-- 수학 함수
--

-- abs 절대값
select abs(-5) from dual;

-- floor 
select floor(3.6), floor(3.9999), floor(-5.444) from dual;

-- ceil (가까운 정수로 이동)
select ceil(3.6), floor(3.1), floor(-5.444) from dual;

-- mod 
select mod(10,3) from dual;

-- round(x) : x에 가장 가까운 정수 출력
-- round(x, d) : x 값중에 소수점 d자리에 가장 가까운 실수....
select round(10.6), round(-3.2) from dual;
select round(1.4981, 2) from dual;

-- power(x,y) : x의 y승  x^y
select power(2,10), pow(2,10) from dual;

-- sign(X): 양수면 1, 음수면 -1
select sign(20) , sign(0), sign(-15) from dual;

-- greatest(x, y, .... ,z) : 최대값! 
-- least(x,y, .... , z) : 최소값
select greatest(10, 450, 2, 565) from dual;
select least(10, 450, 2, 565) from dual;
select greatest('a', 'b', 'z') from dual;
select least('hello', 'hela' , 'hell') from dual;