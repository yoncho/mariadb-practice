package bookmall.dao.test;

import java.util.List;

import bookmall.dao.MemberDao;
import bookmall.vo.MemberVo;

public class MemberDaoTest {

	public static void main(String[] args) {
		insertTest();
//		findAllTest();
	}

	private static void insertTest() {
		MemberDao memberDao = new MemberDao();
		//member#1 setting
		MemberVo memberVo1 = new MemberVo();
		memberVo1.setName("yoncho");
		memberVo1.setPhoneNumber("010-9980-9914");
		memberVo1.setEmail("yoncho@naver.com");
		memberVo1.setPassword("yoncho1234");
		
		boolean result = false;
		result = memberDao.insert(memberVo1);
		System.out.println(result ? memberVo1.getName() + " insert ok":memberVo1.getName() + "insert fail");
	}
	
	private static void findAllTest() {
		List<MemberVo> memberList = new MemberDao().findAll();
		for(MemberVo member : memberList)
		{
			System.out.println("이름 : " + member.getName() +
							   "전화번호 : " + member.getPhoneNumber()+
							   "이메일 : " + member.getEmail() +
							   "비밀번호 : " + member.getPassword());
		}
	}

}
