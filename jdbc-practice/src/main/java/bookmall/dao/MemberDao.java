package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.MemberVo;
import bookshop.vo.BookVo;

public class MemberDao {
	private final String URL = "jdbc:mariadb://192.168.0.181:3307/bookmall?charset=utf8";
	private final String ID = "bookmall";
	private final String PW = "bookmall";
	
	public boolean insert(MemberVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {
			//1. JDBC Driver Class 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			//2. 연결하기
			conn = DriverManager.getConnection(URL, ID, PW);
			
			//3. Member 등록
			String authorSql = "insert into member values (?,?,?,?,?)";
			pstmt = conn.prepareStatement(authorSql);
			
			pstmt.setInt(1, vo.getNo());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getPhoneNumber());
			pstmt.setString(4, vo.getEmail());
			pstmt.setString(5, vo.getPassword());
			
			//4. 결과 
			result =  pstmt.executeUpdate() == 1;
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 : " + e);
		} catch(SQLException e) {
			System.out.println("error:" + e);
		} finally{
			try {
				//5. 자원 정리
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
	
	public List<MemberVo> findAll() {
		List<MemberVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//1. JDBC Driver Class 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			//2. 연결하기
			conn = DriverManager.getConnection(URL, ID, PW);
			
			//3. Statement 객체 생성
			String sql = "select name, phone_number, email, password from member";
			pstmt = conn.prepareStatement(sql);
			
			//4. SQL 실행
			rs =  pstmt.executeQuery();

			//5. 결과 처리
			while (rs.next()) {
				String name = rs.getString(1);
				String phoneNumber = rs.getString(2);
				String email = rs.getString(3);
				String password = rs.getString(4);
				
				MemberVo vo = new MemberVo();
				vo.setName(name);
				vo.setPhoneNumber(phoneNumber);
				vo.setEmail(email);
				vo.setPassword(password);
				
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
