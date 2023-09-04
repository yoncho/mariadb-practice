package bookmall.main;

import java.util.List;

import bookmall.dao.BookDao;
import bookmall.dao.CategoryDao;
import bookmall.dao.MemberDao;
import bookmall.vo.BookVo;
import bookmall.vo.CategoryVo;
import bookmall.vo.MemberVo;

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
		System.out.println(categoryVo1.getName() + (result ? " insert ok":"insert fail"));
		result = categoryDao.insert(categoryVo3);
		System.out.println(categoryVo1.getName() + (result ? " insert ok":"insert fail"));

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
		
//PrintArea
		System.out.println("## 회원리스트"); //2명
		List<MemberVo> memberList = memberDao.findAll();
		for(MemberVo member : memberList)
		{
			System.out.println("이름 : " + member.getName() +
							   "전화번호 : " + member.getPhoneNumber()+
							   "이메일 : " + member.getEmail() +
							   "비밀번호 : " + member.getPassword());
		}	
		System.out.println("## 카테고리"); //카테고리 3개
		System.out.println("## 상품"); //책 3
		System.out.println("## 카트"); //2개
		System.out.println("## 주문"); //1건
		System.out.println("## 주문도서"); 
		
		

		
	}
}
