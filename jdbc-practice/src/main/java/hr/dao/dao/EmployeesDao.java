package hr.dao.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hr.dao.vo.EmployeesVo;

public class EmployeesDao {
	
	public List<EmployeesVo> findByName(String keyword) {
		//JDBC
		List<EmployeesVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//1. JDBC Driver Class 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			//2. 연결하기
			String url = "jdbc:mariadb://192.168.0.181:3307/employees?charset=utf8";
			conn = DriverManager.getConnection(url, "hr", "******");
			
			System.out.println("연결 성공!");
			
			//3. Statement 객체 생성
			String sql = "select emp_no, first_name, last_name"
					+ " from employees"
					+ " where first_name like ?"
					+ " and last_name like ?";
			pstmt = conn.prepareStatement(sql);
			
			//4. 값 binding
			pstmt.setString(1, "%"+keyword+"%" );
			pstmt.setString(2, "%"+keyword+"%" );
			
			//5. SQL 실행
			rs =  pstmt.executeQuery();
			
			System.out.println(rs);
			//6. 결과 처리
			while (rs.next()) {
				Long empNo = rs.getLong(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				
				EmployeesVo vo = new EmployeesVo();
				vo.setEmpNo(empNo);
				vo.setFirstName(firstName);
				vo.setLastName(lastName);
				
				result.add(vo);
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 : " + e);
		} catch(SQLException e) {
			System.out.println("error:" + e);
		} finally{
			try {
				//6. 자원 정리
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if (conn != null && !conn.isClosed())
				{
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
		
		return result;
	}

}
