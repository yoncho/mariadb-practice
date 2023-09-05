package bookmall.vo;

import bookmall.dao.BookDao;

public class CartVo {
	private int memberNo;
	private int bookNo;
	private int bookCount;
    public BookVo bookVo;
    
	public int getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}
	public int getBookNo() {
		
		return bookNo;
	}
	public void setBookNo(int bookNo) {
		bookVo = new BookDao().findByBookNo(bookNo);
		this.bookNo = bookNo;
	}
	public int getBookCount() {
		return bookCount;
	}
	public void setBookCount(int bookCount) {
		this.bookCount = bookCount;
	}
	@Override
	public String toString() {
		return "도서 제목 : " + bookVo.getTitle() + 
				" | 수량 : " + this.bookCount +
				" | 가격 : " + bookVo.getPrice();
	}
}
