package bookmall.main;

import java.util.List;

import bookmall.dao.BookDao;
import bookmall.dao.CartDao;
import bookmall.dao.CategoryDao;
import bookmall.dao.MemberDao;
import bookmall.dao.OrderDao;
import bookmall.vo.BookVo;
import bookmall.vo.CartVo;
import bookmall.vo.CategoryVo;
import bookmall.vo.MemberVo;
import bookmall.vo.OrderBookVo;
import bookmall.vo.OrderVo;

public class BookMall {

	public static void main(String[] args) {
//Member
		MemberDao memberDao = new MemberDao();
		//member#1 setting
		MemberVo memberVo1 = new MemberVo();
		memberVo1.setName("yoncho");
		memberVo1.setPhoneNumber("010-9980-9914");
		memberVo1.setEmail("yoncho@naver.com");
		memberVo1.setPassword("yoncho1234");
		
		//member#2 setting
		MemberVo memberVo2 = new MemberVo();
		memberVo2.setName("hello");
		memberVo2.setPhoneNumber("010-9980-9914");
		memberVo2.setEmail("hello@naver.com");
		memberVo2.setPassword("hello1234");
		
		//member#1,#2 insert & check result
		boolean result = false;
		result = memberDao.insert(memberVo1);
		System.out.println(result ? memberVo1.getName() + " insert ok":memberVo1.getName() + "insert fail");
		
		result = memberDao.insert(memberVo2);
		System.out.println(result ? memberVo2.getName() + " insert ok":memberVo1.getName() + "insert fail");

//Category
		CategoryDao categoryDao = new CategoryDao();
		//Category#1 setting
		CategoryVo categoryVo1 = new CategoryVo();
		categoryVo1.setName("IT");
		//Category#1 setting
		CategoryVo categoryVo2 = new CategoryVo();
		categoryVo2.setName("소설");
		//Category#3 setting
		CategoryVo categoryVo3 = new CategoryVo();
		categoryVo3.setName("과학");
		
		//Category#1,#2,#3 insert & check result
		result = false;
		result = categoryDao.insert(categoryVo1);
		System.out.println(categoryVo1.getName() + (result ? " insert ok":"insert fail"));
		result = categoryDao.insert(categoryVo2);
		System.out.println(categoryVo2.getName() + (result ? " insert ok":"insert fail"));
		result = categoryDao.insert(categoryVo3);
		System.out.println(categoryVo3.getName() + (result ? " insert ok":"insert fail"));

//Product: Book
		BookDao bookDao = new BookDao();
		//Book#1 setting 
		BookVo bookVo1 = new BookVo();
		bookVo1.setTitle("이것이 자바다");
		bookVo1.setPrice(25000);
		bookVo1.setCategoryNo(1);
		
		//Book#2 setting 
		BookVo bookVo2 = new BookVo();
		bookVo2.setTitle("탐정소설");
		bookVo2.setPrice(35000);
		bookVo2.setCategoryNo(2);
		
		//Book#3 setting 
		BookVo bookVo3 = new BookVo();
		bookVo3.setTitle("우주와 우주");
		bookVo3.setPrice(40000);
		bookVo3.setCategoryNo(3);
		
		//member#1,#2 insert & check result
		result = false;
		result = bookDao.insert(bookVo1);
		System.out.println(bookVo1.getTitle() +(result ? " insert ok":"insert fail"));
		result = bookDao.insert(bookVo2);
		System.out.println(bookVo2.getTitle() +(result ? " insert ok":"insert fail"));
		result = bookDao.insert(bookVo3);
		System.out.println(bookVo3.getTitle() +(result ? " insert ok":"insert fail"));
	
//Cart : add book 
		CartDao cartDao = new CartDao();
		CartVo cv = new CartVo();
		int memberPK = new MemberDao().findNoByName("yoncho"); //yoncho
		cv.setMemberNo(memberPK);
		cv.setBookNo(new BookDao().findNoByTitle("이것이 자바다"));
		cv.setBookCount(2);
		
		cartDao.insert(cv);
		
		cv.setMemberNo(memberPK);
		cv.setBookNo(new BookDao().findNoByTitle("탐정소설"));
		cv.setBookCount(3);
		cartDao.insert(cv);
		
		memberPK = new MemberDao().findNoByName("hello");
		//new MemberDao().findNoByName("hello") 이 방식도 있음.
		cv.setMemberNo(memberPK);
		cv.setBookNo(new BookDao().findNoByTitle("이것이 자바다"));
		cv.setBookCount(4);
		
		cartDao.insert(cv);
		
		cv.setMemberNo(memberPK);
		cv.setBookNo(new BookDao().findNoByTitle("탐정소설"));
		cv.setBookCount(5);
		
		result = cartDao.insert(cv);
		System.out.println(result ? "성공":"실패");
		
//Order
		//1) order - yoncho`s order
		int memberNo = new MemberDao().findNoByName("yoncho");
		//주문 번호 (멤버no-주문일자(년도월일시분초밀리초))
		String orderNo = new OrderDao().orderNoGenerator(memberNo);
		
		OrderVo ov = new OrderVo();
		ov.setMemberNo(memberNo);
		ov.setShippingAddress("서울특별시 강남구 비트컴퓨터 3층 서31강의실");
		ov.setOrderNo(orderNo);
		ov.setTotalPrice(0);
		result = new OrderDao().insertOrder(ov);
		System.out.println(result ? "성공":"실패");
		
//Order Book		
		//2-1) orderBook#1
		int no = new OrderDao().findNoByOrderNo(orderNo);
		OrderBookVo obv = new OrderBookVo();
		BookVo bv = new BookDao().findByBookNo(new BookDao().findNoByTitle("이것이 자바다"));
		obv.setOrderNo(no);
		obv.setBookNo(bv.getNo());
		obv.setBookCount(2);
		obv.setBookPrice(bv.getPrice());
		result = new OrderDao().insertOrderBook(obv);
		System.out.println(result ? "성공":"실패");
		
		//2-2) orderBook#2
		obv = new OrderBookVo();
		bv = new BookDao().findByBookNo(new BookDao().findNoByTitle("탐정소설"));
		obv.setOrderNo(no);
		obv.setBookNo(bv.getNo());
		obv.setBookCount(3);
		obv.setBookPrice(bv.getPrice());
		result = new OrderDao().insertOrderBook(obv);
		System.out.println(result ? "성공":"실패");

//PrintArea
		System.out.println("## 회원리스트"); //2명
		List<MemberVo> memberList = memberDao.findAll();
		for(MemberVo member : memberList)
		{
			System.out.println("이름 : " + member.getName() +
							   " | 전화번호 : " + member.getPhoneNumber()+
							   " | 이메일 : " + member.getEmail() +
							   " | 비밀번호 : " + member.getPassword());
		}	
		
		System.out.println("## 카테고리"); //카테고리 3개
		List<CategoryVo> categoryList = new CategoryDao().findAll();
		for(CategoryVo category: categoryList) {
			System.out.println("카테고리 번호 : " + category.getNo()+
							   " | 이름 : " + category.getName());
		}
		
		System.out.println("## 상품"); //책 3
		List<BookVo> bookList = new BookDao().findAll();

		for(BookVo book: bookList) {
			System.out.println("도서 제목 : " + book.getTitle()+
							   " | 가격 : " + book.getPrice());
		}
		
		System.out.println("## 카트"); //2개
		for (MemberVo mvo : new MemberDao().findAll()) {
			System.out.println("==========[" + mvo.getName()+"`s Cart]==========");
			for(CartVo cart : new CartDao().findAllByMember(mvo.getNo()))
			{
				System.out.println(cart);
			}
		}
		
		System.out.println("## 주문"); //1건
		OrderVo vo = new OrderDao().findByOrderNo(orderNo);
		MemberVo mvo = new MemberDao().findByNo(vo.getMemberNo());
		System.out.println("주문번호 : " + vo.getOrderNo()+
						   " | 주문자(이름/이메일) : " + mvo.getName() + " / " + mvo.getEmail() +
						   " | 결제금액 : " + vo.getTotalPrice() +
						   " | 배송지 : " +  vo.getShippingAddress());
		
		System.out.println("## 주문도서"); //2개
		int findNo= new OrderDao().findNoByOrderNo(orderNo);
		for(OrderBookVo ovo : new OrderDao().findAllOrderBookByOrderNo(findNo)){
			System.out.println("도서 번호 : " + ovo.getBookNo() + 
							   " | 도서 제목 : " + ovo.getBookTitle() +
							   " | 수량 : "+  ovo.getBookCount());
		}
	}
}
