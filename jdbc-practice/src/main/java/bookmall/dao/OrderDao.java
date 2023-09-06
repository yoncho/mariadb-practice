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
import bookmall.vo.MemberVo;
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
	
	public OrderVo findByOrderNo(String orderNo) {
		OrderVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "select no, total_price, shipping_address, member_no"
					+ " from orders"
					+ " where order_no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orderNo);
			
			rs =  pstmt.executeQuery();
			rs.next();
			
			int no = rs.getInt(1);
			int totalPrice = rs.getInt(2);
			String shippingAddress = rs.getString(3);
			int memberNo = rs.getInt(4);
			
			result = new OrderVo();
			result.setNo(no);
			result.setMemberNo(memberNo);
			result.setOrderNo(orderNo);
			result.setTotalPrice(totalPrice);
			result.setShippingAddress(shippingAddress);
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
	
	public int findNoByOrderNo(String orderNo){
		int result = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "select no from orders where order_no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orderNo);
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
	
	//Member 고유식별번호로 Member의 모든 OrderNo출력
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
	
	//OrderNo로 모든 OrderBook을 List로 반환
	public List<OrderBookVo> findAllOrderBookByOrderNo(int orderNo)
	{
		List<OrderBookVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "select o.no, o.count, o.price, o.book_no, b.title from order_book o, book b where o.book_no=b.no and orders_no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, orderNo);
			rs =  pstmt.executeQuery();
			
			while (rs.next()) {
				int no = rs.getInt(1);
				int count = rs.getInt(2);
				int price = rs.getInt(3);
				int bookNo = rs.getInt(4);
				String bookTitle = rs.getString(5);
				
				OrderBookVo obvo = new OrderBookVo();
				obvo.setNo(no);
				obvo.setOrderNo(orderNo);
				obvo.setBookNo(bookNo);
				obvo.setBookCount(count);
				obvo.setBookPrice(price);
				obvo.setBookTitle(bookTitle);
				
				result.add(obvo);
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
	
	//OrderNo 생성 함수 (멤버고유식별번호-연도월일시분초밀리초)
	public String orderNoGenerator(int memberNo) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String timeData = formatter.format(new Date());
		
		return memberNo+"-"+timeData;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(URL, ID, PW);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
