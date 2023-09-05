package bookmall.vo;

public class OrderBookVo {
	private int orderNo;
	private int bookNo;
	private int bookCount;
	private int bookPrice;
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public int getBookNo() {
		return bookNo;
	}
	public void setBookNo(int bookNo) {
		this.bookNo = bookNo;
	}
	public int getBookCount() {
		return bookCount;
	}
	public void setBookCount(int bookCount) {
		this.bookCount = bookCount;
	}
	public int getBookPrice() {
		return bookPrice;
	}
	public void setBookPrice(int bookPrice) {
		this.bookPrice = bookPrice;
	}
}
