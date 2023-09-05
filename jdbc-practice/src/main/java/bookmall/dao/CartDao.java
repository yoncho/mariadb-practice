package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.BookVo;
import bookmall.vo.CartVo;

public class CartDao {
	private final String URL = "jdbc:mariadb://192.168.0.181:3307/bookmall?charset=utf8";
	private final String ID = "bookmall";
	private final String PW = "bookmall";
	
	public boolean insert(CartVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			//check 
			String checkSql = "select count(*) from cart where member_no=? and book_no=?";
			pstmt = conn.prepareStatement(checkSql);
			pstmt.setInt(1, vo.getMemberNo());
			pstmt.setInt(2, vo.getBookNo());
			rs =  pstmt.executeQuery();
			rs.next();
			
			//Check : 기존 Cart에 동일한 책이 존재할 시 해당 책의 Count를 증가/ 없을시 새로운 필드 생성
			int fieldCount = rs.getInt(1);
			if (fieldCount > 0) {
				String updateSql = "update cart set count=count+? where member_no=? and book_no=?";
				pstmt = conn.prepareStatement(updateSql);
				pstmt.setInt(1, vo.getBookCount());
				pstmt.setInt(2, vo.getMemberNo());
				pstmt.setInt(3, vo.getBookNo());
			}else{
				//create field in cart
				String insertSql = "insert into cart values (null,?,?,?)";
				pstmt = conn.prepareStatement(insertSql);
				pstmt.setInt(1, vo.getMemberNo());
				pstmt.setInt(2, vo.getBookNo());
				pstmt.setInt(3, vo.getBookCount());
			}
			result = pstmt.executeUpdate() > 0;
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
	
	public List<CartVo> findAllByMember(int no) {
		List<CartVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "select c.book_no, c.count, b.title, b.price, b.category_no"
					+ " from member m, cart c, book b"
					+ " where m.no=? and m.no=c.member_no"
					+ " and c.book_no=b.no";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs =  pstmt.executeQuery();

			while (rs.next()) {
				int memberNo = no;
				int bookNo = rs.getInt(1);
				int bookCount = rs.getInt(2);
				String bookTitle = rs.getString(3);
				int bookPrice = rs.getInt(4);
				int bookCategory = rs.getInt(5);
				
				CartVo vo = new CartVo();
				vo.setMemberNo(memberNo);
				vo.setBookNo(bookNo);
				vo.setBookCount(bookCount);
				vo.bookVo = new BookVo();
				vo.bookVo.setCategoryNo(bookCategory);
				vo.bookVo.setTitle(bookTitle);
				vo.bookVo.setPrice(bookPrice);
				
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
			conn = DriverManager.getConnection(URL, ID, PW);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
