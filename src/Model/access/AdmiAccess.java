package Model.access;

import java.sql.*;
import java.util.Vector;

/**
 * ����Ա��Ϣ�����ɾ�Ĳ�
 * @author rsw
 *
 */
public class AdmiAccess {
	/**
	 * ��ѯ����Ա���˺�����
	 */
	public boolean queryAdmi(String count, String password) throws SQLException {
		String sql = "SELECT adm_count,adm_password FROM bookms.administrator WHERE adm_count=? AND adm_password=?";
		return Connect.exist(sql, count,password);
	}

	/**
	 * ��ѯָ������Ա����Ϣ��������Ա������Ϣ��
	 */
	public Vector<Vector<Object>> queryAdmi(String count) throws SQLException {
		String sql = "SELECT adm_count,adm_name,adm_id_number,adm_tele,adm_email FROM bookms.administrator WHERE adm_count=? AND issuper='0';";
		return Connect.queryExact_public(sql, count);
	}

	/**
	 * ��ѯȫ������Ա����Ϣ
	 */
	public Vector<Vector<Object>> seleAdmi() throws SQLException {
		String sql = "SELECT adm_count,adm_name,adm_id_number,adm_tele,adm_email FROM bookms.administrator WHERE issuper='0';";
		return Connect.queryExact_public(sql);
	}

	/**
	 * ɾ������Ա
	 */
	public void deleAdmi(String superNumber) throws SQLException {
		String sql = "DELETE FROM bookms.administrator WHERE adm_count=?";
		Connect.update_public(sql, superNumber);
	}

	/**
	 * �޸Ĺ���Ա��Ϣ
	 */
	public void updateAdmi(String adm_tele, String adm_email, String superNumber) throws SQLException {
		String sql = "UPDATE bookms.administrator SET adm_tele=?,adm_email=? WHERE adm_count=?";
		Connect.update_public(sql, adm_tele, adm_email, superNumber);
	}

	/**
	 * ��������Ա
	 */
	public void insterAdmi(String adm_count, String adm_name, String adm_id_number, String adm_tele, String adm_email,
			String adm_keeppass, String adm_password) throws SQLException {
		String sql = "INSERT INTO bookms.administrator(adm_count,adm_name,adm_id_number,adm_tele,adm_email,adm_keeppass,adm_password,issuper) VALUES(?,?,?,?,?,?,?,'0')";
		Connect.update_public(sql, adm_count, adm_name, adm_id_number, adm_tele, adm_email, adm_keeppass, adm_password);
	}

	/**
	 * �޸�����
	 */
	public void updateAdmiPass(String alterPass, String adm_count, String adm_password, String adm_keeppass)
			throws SQLException {
		String sql = "UPDATE bookms.administrator SET adm_password='" + alterPass
				+ "' WHERE adm_count=? AND adm_password=? AND adm_keeppass=?";
		Connect.update_public(sql, adm_count, adm_password, adm_keeppass);
	}

	/**
	 * ��������Ա��֤
	 */
	public boolean proveSuper(String count, String password) throws SQLException {
		String sql = "SELECT adm_count,adm_password FROM bookms.administrator WHERE adm_count=? AND adm_password=? AND issuper='1'";
		return Connect.exist(sql, count,password);
	}
}
