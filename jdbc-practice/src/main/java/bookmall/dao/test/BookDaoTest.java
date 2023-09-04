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
		for(BookVo bv: list) {
			System.out.println("책제목 : " + bv.getTitle()+
							   "가격 : " + bv.getPrice()+
							   "카테고리 : " + bv.getCategoryNo());
		}
	}

	private static void insertTest() {
		boolean result = false;
		
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
		System.out.println(categoryVo1.getName() + (result ? " insert ok":"insert fail"));
		result = categoryDao.insert(categoryVo3);
		System.out.println(categoryVo1.getName() + (result ? " insert ok":"insert fail"));
	}
	
}
