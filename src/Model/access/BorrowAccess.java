package Model.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * ���Ĺ黹��Ϣ�����ɾ�Ĳ�
 * @author rsw
 *
 */
public class BorrowAccess {

	/**
	 * ����
	 * ���������Ϣ
	 */
	public boolean insertBorrow(String number, int b_name, int borrowdate, int duedate,int b_id){
		boolean isCommit=false;
		Connection conn = Connect.connectMySQL();
		String sql = "INSERT INTO bookms.borrow(r_number,borrow_b_id,borrowdate,duedate,isreturn) VALUES(?,?,?,?,'0')";
		try {
			conn.setAutoCommit(false);//���Զ��ύ����Ϊfalse 
			PreparedStatement ptmt1 = conn.prepareStatement(sql);
			ptmt1.setString(1, number);
			ptmt1.setInt(2, b_name);
			ptmt1.setLong(3, borrowdate);
			ptmt1.setLong(4, duedate);
			ptmt1.executeUpdate();
			
			String updateInvebtorySql="UPDATE bookms.book SET inventory=inventory-1 WHERE b_id=?;";
			PreparedStatement ptmt2 = conn.prepareStatement(updateInvebtorySql);
			ptmt2.setInt(1, b_id);
			ptmt2.executeUpdate();
			
			conn.commit();//�����������ɹ����ֶ��ύ  
			isCommit=true;
		} catch (SQLException e) {
			try {
				System.out.println("����ع�");
				isCommit=false;
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}    //һ������һ�������������ع���ʹ�������������ɹ�  
			e.printStackTrace();
		}  
		Connect.closeMySQL();// �ر�����
		return isCommit;
	}
	/**
	 * ����
	 * ����ʵ�ʹ黹ʱ�䣬����isreturn���Ƿ�黹����0��Ϊ1
	 */
	public boolean returnBorrow(int returndate,int borrow_id,int b_id){
		boolean isCommit=false;
		Connection conn = Connect.connectMySQL();
		try {
			String sql = "UPDATE bookms.borrow SET returndate=?,isreturn='1' WHERE borrow_id=?";
			conn.setAutoCommit(false);//���Զ��ύ����Ϊfalse 
			PreparedStatement ptmt1 = conn.prepareStatement(sql);
			ptmt1.setInt(1, returndate);
			ptmt1.setInt(2, borrow_id);
			ptmt1.executeUpdate();
			
			String updateInvebtorySql="UPDATE bookms.book SET inventory=inventory+1 WHERE b_id=?;";
			PreparedStatement ptmt2 = conn.prepareStatement(updateInvebtorySql);
			ptmt2.setInt(1, b_id);
			ptmt2.executeUpdate();
			conn.commit();//�����������ɹ����ֶ��ύ  
			isCommit=true;
		} catch (SQLException e) {
			try {
				System.out.println("����ع�");
				isCommit=false;
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}    //һ������һ�������������ع���ʹ�������������ɹ�  
			e.printStackTrace();
			e.printStackTrace();
		}
		Connect.closeMySQL();// �ر�����
		return isCommit;	
	}	
	/**
	 * �鿴������Ϣ�ķ���
	 * @throws SQLException 
	 */
	public Vector<Vector<Object>> queryBorrowInfo(String number1,String number2,boolean isreturn) throws SQLException {
		String sql ="SELECT borrow_id,ISBN,book.b_name,bt_name,author,borrowdate,duedate,returndate FROM bookms.book,bookms.booktype,bookms.borrow WHERE bookms.book.booktype=bookms.booktype.bt_id" + 
				" AND borrow.r_number=? AND borrow.borrow_b_id=book.b_id AND book.b_id IN (SELECT borrow.borrow_b_id FROM bookms.borrow WHERE r_number=? ) AND borrow.isreturn=? ORDER BY borrowdate DESC";
		return Connect.queryExact_public(sql,number1,number2,isreturn);
	}
	/**
	 * ��ѯ�����Ƿ񱻽���
	 * @throws SQLException 
	 */
	public boolean queryExistBook(int borrow_b_id) throws SQLException {
		String sql="SELECT borrow_b_id FROM bookms.borrow WHERE borrow_b_id=? AND isreturn='0'";
		return Connect.exist(sql, borrow_b_id);
	}
	/**
	 * ��ѯ�����Ƿ񱻽���
	 */
	public boolean queryIsBorrowBook(int borrow_b_id,String count)throws SQLException {
		String sql="SELECT borrow_b_id FROM bookms.borrow WHERE borrow_b_id=? AND r_number=? AND isreturn='0'";
		return Connect.exist(sql, borrow_b_id,count);
	}
}
