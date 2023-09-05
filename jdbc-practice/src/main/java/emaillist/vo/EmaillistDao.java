package emaillist.vo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emaillist.dao.EmaillistVo;

public class EmaillistDao {

	public boolean insert(EmaillistVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			conn = getConnection();
			
			//3. Statement 객체 생성
			String sql = "insert into emaillist values (null,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			//4. 값 binding
			pstmt.setString(1, vo.getFirstName());
			pstmt.setString(2, vo.getLastName());
			pstmt.setString(3, vo.getEmail());
			int count =  pstmt.executeUpdate();
			
			//5. 결과
			
			result = count == 1;
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

	public List<EmaillistVo> findAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<EmaillistVo> result = new ArrayList<>();
		try {
			conn = getConnection();
			
			//3. SQL 준비
			String sql = "select no, first_name, last_name, email from emaillist";
			pstmt = conn.prepareStatement(sql);
			
			rs =  pstmt.executeQuery();

			//5. 결과 처리
			while (rs.next()) {
				long no = rs.getLong(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String email = rs.getString(4);
				
				EmaillistVo vo = new EmaillistVo();
				vo.setNo(no);
				vo.setFirstName(firstName);
				vo.setLastName(lastName);
				vo.setEmail(email);
				
				result.add(vo);
			}
			
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

	public boolean deleteByEmail(String email) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		try {
			conn = getConnection();
			
			//3. SQL 준비
			String sql = "delete from emaillist where email=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, email);
			int updateResult =  pstmt.executeUpdate();

			result = updateResult >= 1;
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
	
	
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://192.168.0.181:3307/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "*****", "*");
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 : " + e);
		}
		return conn;
	}
}
