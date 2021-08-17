package Model.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * 读者信息表的增删改查
 * 
 * @author rsw
 *
 */
public class ReaderAccess {

	/**
	 * 注册时向reader表中添加信息
	 */
	public void insertReader(String r_number, String r_name, String gender, int reader_type, String dept,
			String classes, String r_tele, String r_email, String keeppass, String r_password) throws SQLException {
		String sql = "INSERT INTO bookms.reader(number,name,gender,reader_type,dept,classes,tele,email,keeppass,password) VALUES(?,?,?,?,?,?,?,?,?,?)";
		Connect.update_public(sql, r_number, r_name, gender, reader_type, dept, classes, r_tele, r_email, keeppass,
				r_password);
	}

	/**
	 * 查询账号是否存在 用于用户注册和新增读者（保证账号唯一性）
	 */
	public boolean isNumber(String r_number) throws SQLException {
		String sql = "SELECT number FROM bookms.reader WHERE number=?";
		return Connect.exist(sql, r_number);
	}

	/**
	 * 查询账号密码是否存在
	 */
	public boolean queryRerader(String r_number, String r_password) throws SQLException {
		String sql = "SELECT number,password FROM bookms.reader WHERE number=? AND password=?";
		return Connect.exist(sql, r_number, r_password);
	}

	/**
	 * 查询个人信息
	 */
	public Vector<Vector<Object>> queryReaderInfo(String count) throws SQLException {
		String sql = "SELECT * FROM bookms.reader WHERE number=?";
		return Connect.queryExact_public(sql, count);
	}

	/**
	 * 修改读者信息
	 */
	public void updateReader(String dept, String classes, String tele, String email, String number)
			throws SQLException {
		String sql = "UPDATE bookms.reader SET dept=?,classes=?,tele=?,email=? WHERE number=?";
		Connect.update_public(sql, dept, classes, tele, email, number);
	}

	/**
	 * 修改密码
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
	 * 查询 全部读者
	 */
	public Vector<Vector<Object>> seleReader() throws SQLException {
		String sql = "SELECT number,name,gender,rt_name,dept,classes,tele,email,logindate from bookms.reader,bookms.readertype where bookms.reader.reader_type=bookms.readertype.rt_id";
		return Connect.queryExact_public(sql);
	}

	/**
	 * 查询读者的信息 模糊查寻
	 */
	public Vector<Vector<Object>> queryReaderInfo(String number, String name, String dept, String classes)
			throws SQLException {
		String sql = "SELECT number,name,gender,rt_name,dept,classes,tele,email,logindate from bookms.reader,bookms.readertype "
				+ "where bookms.reader.reader_type=bookms.readertype.rt_id AND (number LIKE ? OR name LIKE ? OR dept LIKE ? OR classes LIKE ?)";
		return Connect.queryDim_public(sql, number, name, dept, classes);
	}

	/**
	 * 查询读者的信息 模糊查寻 类型查询 重写原因，读者类型如果模糊查寻，会出现读者类型相似性问题
	 */
	public Vector<Vector<Object>> seleReaderInfo(String number, String name, String dept, String classes,
			String reader_type) throws SQLException {
		String sql = "SELECT number,name,gender,rt_name,dept,classes,tele,email,logindate from bookms.reader,bookms.readertype "
				+ "where bookms.reader.reader_type=bookms.readertype.rt_id AND (number LIKE ? OR name LIKE ? OR dept LIKE ? OR classes LIKE ?) AND rt_name=?";
		Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>(); // 存储所有数据，里面每个小的Vector是存单行的
		Connection conn = Connect.connectMySQL();// 调用数据库的连接方法
		PreparedStatement ptmt = conn.prepareStatement(sql);
		ptmt.setString(1, "%" + number + "%");
		ptmt.setString(2, "%" + name + "%");
		ptmt.setString(3, "%" + dept + "%");
		ptmt.setString(4, "%" + classes + "%");
		ptmt.setString(5, reader_type);
		ResultSet rs = ptmt.executeQuery();
		while (rs.next()) {
			Vector<Object> vec = new Vector<Object>();// 就是这个存单行的，最后放到上面的大的Vector里面
			// 遍历数据库中每列的结果集 column需要遍历的列数
			for (int i = 1; i <= 9; i++) {
				vec.add(rs.getObject(i));
			}
			dataVector.add(vec);
		}
		Connect.closeMySQL();// 关闭连接
		return dataVector;
	}

	/**
	 * 删除读者信息
	 */
	public void dropReader(String studentNumber) throws SQLException {
		String sql = "DELETE FROM bookms.reader WHERE number=?";
		Connect.update_public(sql, studentNumber);
	}

	/**
	 * 忘记密码 密保验证
	 */
	public boolean queryKeeppass(String forgetPass, String count) throws SQLException {
		String sql = "SELECT keeppass FROM bookms.reader WHERE keeppass=? AND number=?";
		return Connect.exist(sql, forgetPass, count);
	}

	/**
	 * 忘记密码后重置密码
	 */
	public void resetPass(String forgetPass, String count, String newPass) throws SQLException {
		String sql = "UPDATE bookms.reader SET password='" + newPass + "' WHERE keeppass=? AND number=? ";
		Connect.update_public(sql, forgetPass, count);
	}

	/**
	 * 查询是否有读者具备此读者类型 删除类读者类型前，保证没有读者应用此读者类型
	 */
	public boolean existReadertype(int rt_id) throws SQLException {
		String sql = "SELECT reader_type FROM bookms.reader WHERE reader_type=?";
		return Connect.exist(sql, rt_id);

	}
}
