package Controller;

import java.sql.SQLException;
import java.util.List;

import Model.access.BookAccess;
import Model.access.BookTypeAccess;
import Model.table.BookType;

/**
 * 图书类型表的控制器
 * 
 * @author rsw
 *
 */
public class BookTypeCon {
	BookTypeAccess bookTypeDao = new BookTypeAccess();

	/**
	 * 查询图书类型id
	 */
	public int queryBTid(String bt_name) throws SQLException {
		int bookType = bookTypeDao.queryBTid(bt_name);
		return bookType;
	}

	/**
	 * 查看图书类型
	 */
	public Object[][] queryBookType() throws SQLException {
		Object[][] bookTypeData = new Object[bookTypeDao.queryBookType().size()][2];
		for (int i = 0; i < bookTypeData.length; i++) {
			BookType bookType = bookTypeDao.queryBookType().get(i);
			bookTypeData[i][0] = bookType.getBt_id();
			bookTypeData[i][1] = bookType.getBt_name();
		}
		return bookTypeData;
	}

	/**
	 * 新增图书类型
	 * 
	 * @param bt_name
	 * @throws SQLException
	 */
	public int insertBookType(String bt_name) throws SQLException {
		return bookTypeDao.insertBookType(bt_name);
	}

	/**
	 * 删除图书类型
	 */
	public void deleteBookType(int bt_id) throws SQLException {
		bookTypeDao.deleteBookType(bt_id);
	}

	/**
	 * 修改图书类型
	 * 
	 * @param bt_id
	 * @throws SQLException
	 */
	public void updateBookType(String input_bookType, int bt_id) throws SQLException {
		bookTypeDao.updateBookType(input_bookType, bt_id);
	}

}
