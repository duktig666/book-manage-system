package Controller;

import java.sql.SQLException;
import java.util.Vector;

import Model.access.AdmiAccess;

/**
 * ����Ա��Ŀ�����
 * @author rsw
 *
 */
public class AdmiCon {
	AdmiAccess admiDao = new AdmiAccess();

	/**
	 * ����Ա��¼
	 */
	public boolean queryAdmi(String count, String password) throws SQLException {
		boolean findAdmi = admiDao.queryAdmi(count, password);
		return findAdmi;
	}

	/**
	 * ��ѯָ������Ա����Ϣ��������Ա������Ϣ��
	 */
	public Vector<Vector<Object>> queryAdmi(String count) throws SQLException {
		return admiDao.queryAdmi(count);
	}

	/**
	 * ��ѯȫ������Ա����Ϣ
	 */
	public Vector<Vector<Object>> seleAdmi() throws SQLException {
		return admiDao.seleAdmi();
	}

	/**
	 * ɾ������Ա
	 */
	public void deleAdmi(String superNumber) throws SQLException {
		admiDao.deleAdmi(superNumber);
	}

	/**
	 * �޸Ĺ���Ա��Ϣ
	 */
	public void updateAdmi(String adm_tele, String adm_email, String superNumber) throws SQLException {
		admiDao.updateAdmi(adm_tele, adm_email, superNumber);
	}

	/**
	 * ��������Ա
	 */
	public void insterAdmi(String adm_count, String adm_name, String adm_id_number, String adm_tele, String adm_email,
			String adm_keeppass, String adm_password) throws SQLException {
		admiDao.insterAdmi(adm_count, adm_name, adm_id_number, adm_tele, adm_email, adm_keeppass, adm_password);
	}

	/**
	 * �޸�����
	 */
	public void updateAdmiPass(String alterPass, String adm_count, String adm_password, String adm_keeppass)
			throws SQLException {
		admiDao.updateAdmiPass(alterPass, adm_count, adm_password, adm_keeppass);
	}

	/**
	 * ��������Ա��֤
	 */
	public boolean proveSuper(String count, String password) throws SQLException {
		return admiDao.proveSuper(count, password);
	}
}
