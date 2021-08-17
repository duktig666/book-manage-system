package Controller;

import java.sql.SQLException;
import java.util.Vector;

import Model.access.BookAccess;

/**
 * ͼ����Ϣ��Ŀ�����
 * @author rsw
 *
 */
public class BookCon {
	BookAccess bd = new BookAccess();

	/**
	 * ��ȷ��ѯ��ģ����ѯ��������ͼ�����ͣ�
	 */
	public Vector<Vector<Object>> getVector(String ISBN,String b_name, String author) throws SQLException {
		Vector<Vector<Object>> Vdata = bd.inithavesold(ISBN,b_name, author);
		return Vdata;
	}

	/**
	 * ��ѯȫ��ͼ��
	 */
	public Vector<Vector<Object>> seleBook() throws SQLException {
		return bd.seleBook();
	}

	/**
	 * ��ȷ��ѯ��ģ����ѯ������ͼ�����ͣ�
	 */
	public Vector<Vector<Object>> getBook(String ISBN,String b_name, String author, String b_type) throws SQLException {
		Vector<Vector<Object>> bookData = bd.queryBook(ISBN,b_name, author, b_type);
		return bookData;
	}

	/**
	 * ��ѯͼ������
	 */
	public Vector<String> getB_type() throws SQLException {
		Vector<String> bt_name = bd.seleB_type();
		return bt_name;
	}

	/**
	 * ��ѯͼ������id
	 */
	public int seleB_name(String name) throws SQLException {
		int count = bd.seleB_name(name);
		return count;
	}

	/**
	 * ɾ��ͼ��
	 */
	public void dropBook(int b_id) throws SQLException {
		bd.dropBook(b_id);
	}

	/**
	 * ����ͼ��
	 */
	public void insterBook(String ISBN, String b_name, int booktype, String author, String press, double price,
			int inventory) throws SQLException {
		bd.insterBook(ISBN, b_name, booktype, author, press, price, inventory);
	}

	/**
	 * �޸�ͼ��
	 */
	public void updateBook(String ISBN, String b_name, String author, String press, double price, int inventory,
			int b_id) throws SQLException {
		bd.updateBook(ISBN, b_name, author, press, price, inventory, b_id);
	}

	/**
	 * ��ѯ�Ƿ���ͼ��߱���ͼ������ ɾ����ͼ������ǰ����֤û��ͼ��Ӧ�ô�ͼ������
	 */
	public boolean existBooktype(int bt_id) throws SQLException {
		return bd.existBooktype(bt_id);
	}
	

	/**
	 * ��ѯͼ��ISBN�Ƿ���� �����ڣ�����ע��
	 */
	public boolean isISBN(String ISBN) throws SQLException {
		return bd.isISBN(ISBN);
	}
}
