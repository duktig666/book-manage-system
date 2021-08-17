package Model.table;

/**
 * 图书信息表字段 映射
 * @author rsw
 */
public class Book {
	private int b_id;
	private String ISBN;
	private String b_name;
	private int booktype;
	private String author;
	private double price;
	private int invebtory;

	public int getB_id() {
		return b_id;
	}

	public void setB_id(int b_id) {
		this.b_id = b_id;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getB_name() {
		return b_name;
	}

	public void setB_name(String b_name) {
		this.b_name = b_name;
	}

	public int getBooktype() {
		return booktype;
	}

	public void setBooktype(int booktype) {
		this.booktype = booktype;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getInvebtory() {
		return invebtory;
	}

	public void setInvebtory(int invebtory) {
		this.invebtory = invebtory;
	}
	
}
