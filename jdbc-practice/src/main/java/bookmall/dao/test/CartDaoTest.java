package bookmall.dao.test;

import bookmall.dao.BookDao;
import bookmall.dao.CartDao;
import bookmall.dao.MemberDao;
import bookmall.vo.CartVo;
import bookmall.vo.MemberVo;

public class CartDaoTest {

	public static void main(String[] args) {
		testInsert();
		testFindAll();
	}
	
	private static void testInsert() {
		CartDao cartDao = new CartDao();
		CartVo cv = new CartVo(); 
		cv.setMemberNo(new MemberDao().findNoByName("yoncho"));
		cv.setBookNo(new BookDao().findNoByTitle("우주와 우주"));
		cv.setBookCount(2);
		
		cartDao.insert(cv);
		
		cv.setMemberNo(new MemberDao().findNoByName("yoncho"));
		cv.setBookNo(new BookDao().findNoByTitle("탐정소설"));
		cv.setBookCount(3);
		cartDao.insert(cv);
		
		cv.setMemberNo(new MemberDao().findNoByName("hello"));
		cv.setBookNo(new BookDao().findNoByTitle("이것이 자바다"));
		cv.setBookCount(4);
		
		cartDao.insert(cv);
		
		cv.setMemberNo(new MemberDao().findNoByName("hello"));
		cv.setBookNo(new BookDao().findNoByTitle("탐정소설"));
		cv.setBookCount(5);
		
		boolean result = cartDao.insert(cv);
		
		System.out.println(result ? "성공":"실패");
	}
	
	private static void testFindAll() {
		for (MemberVo mvo : new MemberDao().findAll()) {
			System.out.println("==========[" + mvo.getName()+"`s Cart]==========");
			for(CartVo vo : new CartDao().findAllByMember(mvo.getNo()))
			{
				System.out.println(vo);
			}
		}
		
	}
}
