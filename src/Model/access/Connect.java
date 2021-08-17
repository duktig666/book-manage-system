package Model.access;

import java.sql.*;
import java.util.Vector;

/**
 * 公共的sql连接及增删改查方法
 * @author rsw
 *
 */
public class Connect {
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/bookms?autoReconnect=true&useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true";
	private static String user = "root";
	private static String password = "159357asd";
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet rs = null;

	
	/**
	 * 方法二 connectMySQL()方法连接数据库
	 */
	public static Connection connectMySQL() {
		try {
			Class.forName(driver);// 加载MySQL驱动
			// 下面建立驱动程序与数据库的连接
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * query()方法执行SELECT语句
	 */
	public ResultSet query(String sql) throws SQLException {
		if (sql == null || sql.equals("")) {
			return null;
		}
		rs = stmt.executeQuery(sql);
		return rs;
	}

	/**
	 * update()方法执行INSERT、UPDATE、DELETE语句
	 */
	public int update(String sql) throws SQLException {
		int count;
		if (sql == null || sql.equals("")) {
			return 0;
		}
		count = stmt.executeUpdate(sql);
		return count;
	}

	/**
	 * excute()方法既可以执行SELEYE语句，也可以执行INSERT、UPDATE、DELETE语句
	 */
	public ResultSet excute(String sql) throws SQLException {
		boolean isSelete;
		if (sql == null || sql.equals("")) {
			return null;
		}
		isSelete = stmt.execute(sql);// 将excute()方法的返回值赋给isSelete
		// 如果isSelete的值为TRUE，则excute()方法执行了SELETE语句
		if (isSelete == true) {
			rs = stmt.getResultSet();
			return rs;
		}
		// 如果isSelete的值为不是TRUE，则excute()方法执行了INSERT、UPDATE、DELETE语句
		else {
			int count = stmt.getUpdateCount();
			System.out.println("更新的记录数是：" + count);
			return null;
		}
	}

	/**
	 * closeMySQL()方法关闭与MySQL连接
	 */
	public static void closeMySQL() {
		try {
			if (rs != null)
				rs.close();
			rs = null;
			if (stmt != null)
				stmt.close();
			stmt = null;
			if (conn != null)
				conn.close();
			conn = null;
		} catch (Exception e) {
			e.printStackTrace();// 输出当前异常对象的堆栈使用轨迹
		}
	}

	/**
	 * 利用object...传参 公共的insert、update、delect方法
	 * 
	 * @throws SQLException
	 */
	public static void update_public(String sql, Object... parameter) throws SQLException {
		connectMySQL();
		PreparedStatement ptmt = conn.prepareStatement(sql);
		for (int i = 0; i < parameter.length; i++) {
			ptmt.setObject(i + 1, parameter[i]);
		}
		int count = ptmt.executeUpdate();
		System.out.println("数据库执行了" + count + "条操作");
		closeMySQL();// 关闭连接
	}

	/**
	 * 利用object...传参 公共的select方法(精确查询) 返回类型为Vector<Vector<Object>> 遍历Object类型的结果集
	 */
	public static Vector<Vector<Object>> queryExact_public(String sql,Object... parameter)
			throws SQLException {
		Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>(); // 存储所有数据，里面每个小的Vector是存单行的
		connectMySQL();
		PreparedStatement ptmt = conn.prepareStatement(sql);
		for (int i = 0; i < parameter.length; i++) {
			ptmt.setObject(i + 1, parameter[i]);
		}
		ResultSet rs = ptmt.executeQuery();
		while (rs.next()) {
			Vector<Object> vec = new Vector<Object>();// 就是这个存单行的，最后放到上面的大的Vector里面
			// 遍历数据库中每列的结果集 column需要遍历的列数
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				vec.add(rs.getObject(i));
			}
			dataVector.add(vec);
		}
		closeMySQL();// 关闭连接
		return dataVector;
	}

	/**
	 * 利用object...传参 公共的select方法(模糊查询) 返回类型为Vector<Vector<Object>> 遍历Object类型的结果集
	 */
	public static Vector<Vector<Object>> queryDim_public(String sql,Object... parameter)
			throws SQLException {
		Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>(); // 存储所有数据，里面每个小的Vector是存单行的
		connectMySQL();
		PreparedStatement ptmt = conn.prepareStatement(sql);
		for (int i = 0; i < parameter.length; i++) {
			ptmt.setObject(i + 1, "%" + parameter[i] + "%");
		}
		ResultSet rs = ptmt.executeQuery();
		while (rs.next()) {
			Vector<Object> vec = new Vector<Object>();// 就是这个存单行的，最后放到上面的大的Vector里面
			// 遍历数据库中每列的结果集 column需要遍历的列数
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				vec.add(rs.getObject(i));
			}
			dataVector.add(vec);
		}
		closeMySQL();// 关闭连接
		return dataVector;
	}

	/**
	 * 利用object...预处理 查询目标记录是否在数据库中存在
	 */
	public static boolean exist(String sql, Object... parameter) throws SQLException {
		boolean exist = false;
		Connection conn = Connect.connectMySQL();// 调用数据库的连接方法
		PreparedStatement ptmt = conn.prepareStatement(sql);
		for (int i = 0; i < parameter.length; i++) {
			ptmt.setObject(i + 1, parameter[i]);
		}
		ResultSet rs = ptmt.executeQuery();
		while (rs.next()) {
			exist = true;
		}
		closeMySQL();// 关闭连接
		return exist;
	}
}