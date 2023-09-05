package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bookmall.vo.BookVo;
import bookmall.vo.CartVo;
import bookmall.vo.OrderBookVo;
import bookmall.vo.OrderVo;

public class OrderDao {
	private final String URL = "jdbc:mariadb://192.168.0.181:3307/bookmall?charset=utf8";
	private final String ID = "bookmall";
	private final String PW = "bookmall";
	
	public boolean insertOrder(OrderVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {
			conn = getConnection();
			
			String insertSql = "insert into orders values (null,?,?,?,?)";
			pstmt = conn.prepareStatement(insertSql);
			
			pstmt.setString(1, vo.getOrderNo());
			pstmt.setInt(2, vo.getTotalPrice());
			pstmt.setString(3, vo.getShippingAddress());
			pstmt.setInt(4, vo.getMemberNo());
			
			result = pstmt.executeUpdate() > 0;
		} catch(SQLException e) {
			System.out.println("error:" + e);
		} finally{
			try {
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
	
	public List<String> findOrderNoByMemberNo(int memberNo){
		List<String> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "select order_no from orders where member_no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberNo);
			rs =  pstmt.executeQuery();

			while (rs.next()) {
				result.add(rs.getString(1));
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
	
	public boolean insertOrderBook(OrderBookVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {
			conn = getConnection();
			
			String insertSql = "insert into order_book values (null,?,?,?,?)";
			pstmt = conn.prepareStatement(insertSql);
			
			pstmt.setInt(1, vo.getBookCount());
			pstmt.setInt(2, vo.getBookPrice());
			pstmt.setInt(3, vo.getOrderNo());
			pstmt.setInt(4, vo.getBookNo());
			
			result = pstmt.executeUpdate() > 0;
			
			//Order totalPrice Update
			String updateSql = "update orders set total_price=total_price + ? where no=?";
			pstmt = conn.prepareStatement(updateSql);
			pstmt.setInt(1, vo.getBookCount() * vo.getBookPrice());
			pstmt.setInt(2, vo.getOrderNo());
			result = pstmt.executeUpdate() > 0;
		
		} catch(SQLException e) {
			System.out.println("error:" + e);
		} finally{
			try {
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
	
	public String orderNoGenerator(int memberNo) {
		String orderNo = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		String timeData = formatter.format(new Date());
		
		orderNo = memberNo + "-"+timeData;
		
		return orderNo;
	}
}
