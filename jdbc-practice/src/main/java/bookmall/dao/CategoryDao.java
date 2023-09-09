package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.main.BookMall;
import bookmall.vo.CategoryVo;
import bookmall.vo.MemberVo;

public class CategoryDao {
	
	public boolean insert(CategoryVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		ResultSet rs = null;
		try {
			conn =  getConnection();
			
			//Check : 카테고리가 이미 있는지 확인
			String checkSql = "select count(*) from category where name=?";
			pstmt = conn.prepareStatement(checkSql);
			pstmt.setString(1, vo.getName());
			rs = pstmt.executeQuery();
			rs.next();
			
			//만약 카테고리가 존재하지 않는다면 insert
			if(rs.getInt(1) == 0) {
				String authorSql = "insert into category values (?,?)";
				pstmt = conn.prepareStatement(authorSql);	
				pstmt.setInt(1, vo.getNo());
				pstmt.setString(2, vo.getName());

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
	
	public String findByNo(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			conn =  getConnection();
			
			String sql = "select name from category where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs =  pstmt.executeQuery();
			rs.next();
			result = rs.getString(1);
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
	
	public List<CategoryVo> findAll() {
		List<CategoryVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "select no, name from category";
			pstmt = conn.prepareStatement(sql);
			rs =  pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt(1);
				String name = rs.getString(2);
				
				CategoryVo vo = new CategoryVo();
				vo.setNo(no);
				vo.setName(name);

				result.add(vo);
			}
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
