package Controller;

import java.sql.SQLException;
import java.util.Vector;

import Model.access.AdmiAccess;

/**
 * 管理员表的控制器
 * @author rsw
 *
 */
public class AdmiCon {
	AdmiAccess admiDao = new AdmiAccess();

	/**
	 * 管理员登录
	 */
	public boolean queryAdmi(String count, String password) throws SQLException {
		boolean findAdmi = admiDao.queryAdmi(count, password);
		return findAdmi;
	}

	/**
	 * 查询指定管理员的信息（含管理员自身信息）
	 */
	public Vector<Vector<Object>> queryAdmi(String count) throws SQLException {
		return admiDao.queryAdmi(count);
	}

	/**
	 * 查询全部管理员的信息
	 */
	public Vector<Vector<Object>> seleAdmi() throws SQLException {
		return admiDao.seleAdmi();
	}

	/**
	 * 删除管理员
	 */
	public void deleAdmi(String superNumber) throws SQLException {
		admiDao.deleAdmi(superNumber);
	}

	/**
	 * 修改管理员信息
	 */
	public void updateAdmi(String adm_tele, String adm_email, String superNumber) throws SQLException {
		admiDao.updateAdmi(adm_tele, adm_email, superNumber);
	}

	/**
	 * 新增管理员
	 */
	public void insterAdmi(String adm_count, String adm_name, String adm_id_number, String adm_tele, String adm_email,
			String adm_keeppass, String adm_password) throws SQLException {
		admiDao.insterAdmi(adm_count, adm_name, adm_id_number, adm_tele, adm_email, adm_keeppass, adm_password);
	}

	/**
	 * 修改密码
	 */
	public void updateAdmiPass(String alterPass, String adm_count, String adm_password, String adm_keeppass)
			throws SQLException {
		admiDao.updateAdmiPass(alterPass, adm_count, adm_password, adm_keeppass);
	}

	/**
	 * 超级管理员验证
	 */
	public boolean proveSuper(String count, String password) throws SQLException {
		return admiDao.proveSuper(count, password);
	}
}
