package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertTest02 {

	public static void main(String[] args) {
		
		boolean result = insertDepartment("기획2팀");
		
		System.out.println(result ? "성공":"실패");

		
	}

	private static boolean insertDepartment(String name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {
			//1. JDBC Driver Class 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			//2. 연결하기
			String url = "jdbc:mariadb://192.168.0.181:3307/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "yoncho", "*********");
			
			System.out.println("연결 성공!");
			
			//3. SQL 준비
//			String sql = "insert into dept values(null,'"+name+"')"; //replace 이렇게 작성하면 사용자가 조작 가능
			String sql = "insert into dept values(null,?)";
			pstmt = conn.prepareStatement(sql); //sql prepared...!!
			
			//4. 값 binding
			pstmt.setString(1, name);
			
			//5. SQL 실행
			int count =  pstmt.executeUpdate(); //select - executeQuery, others - executeUpdate

			
			//5. 결과 처리
			result = count == 1;
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 : " + e);
		} catch(SQLException e) {
			System.out.println("error:" + e);
		} finally{
			try {
				//6. 자원 정리
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
