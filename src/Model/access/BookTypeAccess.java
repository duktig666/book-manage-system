package Model.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.table.BookType;

/**
 * ͼ��������Ϣ�����ɾ�Ĳ�
 * @author rsw
 *
 */
public class BookTypeAccess {
	
	/**
	 * ��ѯͼ�����͵�id
	 * @throws SQLException 
	 */
	public int queryBTid(String bt_name) throws SQLException {
		Connection conn = Connect.connectMySQL();
		String sql = "SELECT bt_id from bookms.booktype WHERE bt_name=?";
		PreparedStatement pr = conn.prepareStatement(sql);
		pr.setString(1, bt_name);
		ResultSet rs = pr.executeQuery();
		int booktype = 0;
		while (rs.next()) { // �������ݿ������
			booktype = rs.getInt("bt_id");
		}
		Connect.closeMySQL();// �ر�����
		return booktype;
	}
	
	/**
	 * ��ѯͼ�����ͼ������
	 */
	public List<BookType> queryBookType() throws SQLException {
		List<BookType> bookTypeData = new ArrayList<BookType>();
		Connection conn = Connect.connectMySQL();
		String sql = "SELECT * FROM bookms.booktype ORDER BY bt_id ASC";//��ѯ����������
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			BookType bookType=new BookType();
			bookType.setBt_id(rs.getInt("bt_id"));
			bookType.setBt_name(rs.getString("bt_name"));
			bookTypeData.add(bookType);
		}
		Connect.closeMySQL();// �ر�����
		return bookTypeData;
	}
	/**
	 * ����ͼ������
	 */
	public int insertBookType(String bt_name) throws SQLException {
		String sql = "INSERT INTO bookms.booktype(bt_name) VALUES(?);";
		Connect.update_public(sql, bt_name);
	
		int id=0;
		Connection conn = Connect.connectMySQL();
		String insterID="SELECT bt_id FROM bookms.booktype WHERE bt_name=?";
		PreparedStatement pr = conn.prepareStatement(insterID);
		pr.setString(1, bt_name);
		ResultSet rs = pr.executeQuery();
		while (rs.next()) {
			id=rs.getInt("bt_id");
		}
		return id;
	}
	/**
	 * ɾ��ͼ������
	 */
	public void deleteBookType(int bt_id) throws SQLException {
		String sql = "DELETE FROM bookms.booktype WHERE bt_id=?";
		Connect.update_public(sql, bt_id);
	}
	/**
	 * 
	 * �޸�ͼ������
	 */
	public void updateBookType(String input_bookType,int bt_id) throws SQLException {
		String sql = "UPDATE bookms.booktype SET bt_name='"+input_bookType+"' WHERE bt_id=?";
		Connect.update_public(sql, bt_id);
	}
}
