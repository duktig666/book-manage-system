package Model.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import Model.table.Book;

/**
 * ͼ����Ϣ�����ɾ�Ĳ�
 * 
 * @author rsw
 *
 */
public class BookAccess {
	/**
	   * ��ȷ��ѯ��ģ����ѯ��������ͼ�����ͣ�
	 */
	public Vector<Vector<Object>> inithavesold(String ISBN, String b_name, String author) throws SQLException {
		String sql = "SELECT b_id,ISBN,b_name,bt_name,author,press,price,inventory from bookms.book LEFT JOIN bookms.booktype "
				+ "ON bookms.book.booktype=bookms.booktype.bt_id WHERE (ISBN LIKE ? OR b_name LIKE ? OR author LIKE ? )";
		return Connect.queryDim_public(sql, ISBN, b_name, author);
	}

	/**
	 * ��ѯȫ��ͼ��
	 */
	public Vector<Vector<Object>> seleBook() throws SQLException {
		String sql = "SELECT b_id,ISBN,b_name,bt_name,author,press,price,inventory from bookms.book LEFT JOIN bookms.booktype "
				+ "ON bookms.book.booktype=bookms.booktype.bt_id ";
		return Connect.queryExact_public(sql);
	}

	/**
	 * ��ȷ��ѯ��ģ����ѯ������ͼ�����ͣ�
	 */
	public Vector<Vector<Object>> queryBook(String ISBN, String b_name, String author, String b_type)
			throws SQLException {
		String sql = "SELECT b_id,ISBN,b_name,bt_name,author,press,price,inventory from bookms.book LEFT JOIN bookms.booktype  "
				+ " ON bookms.book.booktype=bookms.booktype.bt_id WHERE (ISBN LIKE ? OR b_name LIKE ? OR author LIKE ? ) AND bt_name=? ";
		Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>(); // �洢�������ݣ�����ÿ��С��Vector�Ǵ浥�е�
		Connection conn = Connect.connectMySQL();// �������ݿ�����ӷ���
		PreparedStatement ptmt = conn.prepareStatement(sql);
		ptmt.setString(1, "%" + ISBN + "%");
		ptmt.setString(2, "%" + b_name + "%");
		ptmt.setString(3, "%" + author + "%");
		ptmt.setString(4, b_type);
		ResultSet rs = ptmt.executeQuery();
		while (rs.next()) {
			Vector<Object> vec = new Vector<Object>();// ��������浥�еģ����ŵ�����Ĵ��Vector����
			// �������ݿ���ÿ�еĽ���� column��Ҫ����������
			for (int i = 1; i <= 8; i++) {
				vec.add(rs.getObject(i));
			}
			dataVector.add(vec);
		}
		Connect.closeMySQL();// �ر�����
		return dataVector;
	}

	/**
	 * ��ѯͼ������
	 */
	public Vector<String> seleB_type() throws SQLException {
		Connection conn = Connect.connectMySQL();
		Vector<String> bt_name = new Vector<String>();
		String sql = "SELECT bt_name from bookms.booktype ORDER BY bt_id ASC";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			bt_name.add(rs.getString("bt_name"));
		}
		Connect.closeMySQL();// �ر�����
		return bt_name;
	}

	/**
	 * ��ѯͼ������id
	 */
	public int seleB_name(String name) throws SQLException {
		Connection conn = Connect.connectMySQL();
		String sql = "SELECT b_id from bookms.book WHERE b_name=?";
		int count = 0;
		PreparedStatement pr = conn.prepareStatement(sql);
		pr.setString(1, name);
		ResultSet rs = pr.executeQuery();
		while (rs.next()) { // �������ݿ������
			count = rs.getInt(1);
		}
		Connect.closeMySQL();// �ر�����
		return count;
	}

	/**
	 * ɾ��ͼ��
	 */
	public void dropBook(int b_id) throws SQLException {
		String sql = "DELETE FROM bookms.book WHERE b_id=?";
		Connect.update_public(sql, b_id);
	}

	/**
	 * ����ͼ��
	 */
	public void insterBook(String ISBN, String b_name, int booktype, String author, String press, double price,
			int inventory) throws SQLException {
		String sql = "INSERT INTO bookms.book(ISBN,b_name,booktype,author,press,price,inventory) VALUES(?,?,?,?,?,?,?)";
		Connect.update_public(sql, ISBN, b_name, booktype, author, press, price, inventory);
	}

	/**
	 * �޸�ͼ����Ϣ
	 */
	public void updateBook(String ISBN, String b_name, String author, String press, double price, int inventory,
			int b_id) throws SQLException {
		String sql = "UPDATE bookms.book SET ISBN=?,b_name=?,author=?,press=?,price=?,inventory=? WHERE b_id=?";
		Connect.update_public(sql, ISBN, b_name, author, press, price, inventory, b_id);
	}

	/**
	 * ��ѯ�Ƿ���ͼ��߱���ͼ������ ɾ����ͼ������ǰ����֤û��ͼ��Ӧ�ô�ͼ������
	 */
	public boolean existBooktype(int bt_id) throws SQLException {
		String sql = "SELECT booktype FROM bookms.book WHERE booktype=?";
		return Connect.exist(sql, bt_id);
	}

	/**
	 * ��ѯͼ��ISBN�Ƿ���� �����ڣ�����ע��
	 */
	public boolean isISBN(String ISBN) throws SQLException {
		String sql = "SELECT ISBN FROM bookms.book WHERE ISBN=?";
		return Connect.exist(sql, ISBN);
	}
}
