package Model.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * ������Ϣ�����ɾ�Ĳ�
 * 
 * @author rsw
 *
 */
public class ReaderAccess {

	/**
	 * ע��ʱ��reader���������Ϣ
	 */
	public void insertReader(String r_number, String r_name, String gender, int reader_type, String dept,
			String classes, String r_tele, String r_email, String keeppass, String r_password) throws SQLException {
		String sql = "INSERT INTO bookms.reader(number,name,gender,reader_type,dept,classes,tele,email,keeppass,password) VALUES(?,?,?,?,?,?,?,?,?,?)";
		Connect.update_public(sql, r_number, r_name, gender, reader_type, dept, classes, r_tele, r_email, keeppass,
				r_password);
	}

	/**
	 * ��ѯ�˺��Ƿ���� �����û�ע����������ߣ���֤�˺�Ψһ�ԣ�
	 */
	public boolean isNumber(String r_number) throws SQLException {
		String sql = "SELECT number FROM bookms.reader WHERE number=?";
		return Connect.exist(sql, r_number);
	}

	/**
	 * ��ѯ�˺������Ƿ����
	 */
	public boolean queryRerader(String r_number, String r_password) throws SQLException {
		String sql = "SELECT number,password FROM bookms.reader WHERE number=? AND password=?";
		return Connect.exist(sql, r_number, r_password);
	}

	/**
	 * ��ѯ������Ϣ
	 */
	public Vector<Vector<Object>> queryReaderInfo(String count) throws SQLException {
		String sql = "SELECT * FROM bookms.reader WHERE number=?";
		return Connect.queryExact_public(sql, count);
	}

	/**
	 * �޸Ķ�����Ϣ
	 */
	public void updateReader(String dept, String classes, String tele, String email, String number)
			throws SQLException {
		String sql = "UPDATE bookms.reader SET dept=?,classes=?,tele=?,email=? WHERE number=?";
		Connect.update_public(sql, dept, classes, tele, email, number);
	}

	/**
	 * �޸�����
	 * 
	 * @throws SQLException
	 */
	public void updateReaderPass(String alterPass, String r_number, String r_password, String r_keepPass)
			throws SQLException {
		String sql = "UPDATE bookms.reader SET password='" + alterPass
				+ "' WHERE number=? AND password=? AND keeppass=?";
		Connect.update_public(sql, r_number, r_password, r_keepPass);
	}

	/**
	 * ��ѯ ȫ������
	 */
	public Vector<Vector<Object>> seleReader() throws SQLException {
		String sql = "SELECT number,name,gender,rt_name,dept,classes,tele,email,logindate from bookms.reader,bookms.readertype where bookms.reader.reader_type=bookms.readertype.rt_id";
		return Connect.queryExact_public(sql);
	}

	/**
	 * ��ѯ���ߵ���Ϣ ģ����Ѱ
	 */
	public Vector<Vector<Object>> queryReaderInfo(String number, String name, String dept, String classes)
			throws SQLException {
		String sql = "SELECT number,name,gender,rt_name,dept,classes,tele,email,logindate from bookms.reader,bookms.readertype "
				+ "where bookms.reader.reader_type=bookms.readertype.rt_id AND (number LIKE ? OR name LIKE ? OR dept LIKE ? OR classes LIKE ?)";
		return Connect.queryDim_public(sql, number, name, dept, classes);
	}

	/**
	 * ��ѯ���ߵ���Ϣ ģ����Ѱ ���Ͳ�ѯ ��дԭ�򣬶����������ģ����Ѱ������ֶ�����������������
	 */
	public Vector<Vector<Object>> seleReaderInfo(String number, String name, String dept, String classes,
			String reader_type) throws SQLException {
		String sql = "SELECT number,name,gender,rt_name,dept,classes,tele,email,logindate from bookms.reader,bookms.readertype "
				+ "where bookms.reader.reader_type=bookms.readertype.rt_id AND (number LIKE ? OR name LIKE ? OR dept LIKE ? OR classes LIKE ?) AND rt_name=?";
		Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>(); // �洢�������ݣ�����ÿ��С��Vector�Ǵ浥�е�
		Connection conn = Connect.connectMySQL();// �������ݿ�����ӷ���
		PreparedStatement ptmt = conn.prepareStatement(sql);
		ptmt.setString(1, "%" + number + "%");
		ptmt.setString(2, "%" + name + "%");
		ptmt.setString(3, "%" + dept + "%");
		ptmt.setString(4, "%" + classes + "%");
		ptmt.setString(5, reader_type);
		ResultSet rs = ptmt.executeQuery();
		while (rs.next()) {
			Vector<Object> vec = new Vector<Object>();// ��������浥�еģ����ŵ�����Ĵ��Vector����
			// �������ݿ���ÿ�еĽ���� column��Ҫ����������
			for (int i = 1; i <= 9; i++) {
				vec.add(rs.getObject(i));
			}
			dataVector.add(vec);
		}
		Connect.closeMySQL();// �ر�����
		return dataVector;
	}

	/**
	 * ɾ��������Ϣ
	 */
	public void dropReader(String studentNumber) throws SQLException {
		String sql = "DELETE FROM bookms.reader WHERE number=?";
		Connect.update_public(sql, studentNumber);
	}

	/**
	 * �������� �ܱ���֤
	 */
	public boolean queryKeeppass(String forgetPass, String count) throws SQLException {
		String sql = "SELECT keeppass FROM bookms.reader WHERE keeppass=? AND number=?";
		return Connect.exist(sql, forgetPass, count);
	}

	/**
	 * �����������������
	 */
	public void resetPass(String forgetPass, String count, String newPass) throws SQLException {
		String sql = "UPDATE bookms.reader SET password='" + newPass + "' WHERE keeppass=? AND number=? ";
		Connect.update_public(sql, forgetPass, count);
	}

	/**
	 * ��ѯ�Ƿ��ж��߾߱��˶������� ɾ�����������ǰ����֤û�ж���Ӧ�ô˶�������
	 */
	public boolean existReadertype(int rt_id) throws SQLException {
		String sql = "SELECT reader_type FROM bookms.reader WHERE reader_type=?";
		return Connect.exist(sql, rt_id);

	}
}
