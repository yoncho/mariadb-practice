package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectTest01 {

	public static void main(String[] args) {
		searchEmployees("ko");

	}
	public static void searchEmployees(String keyword) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//1. JDBC Driver Class 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			//2. 연결하기
			String url = "jdbc:mariadb://192.168.0.181:3307/employees?charset=utf8";
			conn = DriverManager.getConnection(url, "hr", "********");
			
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
			rs =  pstmt.executeQuery();
			
			System.out.println(rs);
			//5. 결과 처리
			while (rs.next()) {
				Long empNo = rs.getLong(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				
				System.out.println(empNo + ":" + firstName + " " + lastName);
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
	}
}