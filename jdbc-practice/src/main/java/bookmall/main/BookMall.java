package bookmall.main;

import java.util.ArrayList;
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
	public static final String URL = "jdbc:mariadb://192.168.0.181:3307/bookmall?charset=utf8";
	public static final String ID = "bookmall";
	public static final String PW = "bookmall";

	public static void main(String[] args) {
//Member
		createMember();
//Category
		createCategory();
//Product: Book
		createBook();
//Cart : add book 
		addBookInCart();
//Order
		String userName = "yoncho";
		String shippingAddress = "서울특별시 강남구 비트컴퓨터 3층 서31강의실";
		String orderNo = createOrder(userName, shippingAddress);
//Order Book		
		orderBook(orderNo);
		
//PrintArea
		System.out.println("## 회원리스트"); //2명
		printMemberList();
		
		System.out.println("## 카테고리"); //카테고리 3개
		printCategoryList();
		
		System.out.println("## 상품"); //책 3
		printBookList();
		
		System.out.println("## 카트"); //2개
		printCartList();
		
		System.out.println("## 주문"); //1건
		printOrderList(orderNo);
		
		System.out.println("## 주문도서"); //2개
		printOrderDetail(orderNo);
	}
	
	
	public static void createMember() {
		List<MemberVo> memberList = new ArrayList();
		//add member#1
		MemberVo memberVo1 = new MemberVo();
		memberVo1.setName("yoncho");
		memberVo1.setPhoneNumber("010-9955-****");
		memberVo1.setEmail("yoncho@naver.com");
		memberVo1.setPassword("yoncho1234");
		memberList.add(memberVo1);
		//add member#2 setting
		MemberVo memberVo2 = new MemberVo();
		memberVo2.setName("hello");
		memberVo2.setPhoneNumber("010-9980-****");
		memberVo2.setEmail("hello@naver.com");
		memberVo2.setPassword("hello1234");
		memberList.add(memberVo2);
		
		insertMember(memberList);
	}
	
	public static boolean insertMember(List<MemberVo> memberList) {
		MemberDao memberDao = new MemberDao();
		boolean result = false;
		for(MemberVo memberVo : memberList) {
			result = memberDao.insert(memberVo);
			System.out.println(memberVo.getName() + (result ? "멤버 등록 성공":"멤버 등록 실패 (이미 존재하는 사용자)"));
		}
		return result;
	}
	
	public static void createCategory() {
		String[] categoryNameList = new String[] {"IT", "소설", "과학"};
		insertCategory(categoryNameList);
	}
	
	public static void insertCategory(String[] categoryNameList) {
		CategoryDao categoryDao = new CategoryDao();
		boolean result = false;
		CategoryVo categoryVo;
		
		for(String category : categoryNameList) {
			categoryVo = new CategoryVo();
			categoryVo.setName(category);
			
			result = new CategoryDao().insert(categoryVo);
			System.out.println(categoryVo.getName() + (result ? "카테고리 등록 성공":"카테고리 등록 실패 (이미 존재하는 카테고리)"));
		}
	}
	
	public static void createBook() {
		List<BookVo> bookList = new ArrayList();
		//add Book#1  
		BookVo bookVo1 = new BookVo();
		bookVo1.setTitle("이것이 자바다");
		bookVo1.setPrice(25000);
		bookVo1.setCategoryNo(1);
		bookList.add(bookVo1);
		//add Book#2 
		BookVo bookVo2 = new BookVo();
		bookVo2.setTitle("탐정소설");
		bookVo2.setPrice(35000);
		bookVo2.setCategoryNo(2);
		bookList.add(bookVo2);
		//add Book#3 setting 
		BookVo bookVo3 = new BookVo();
		bookVo3.setTitle("우주와 우주");
		bookVo3.setPrice(40000);
		bookVo3.setCategoryNo(3);
		bookList.add(bookVo3);
		
		insertBook(bookList);
	}
	
	public static void insertBook(List<BookVo> bookList) {
		BookDao bookDao = new BookDao();
		boolean result = false;
		for(BookVo book : bookList) {
			result = bookDao.insert(book);
			System.out.println(book.getTitle() +(result ? " 도서 등록 성공":"도서 등록 실패 (이미 존재하는 도서)"));
		}
	}
	
	public static void insertCart(List<CartVo> cartList) {
		boolean result = false;
		for(CartVo cart : cartList) {
			result = new CartDao().insert(cart);
			System.out.println(result ? "카드 담기 성공":"카트 담기 실패");
		}
	}
	
	public static void addBookInCart() {
		List<CartVo> cartList = new ArrayList();
		CartVo cv = new CartVo();
		
		//yoncho`s cart add book (이것이 자바다 2권, 탐정소설 3권)
		int memberPK = new MemberDao().findNoByName("yoncho"); //yoncho
		cv.setMemberNo(memberPK);
		cv.setBookNo(new BookDao().findNoByTitle("이것이 자바다"));
		cv.setBookCount(2);
		cartList.add(cv);
		cv = new CartVo();
		cv.setMemberNo(memberPK);
		cv.setBookNo(new BookDao().findNoByTitle("탐정소설"));
		cv.setBookCount(3);
		cartList.add(cv);
		
	 	//hello`s cart add book (이것이 자바다 4권, 탐정소설 5권)
	 	memberPK = new MemberDao().findNoByName("hello");
	 	//new MemberDao().findNoByName("hello") 이 방식도 있음.
	 	cv = new CartVo();
	 	cv.setMemberNo(memberPK);
	 	cv.setBookNo(new BookDao().findNoByTitle("이것이 자바다"));
	 	cv.setBookCount(4);
	 	cartList.add(cv);
	 	cv = new CartVo();
	 	cv.setMemberNo(memberPK);
	 	cv.setBookNo(new BookDao().findNoByTitle("탐정소설"));
	 	cv.setBookCount(5);
	 	cartList.add(cv);
		
		insertCart(cartList);
	}
	
	public static String createOrder(String userName ,String shippingAddress) {
		boolean result = false;
		String orderNo;
		//1) order - yoncho`s order
		int memberNo = new MemberDao().findNoByName("yoncho");
		//주문 번호 (멤버no-주문일자(년도월일시분초밀리초))
		orderNo = new OrderDao().orderNoGenerator(memberNo);
		
		OrderVo ov = new OrderVo();
		ov.setMemberNo(memberNo);
		ov.setShippingAddress(shippingAddress);
		ov.setOrderNo(orderNo);
		ov.setTotalPrice(0);
		result = new OrderDao().insertOrder(ov);
		System.out.println(result ? "주문서 생성 성공":"주문서 생성 실패");
		
		return orderNo;
	}
	
	public static void insertOrderBook(List<OrderBookVo> orderBookList) {
		boolean result = false;
		for(OrderBookVo orderBook : orderBookList) {
			result = new OrderDao().insertOrderBook(orderBook);
			System.out.println(result ? "책 주문 성공" : "책 주문 실패");
		}
	}
	
	public static void orderBook(String orderNo) {
		int orderPrimaryKey = new OrderDao().findNoByOrderNo(orderNo);
		List<OrderBookVo> orderBookList = new ArrayList();
		//2-1) orderBook#1
		OrderBookVo obv = new OrderBookVo();
		BookVo bv = new BookDao().findByBookNo(new BookDao().findNoByTitle("이것이 자바다"));
		obv.setOrderNo(orderPrimaryKey);
		obv.setBookNo(bv.getNo());
		obv.setBookCount(2);
		obv.setBookPrice(bv.getPrice());
		orderBookList.add(obv);
		
		// 2-2) orderBook#2
		obv = new OrderBookVo();
		bv = new BookDao().findByBookNo(new BookDao().findNoByTitle("탐정소설"));
		obv.setOrderNo(orderPrimaryKey);
		obv.setBookNo(bv.getNo());
		obv.setBookCount(3);
		obv.setBookPrice(bv.getPrice());
		orderBookList.add(obv);
		
		insertOrderBook(orderBookList);
	}
	
	public static void printMemberList() {
		List<MemberVo> memberList = new MemberDao().findAll();
		for(MemberVo member : memberList)
		{
			System.out.println("이름 : " + member.getName() +
							   " | 전화번호 : " + member.getPhoneNumber()+
							   " | 이메일 : " + member.getEmail() +
							   " | 비밀번호 : " + member.getPassword());
		}	
		
	}
	
	public static void printCategoryList() {
		List<CategoryVo> categoryList = new CategoryDao().findAll();
		for(CategoryVo category: categoryList) {
			System.out.println("카테고리 번호 : " + category.getNo()+
							   " | 이름 : " + category.getName());
		}
	}
	
	public static void printBookList() {
		List<BookVo> bookList = new BookDao().findAll();

		for(BookVo book: bookList) {
			System.out.println("도서 제목 : " + book.getTitle()+
							   " | 가격 : " + book.getPrice());
		}
	}
	
	public static void printCartList() {
		for (MemberVo mvo : new MemberDao().findAll()) {
			System.out.println("==========[" + mvo.getName()+"`s Cart]==========");
			for(CartVo cart : new CartDao().findAllByMember(mvo.getNo()))
			{
				System.out.println(cart);
			}
		}
	}
	
	public static void printOrderList(String orderNo) {
		OrderVo vo = new OrderDao().findByOrderNo(orderNo);
		MemberVo mvo = new MemberDao().findByNo(vo.getMemberNo());
		System.out.println("주문번호 : " + vo.getOrderNo()+
						   " | 주문자(이름/이메일) : " + mvo.getName() + " / " + mvo.getEmail() +
						   " | 결제금액 : " + vo.getTotalPrice() +
						   " | 배송지 : " +  vo.getShippingAddress());
	}
	
	public static void printOrderDetail(String orderNo) {
		int findNo= new OrderDao().findNoByOrderNo(orderNo);
		for(OrderBookVo ovo : new OrderDao().findAllOrderBookByOrderNo(findNo)){
			System.out.println("도서 번호 : " + ovo.getBookNo() + 
							   " | 도서 제목 : " + ovo.getBookTitle() +
							   " | 수량 : "+  ovo.getBookCount());
		}
	}
}
