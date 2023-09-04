package bookmall.dao.test;

import java.util.List;

import bookmall.dao.BookDao;
import bookmall.dao.CategoryDao;
import bookmall.vo.BookVo;
import bookmall.vo.CategoryVo;

public class BookDaoTest {
	public static void main(String[] args) {
		insertTest();
		findAllTest();
	}

	private static void findAllTest() {
		List<BookVo> list = new BookDao().findAll();
		CategoryDao cd = new CategoryDao();
		
		for(BookVo bv: list) {
			System.out.println("책제목 : " + bv.getTitle()+
							   " | 가격 : " + bv.getPrice()+
							   " | 카테고리 : " + cd.findByNo(bv.getCategoryNo()));
		}
	}

	private static boolean insertTest() {
		boolean result = false;
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
		result = bookDao.insert(bookVo1);
		System.out.println(bookVo1.getTitle() +(result ? " insert ok":"insert fail"));
		result = bookDao.insert(bookVo2);
		System.out.println(bookVo2.getTitle() +(result ? " insert ok":"insert fail"));
		result = bookDao.insert(bookVo3);
		System.out.println(bookVo3.getTitle() +(result ? " insert ok":"insert fail"));
		return result;
	}
	
}
