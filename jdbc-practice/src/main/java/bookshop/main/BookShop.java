package bookshop.main;

import java.util.List;
import java.util.Scanner;

import bookshop.dao.BookDao;
import bookshop.vo.BookVo;

public class BookShop {

	public static void main(String[] args) {
		displayBookInfo(); //책표시
		
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.print("책 대여를 원할시 1 입력, 반납을 원할시 2 입력, 책등록을 원할시 3 입력, 종료를 원할시 -1 입력 >>");
			int choose = scanner.nextInt();
			scanner.nextLine();
			if (choose == -1) {
				break;
			}else if (choose == 1){
				System.out.print("대여 하고 싶은 책의 번호를 입력하세요:");
				int no = scanner.nextInt();
				BookVo vo = new BookVo();
				vo.setNo(no);
				vo.setRent("Y");
				
				boolean result = new BookDao().updateRent(vo);
				System.out.println(result ? "대여성공":"대여실패");
			}else if (choose == 2){
				System.out.print("반납 하고 싶은 책의 번호를 입력하세요:");
				int no = scanner.nextInt();
				BookVo vo = new BookVo();
				vo.setNo(no);
				vo.setRent("N");
				
				boolean result = new BookDao().updateRent(vo);
				System.out.println(result ? "반납성공":"반납실패");
			}else if (choose == 3){
//				System.out.print("등록 하고 싶은 책의 정보를 입력하세요");
				System.out.print("책의 저자>>");
				String author = scanner.nextLine();
				System.out.println("책의 제목>> ");
				String title = scanner.nextLine();
				
				BookVo vo = new BookVo();
				vo.setAuthor(author);
				vo.setTitle(title);
				vo.setRent("N");
				
				boolean result = new BookDao().addBookInfo(vo);
				System.out.println(result ? "등록성공":"등록실패");
			}else {
				System.out.println("잘못 입력하셨습니다.");
				continue;
			}
			
			displayBookInfo();
		}
		
		scanner.close();
	}
	
	private static void displayBookInfo() {
		System.out.println("도서 출력");
		
		List<BookVo> list = new BookDao().findAll();
		
		for(BookVo bv : list) {
			System.out.println(bv.getNo() + "| 제목 : " + bv.getTitle() + "| 대여 여부 : " + bv.getRent() + "| 작가 :"+ bv.getAuthor());
		}
		
	}

}
