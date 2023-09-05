package bookmall.dao.test;

import bookmall.dao.BookDao;
import bookmall.dao.MemberDao;
import bookmall.dao.OrderDao;
import bookmall.vo.BookVo;
import bookmall.vo.OrderBookVo;
import bookmall.vo.OrderVo;

public class OrderDaoTest {

	public static void main(String[] args) {
		testInsert();
		testFindAll();
	}
	
	private static void testOrderBook() {
		
		OrderBookVo obv = new OrderBookVo();
//		obv.setOrderNo(0);
		
	}

	private static void testInsert() {
		//order
		OrderVo ov = new OrderVo();
		int memberNo = new MemberDao().findNoByName("yoncho");
		ov.setNo(memberNo);
		ov.setMemberNo(memberNo);
		ov.setShippingAddress("서울특별시 강남구 비트컴퓨터 3층 서31강의실");
		ov.setOrderNo(new OrderDao().orderNoGenerator(memberNo));
		ov.setTotalPrice(0);
		boolean result = new OrderDao().insertOrder(ov);
		System.out.println(result ? "성공":"실패");
		//orderBook
		OrderBookVo obv = new OrderBookVo();
		BookVo bv = new BookDao().findByBookNo(new BookDao().findNoByTitle("이것이 자바다"));
		obv.setOrderNo(ov.getNo());
		obv.setBookNo(bv.getNo());
		obv.setBookCount(1);
		obv.setBookPrice(bv.getPrice());
		result = new OrderDao().insertOrderBook(obv);
		System.out.println(result ? "성공":"실패");
		//orderBook
		obv = new OrderBookVo();
		bv = new BookDao().findByBookNo(new BookDao().findNoByTitle("우주와 우주"));
		obv.setOrderNo(ov.getNo());
		obv.setBookNo(bv.getNo());
		obv.setBookCount(1);
		obv.setBookPrice(bv.getPrice());
		result = new OrderDao().insertOrderBook(obv);
		System.out.println(result ? "성공":"실패");
		
		
	}
	
	private static void testFindAll() {	
		int memberNo = new MemberDao().findNoByName("yoncho");
		for(String orderNo : new OrderDao().findOrderNoByMemberNo(memberNo)) {
			System.out.println(orderNo);
		}
	}
	

}
