package emaillist.dao.test;

import java.util.List;

import emaillist.dao.EmaillistVo;
import emaillist.vo.EmaillistDao;

public class EmaillistDaoTest {

	public static void main(String[] args) {
		
		EmaillistVo vo = new EmaillistVo();
		vo.setFirstName("둘");
		vo.setLastName("리");
		vo.setEmail("dooly@gmail.com");
		
		testInsert(vo);
		testFindAll();
		testDeleteByEmail("dooly@gmail.com");
		testFindAll();
	}

	private static void testDeleteByEmail(String email) {
		boolean result = new EmaillistDao().deleteByEmail(email);
		System.out.println(result ? "성공":"실패");
	}


	private static void testInsert(EmaillistVo vo) {
		boolean result = new EmaillistDao().insert(vo);
		System.out.println(result ? "성공":"실패");
	}
	

	private static void testFindAll() {
		for(EmaillistVo ev:new EmaillistDao().findAll()) {
			System.out.println(ev.getFirstName() + " " + ev.getLastName()+ " : " + ev.getEmail());
		}
	}
}
