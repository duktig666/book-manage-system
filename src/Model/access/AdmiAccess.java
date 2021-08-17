package Model.access;

import java.sql.*;
import java.util.Vector;

/**
 * 管理员信息表的增删改查
 * @author rsw
 *
 */
public class AdmiAccess {
	/**
	 * 查询管理员的账号密码
	 */
	public boolean queryAdmi(String count, String password) throws SQLException {
		String sql = "SELECT adm_count,adm_password FROM bookms.administrator WHERE adm_count=? AND adm_password=?";
		return Connect.exist(sql, count,password);
	}

	/**
	 * 查询指定管理员的信息（含管理员自身信息）
	 */
	public Vector<Vector<Object>> queryAdmi(String count) throws SQLException {
		String sql = "SELECT adm_count,adm_name,adm_id_number,adm_tele,adm_email FROM bookms.administrator WHERE adm_count=? AND issuper='0';";
		return Connect.queryExact_public(sql, count);
	}

	/**
	 * 查询全部管理员的信息
	 */
	public Vector<Vector<Object>> seleAdmi() throws SQLException {
		String sql = "SELECT adm_count,adm_name,adm_id_number,adm_tele,adm_email FROM bookms.administrator WHERE issuper='0';";
		return Connect.queryExact_public(sql);
	}

	/**
	 * 删除管理员
	 */
	public void deleAdmi(String superNumber) throws SQLException {
		String sql = "DELETE FROM bookms.administrator WHERE adm_count=?";
		Connect.update_public(sql, superNumber);
	}

	/**
	 * 修改管理员信息
	 */
	public void updateAdmi(String adm_tele, String adm_email, String superNumber) throws SQLException {
		String sql = "UPDATE bookms.administrator SET adm_tele=?,adm_email=? WHERE adm_count=?";
		Connect.update_public(sql, adm_tele, adm_email, superNumber);
	}

	/**
	 * 新增管理员
	 */
	public void insterAdmi(String adm_count, String adm_name, String adm_id_number, String adm_tele, String adm_email,
			String adm_keeppass, String adm_password) throws SQLException {
		String sql = "INSERT INTO bookms.administrator(adm_count,adm_name,adm_id_number,adm_tele,adm_email,adm_keeppass,adm_password,issuper) VALUES(?,?,?,?,?,?,?,'0')";
		Connect.update_public(sql, adm_count, adm_name, adm_id_number, adm_tele, adm_email, adm_keeppass, adm_password);
	}

	/**
	 * 修改密码
	 */
	public void updateAdmiPass(String alterPass, String adm_count, String adm_password, String adm_keeppass)
			throws SQLException {
		String sql = "UPDATE bookms.administrator SET adm_password='" + alterPass
				+ "' WHERE adm_count=? AND adm_password=? AND adm_keeppass=?";
		Connect.update_public(sql, adm_count, adm_password, adm_keeppass);
	}

	/**
	 * 超级管理员验证
	 */
	public boolean proveSuper(String count, String password) throws SQLException {
		String sql = "SELECT adm_count,adm_password FROM bookms.administrator WHERE adm_count=? AND adm_password=? AND issuper='1'";
		return Connect.exist(sql, count,password);
	}
}
