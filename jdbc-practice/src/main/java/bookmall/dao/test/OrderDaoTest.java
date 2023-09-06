package bookmall.dao.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import bookmall.dao.BookDao;
import bookmall.dao.MemberDao;
import bookmall.dao.OrderDao;
import bookmall.vo.BookVo;
import bookmall.vo.MemberVo;
import bookmall.vo.OrderBookVo;
import bookmall.vo.OrderVo;

public class OrderDaoTest {
	public static String orderNo;
	public static void main(String[] args) {
		testInsert();
		testFindOrderByOrderNo();
		testFindOrderBookByOrderNo();
	}
	
	public String orderNoGenerator(int memberNo) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String timeData = formatter.format(new Date());
		
		return memberNo+"-"+timeData;
	}
	
	private static void testFindOrderBookByOrderNo() {
		int findNo= new OrderDao().findNoByOrderNo(orderNo);
		for(OrderBookVo ovo : new OrderDao().findAllOrderBookByOrderNo(findNo)){
			
			System.out.println("도서 번호 : " + ovo.getBookNo() + 
							   " | 도서 제목 : " + ovo.getBookTitle() +
							   " | 수량 : "+  ovo.getBookCount());
		}
	}

	private static void testFindOrderByOrderNo() {
		OrderVo vo = new OrderDao().findByOrderNo(orderNo);
		MemberVo mvo = new MemberDao().findByNo(vo.getMemberNo());
		System.out.println("주문번호 : " + vo.getOrderNo()+
						   " | 주문자(이름/이메일) : " + mvo.getName() + " / " + mvo.getEmail() +
						   " | 결제금액 : " + vo.getTotalPrice() +
						   " | 배송지 : " +  vo.getShippingAddress());
	}

	private static void testInsert() {
		//order
		int memberNo = new MemberDao().findNoByName("yoncho");
		orderNo = new OrderDao().orderNoGenerator(memberNo);
		
		OrderVo ov = new OrderVo();
		ov.setMemberNo(memberNo);
		ov.setShippingAddress("서울특별시 강남구 비트컴퓨터 3층 서31강의실");
		ov.setOrderNo(orderNo);
		ov.setTotalPrice(0);
		boolean result = new OrderDao().insertOrder(ov);
		System.out.println(result ? "성공":"실패");
		
		//orderBook
		int no = new OrderDao().findNoByOrderNo(orderNo);
		OrderBookVo obv = new OrderBookVo();
		BookVo bv = new BookDao().findByBookNo(new BookDao().findNoByTitle("이것이 자바다"));
		obv.setOrderNo(no);
		obv.setBookNo(bv.getNo());
		obv.setBookCount(1);
		obv.setBookPrice(bv.getPrice());
		result = new OrderDao().insertOrderBook(obv);
		System.out.println(result ? "성공":"실패");
		
		//orderBook
		obv = new OrderBookVo();
		bv = new BookDao().findByBookNo(new BookDao().findNoByTitle("우주와 우주"));
		obv.setOrderNo(no);
		obv.setBookNo(bv.getNo());
		obv.setBookCount(1);
		obv.setBookPrice(bv.getPrice());
		result = new OrderDao().insertOrderBook(obv);
		System.out.println(result ? "성공":"실패");
		
		
	}
	
	

}
