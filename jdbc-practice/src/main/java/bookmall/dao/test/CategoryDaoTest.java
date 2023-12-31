package bookmall.dao.test;

import java.util.List;

import bookmall.dao.CategoryDao;
import bookmall.vo.CategoryVo;

public class CategoryDaoTest {
	public static void main(String[] args) {
		insertTest();
		findAllTest();
	}

	private static void findAllTest() {
		List<CategoryVo> list = new CategoryDao().findAll();
		for(CategoryVo cv: list) {
			System.out.println("No : " + cv.getNo()+
							   "이름 : " + cv.getName());
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
		CategoryVo categoryVo4 = new CategoryVo();
		categoryVo4.setName("과학");
		
		
		//Category#1,#2,#3 insert & check result
		result = false;
		result = categoryDao.insert(categoryVo1);
		System.out.println(categoryVo1.getName() + (result ? " insert ok":" insert fail"));
		result = categoryDao.insert(categoryVo2);
		System.out.println(categoryVo2.getName() + (result ? " insert ok":" insert fail"));
		result = categoryDao.insert(categoryVo3);
		System.out.println(categoryVo3.getName() + (result ? " insert ok":" insert fail"));
		//이미 존재하는 카테고리를 또 insert하려는 경우 insert 실패 되어야함..!
		result = categoryDao.insert(categoryVo4);
		System.out.println(categoryVo4.getName() + (result ? " insert ok":" insert fail"));
	}
	
}
