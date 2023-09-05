package emaillist.main;

import java.util.Scanner;

import emaillist.dao.EmaillistVo;
import emaillist.vo.EmaillistDao;

public class EmaillistApp {
	private static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		while(true) {
			System.out.print("(l)ist (i)nsert (d)elete (q)uit >>");
			String line = scanner.nextLine();
			
			if("q".equals(line)) {
				break;
			}else if("i".equals(line)) {
				doInsert();
			}else if("d".equals(line)) {
				doDelete();
			}else if("l".equals(line)) {
				doList();
			}
		}
	}

	private static void doList() {
		for(EmaillistVo ev:new EmaillistDao().findAll()) {
			System.out.println("이름:" +ev.getFirstName() + " " + ev.getLastName()+ " | 이메일 : " + ev.getEmail());
		}
	}

	private static void doDelete() {
		System.out.print("Email >>");
		String email = scanner.nextLine();
		boolean result = new EmaillistDao().deleteByEmail(email);
		System.out.println(result ? "성공":"실패");
		doList();
	}

	private static void doInsert() {
		EmaillistVo vo = new EmaillistVo();
		System.out.print("First Name >>");
		vo.setFirstName(scanner.nextLine());
		System.out.print("Last Name >>");
		vo.setLastName(scanner.nextLine());
		System.out.print("Email >>");
		vo.setEmail(scanner.nextLine());

		boolean result = new EmaillistDao().insert(vo);
		System.out.println(result ? "성공":"실패");
		doList();
	}

}
