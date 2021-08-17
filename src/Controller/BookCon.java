package Controller;

import java.sql.SQLException;
import java.util.Vector;

import Model.access.BookAccess;

/**
 * 图书信息表的控制器
 * @author rsw
 *
 */
public class BookCon {
	BookAccess bd = new BookAccess();

	/**
	 * 精确查询、模糊查询（不包含图书类型）
	 */
	public Vector<Vector<Object>> getVector(String ISBN,String b_name, String author) throws SQLException {
		Vector<Vector<Object>> Vdata = bd.inithavesold(ISBN,b_name, author);
		return Vdata;
	}

	/**
	 * 查询全部图书
	 */
	public Vector<Vector<Object>> seleBook() throws SQLException {
		return bd.seleBook();
	}

	/**
	 * 精确查询、模糊查询（包含图书类型）
	 */
	public Vector<Vector<Object>> getBook(String ISBN,String b_name, String author, String b_type) throws SQLException {
		Vector<Vector<Object>> bookData = bd.queryBook(ISBN,b_name, author, b_type);
		return bookData;
	}

	/**
	 * 查询图书类型
	 */
	public Vector<String> getB_type() throws SQLException {
		Vector<String> bt_name = bd.seleB_type();
		return bt_name;
	}

	/**
	 * 查询图书类型id
	 */
	public int seleB_name(String name) throws SQLException {
		int count = bd.seleB_name(name);
		return count;
	}

	/**
	 * 删除图书
	 */
	public void dropBook(int b_id) throws SQLException {
		bd.dropBook(b_id);
	}

	/**
	 * 新增图书
	 */
	public void insterBook(String ISBN, String b_name, int booktype, String author, String press, double price,
			int inventory) throws SQLException {
		bd.insterBook(ISBN, b_name, booktype, author, press, price, inventory);
	}

	/**
	 * 修改图书
	 */
	public void updateBook(String ISBN, String b_name, String author, String press, double price, int inventory,
			int b_id) throws SQLException {
		bd.updateBook(ISBN, b_name, author, press, price, inventory, b_id);
	}

	/**
	 * 查询是否有图书具备此图书类型 删除类图书类型前，保证没有图书应用此图书类型
	 */
	public boolean existBooktype(int bt_id) throws SQLException {
		return bd.existBooktype(bt_id);
	}
	

	/**
	 * 查询图书ISBN是否存在 若存在，不能注册
	 */
	public boolean isISBN(String ISBN) throws SQLException {
		return bd.isISBN(ISBN);
	}
}
