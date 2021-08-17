package Controller;

import java.sql.SQLException;
import java.util.Vector;

import Model.access.ReaderAccess;

/**
 * ������Ϣ��Ŀ�����
 * @author rsw
 *
 */
public class ReaderCon {
	ReaderAccess readerDao = new ReaderAccess();

	/**
	 * ע��ʱ��reader���������Ϣ
	 */
	public void insertReader(String r_number, String r_name, String gender, int reader_type, String dept,
			String classes, String r_tele, String r_email, String keeppass, String r_password) throws SQLException {
		readerDao.insertReader(r_number, r_name, gender, reader_type, dept, classes, r_tele, r_email, keeppass,
				r_password);
	}

	/**
	 * ��ѯ�˺��Ƿ���� �����û�ע����������ߣ���֤�˺�Ψһ�ԣ�
	 */
	public boolean isNumber(String r_number) throws SQLException {
		return readerDao.isNumber(r_number);
	}
	/**
	 * ��¼��֤
	 */
	public boolean queryRerader(String r_number, String r_password) throws SQLException {
		boolean find = readerDao.queryRerader(r_number, r_password);
		return find;
	}

	/**
	 * ��ѯ������Ϣ
	 */
	public Vector<Vector<Object>> queryReaderInfo(String r_number) throws SQLException {
		Vector<Vector<Object>> readerInfo = readerDao.queryReaderInfo(r_number);
		return readerInfo;
	}

	/**
	 * �޸Ķ�����Ϣ
	 */
	public void updateReader(String dept, String classes, String tele, String email, String number)
			throws SQLException {
		readerDao.updateReader(dept, classes, tele, email, number);
	}

	/**
	 * �޸��û�����
	 */
	public void updateReaderPass(String alterPass, String r_number, String r_password, String r_keepPass)
			throws SQLException {
		readerDao.updateReaderPass(alterPass, r_number, r_password, r_keepPass);
	}

	/**
	 * ��ѯ ȫ������
	 */
	public Vector<Vector<Object>> seleReader() throws SQLException {
		return readerDao.seleReader();
	}

	/**
	 * ��ѯ����������Ϣ
	 */
	public Vector<Vector<Object>> queryReaderInfo(String number, String name, String dept, String classes)
			throws SQLException {
		Vector<Vector<Object>> readerInfo = readerDao.queryReaderInfo(number, name, dept, classes);
		return readerInfo;
	}

	/**
	 * ��ѯ���ߵ���Ϣ ģ����Ѱ ���Ͳ�ѯ
	 */
	public Vector<Vector<Object>> seleReaderInfo(String number, String name, String dept, String classes,
			String reader_type) throws SQLException {
		return readerDao.seleReaderInfo(number, name, dept, classes, reader_type);
	}

	/**
	 * ɾ������
	 */
	public void dropReader(String studentNumber) throws SQLException {
		readerDao.dropReader(studentNumber);
	}

	/**
	 * �������� �ܱ���֤
	 */
	public boolean queryKeeppass(String forgetPass, String count) throws SQLException {
		return readerDao.queryKeeppass(forgetPass, count);
	}

	/**
	 * �����������������
	 */
	public void resetPass(String forgetPass, String count, String newPass) throws SQLException {
		readerDao.resetPass(forgetPass, count, newPass);
	}
	
	/**
	 * ��ѯ�Ƿ��ж��߾߱��˶�������
	 * ɾ�����������ǰ����֤û�ж���Ӧ�ô˶�������
	 */
	public boolean existReadertype(int rt_id) throws SQLException {
		return  readerDao.existReadertype(rt_id);
	}
}
