package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.BookVo;
import bookmall.vo.CategoryVo;

public class BookDao {
	private final String URL = "jdbc:mariadb://192.168.0.181:3307/bookmall?charset=utf8";
	private final String ID = "bookmall";
	private final String PW = "bookmall";
	
	public boolean insert(BookVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {
			//1. JDBC Driver Class 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			//2. 연결하기
			conn = DriverManager.getConnection(URL, ID, PW);
			
			//3. Member 등록
			String authorSql = "insert into book values (?,?,?,?)";
			pstmt = conn.prepareStatement(authorSql);
			
			pstmt.setInt(1, vo.getNo());
			pstmt.setString(2, vo.getTitle());
			pstmt.setInt(3, vo.getPrice());
			pstmt.setInt(4, vo.getCategoryNo());
			
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
	
	public List<BookVo> findAll() {
		List<BookVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//1. JDBC Driver Class 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			//2. 연결하기
			conn = DriverManager.getConnection(URL, ID, PW);
			
			//3. Statement 객체 생성
			String sql = "select title, price, category_no from book";
			pstmt = conn.prepareStatement(sql);
			
			//4. SQL 실행
			rs =  pstmt.executeQuery();

			//5. 결과 처리
			while (rs.next()) {
				String title = rs.getString(1);
				int price = rs.getInt(2);
				int categoryNo = rs.getInt(3);
				BookVo vo = new BookVo();
				vo.setTitle(title);
				vo.setPrice(price);
				vo.setCategoryNo(categoryNo);

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