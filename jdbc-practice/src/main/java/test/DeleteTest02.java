package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteTest02 {

	public static void main(String[] args) {
		boolean result = deleteDepartmentByNo(7L);
		System.out.println(result ? "성공":"실패");
	}

	private static boolean deleteDepartmentByNo(long no) {
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
			
			//3. Statement 객체 생성
			String sql = "delete from dept where no=?";
			pstmt = conn.prepareStatement(sql);
			
			//4. 값 binding
			pstmt.setLong(1, no);
			
			//5. SQL 실행
			int count = pstmt.executeUpdate(); //select - executeQuery, others - executeUpdate

			//6. 결과 처리
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
