package bookshop.example;

public class Book {
	private int bookNo;
	public int getBookNo() {
		return bookNo;
	}

	public void setBookNo(int bookNo) {
		this.bookNo = bookNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	private String title;
	private String author;
	private int stateCode;
	
	public Book(int bookNo, String title, String author)
	{
		this.bookNo = bookNo;
		this.title = title;
		this.author = author;
		this.stateCode = 1;
	}
	
	public void rent() {
		//대여 기능 수행
		if(this.stateCode == 1)
		{
			this.stateCode = 0;
			System.out.println(title + "이(가) 대여 됐습니다.");
		}
		else
		{
			System.out.println(title + "는 이미 다른 사람에게 대여 됐습니다.");
		}
	}
	
	public void print()
	{
		System.out.println("책 제목 : " + title
				+ ", 책 작가 : " + author + ", 대여 유무 : " + (stateCode == 1? "재고있음" : "대여중"));
		
	}
}
