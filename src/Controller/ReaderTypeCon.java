package Controller;

import java.sql.SQLException;
import java.util.Vector;

import javax.sound.midi.SysexMessage;

import Model.access.ReaderTypeAccess;

/**
 * �������ͱ�Ŀ�����
 * @author rsw
 *
 */
public class ReaderTypeCon {
	ReaderTypeAccess readerTypeDao = new ReaderTypeAccess();

	/**
	 * ��ѯ�������ͱ��ȫ������
	 */
	public Object[][] queryReaderType() throws SQLException {
		Object[][] data_readerType =  readerTypeDao.queryReaderType();
		return data_readerType;
	}

	/**
	 * ��ѯ��������
	 */
	public String[] getReaderType() throws SQLException {
		Object[][] data_readerType = readerTypeDao.queryReaderType();
		String[] readerType = new String[data_readerType.length];
		for (int i = 0; i < data_readerType.length; i++) {
			readerType[i] = data_readerType[i][1].toString();
		}
		return readerType;
	}
	/**
	 * ��ѯ�������͵�ID
	 */
	public int queryReaderTypeID(String reader_type) throws SQLException {
		int rt_id=readerTypeDao.queryReaderTypeID(reader_type);
		return rt_id;
	}
	/**
	 * ��ѯ����Ȩ��
	 */
	public Vector<Vector<Object>> queryPersonalType(String count) throws SQLException {
		return readerTypeDao.queryPersonalType(count);
	}
	/**
	 * ������������
	 */
	public void insertReaderType(String rt_name,int maxcont,int maxday) throws SQLException {
		readerTypeDao.insertReaderType(rt_name, maxcont, maxday);
	}
	/**
	 * ɾ����������
	 */
	public void deleteRederType(int rt_id) throws SQLException {
		readerTypeDao.deleteRederType(rt_id);
	}
	/**
	 * ���¶�������
	 */
	public void updateRederType(String readerType,int maxcount,int maxday,int rt_id) throws SQLException {
		readerTypeDao.updateRederType(readerType,maxcount ,maxday,rt_id);
	}
}
