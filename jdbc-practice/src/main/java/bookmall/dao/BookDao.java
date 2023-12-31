package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.main.BookMall;
import bookmall.vo.BookVo;

public class BookDao {
	
	public boolean insert(BookVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		ResultSet rs = null;
		try {
			conn = getConnection();

			//Check : 동일한 카테고리, 동일한 이름의 도서가 이미 있는지 확인
			String checkSql = "select count(*) from book where title=? and category_no=?";
			pstmt = conn.prepareStatement(checkSql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setInt(2, vo.getCategoryNo());
			rs = pstmt.executeQuery();
			rs.next();
			
			if(rs.getInt(1) == 0) {
				String authorSql = "insert into book values (?,?,?,?)";
				pstmt = conn.prepareStatement(authorSql);
				
				pstmt.setInt(1, vo.getNo());
				pstmt.setString(2, vo.getTitle());
				pstmt.setInt(3, vo.getPrice());
				pstmt.setInt(4, vo.getCategoryNo());
				
				result =  pstmt.executeUpdate() == 1;
			}
		} catch(SQLException e) {
			System.out.println("error:" + e);
		} finally{
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(rs != null) {
					rs.close();
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
			conn = getConnection();
			
			String sql = "select title, price, category_no from book";
			pstmt = conn.prepareStatement(sql);
			rs =  pstmt.executeQuery();

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
	
	public int findNoByTitle(String title) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		
		try {
			conn = getConnection();
			
			String sql = "select no from book where title=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,title);
			
			rs =  pstmt.executeQuery();
			rs.next();
			result = rs.getInt(1);

		} catch(SQLException e) {
			System.out.println("error:" + e);
		} finally{
			try {
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

	public BookVo findByBookNo(int bookNo) {
		BookVo vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			conn = getConnection();
			
			String sql = "select title, price from book where no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,bookNo);
			
			rs =  pstmt.executeQuery();
			rs.next();
			vo = new BookVo();
			vo.setNo(bookNo);
			vo.setTitle(rs.getString(1));
			vo.setPrice(rs.getInt(2));
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
		return vo;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(BookMall.URL, BookMall.ID, BookMall.PW);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
