package Model.access;

import java.sql.*;
import java.util.Vector;

/**
 * ������sql���Ӽ���ɾ�Ĳ鷽��
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
	 * ������ connectMySQL()�����������ݿ�
	 */
	public static Connection connectMySQL() {
		try {
			Class.forName(driver);// ����MySQL����
			// ���潨���������������ݿ������
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * query()����ִ��SELECT���
	 */
	public ResultSet query(String sql) throws SQLException {
		if (sql == null || sql.equals("")) {
			return null;
		}
		rs = stmt.executeQuery(sql);
		return rs;
	}

	/**
	 * update()����ִ��INSERT��UPDATE��DELETE���
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
	 * excute()�����ȿ���ִ��SELEYE��䣬Ҳ����ִ��INSERT��UPDATE��DELETE���
	 */
	public ResultSet excute(String sql) throws SQLException {
		boolean isSelete;
		if (sql == null || sql.equals("")) {
			return null;
		}
		isSelete = stmt.execute(sql);// ��excute()�����ķ���ֵ����isSelete
		// ���isSelete��ֵΪTRUE����excute()����ִ����SELETE���
		if (isSelete == true) {
			rs = stmt.getResultSet();
			return rs;
		}
		// ���isSelete��ֵΪ����TRUE����excute()����ִ����INSERT��UPDATE��DELETE���
		else {
			int count = stmt.getUpdateCount();
			System.out.println("���µļ�¼���ǣ�" + count);
			return null;
		}
	}

	/**
	 * closeMySQL()�����ر���MySQL����
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
			e.printStackTrace();// �����ǰ�쳣����Ķ�ջʹ�ù켣
		}
	}

	/**
	 * ����object...���� ������insert��update��delect����
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
		System.out.println("���ݿ�ִ����" + count + "������");
		closeMySQL();// �ر�����
	}

	/**
	 * ����object...���� ������select����(��ȷ��ѯ) ��������ΪVector<Vector<Object>> ����Object���͵Ľ����
	 */
	public static Vector<Vector<Object>> queryExact_public(String sql,Object... parameter)
			throws SQLException {
		Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>(); // �洢�������ݣ�����ÿ��С��Vector�Ǵ浥�е�
		connectMySQL();
		PreparedStatement ptmt = conn.prepareStatement(sql);
		for (int i = 0; i < parameter.length; i++) {
			ptmt.setObject(i + 1, parameter[i]);
		}
		ResultSet rs = ptmt.executeQuery();
		while (rs.next()) {
			Vector<Object> vec = new Vector<Object>();// ��������浥�еģ����ŵ�����Ĵ��Vector����
			// �������ݿ���ÿ�еĽ���� column��Ҫ����������
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				vec.add(rs.getObject(i));
			}
			dataVector.add(vec);
		}
		closeMySQL();// �ر�����
		return dataVector;
	}

	/**
	 * ����object...���� ������select����(ģ����ѯ) ��������ΪVector<Vector<Object>> ����Object���͵Ľ����
	 */
	public static Vector<Vector<Object>> queryDim_public(String sql,Object... parameter)
			throws SQLException {
		Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>(); // �洢�������ݣ�����ÿ��С��Vector�Ǵ浥�е�
		connectMySQL();
		PreparedStatement ptmt = conn.prepareStatement(sql);
		for (int i = 0; i < parameter.length; i++) {
			ptmt.setObject(i + 1, "%" + parameter[i] + "%");
		}
		ResultSet rs = ptmt.executeQuery();
		while (rs.next()) {
			Vector<Object> vec = new Vector<Object>();// ��������浥�еģ����ŵ�����Ĵ��Vector����
			// �������ݿ���ÿ�еĽ���� column��Ҫ����������
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				vec.add(rs.getObject(i));
			}
			dataVector.add(vec);
		}
		closeMySQL();// �ر�����
		return dataVector;
	}

	/**
	 * ����object...Ԥ���� ��ѯĿ���¼�Ƿ������ݿ��д���
	 */
	public static boolean exist(String sql, Object... parameter) throws SQLException {
		boolean exist = false;
		Connection conn = Connect.connectMySQL();// �������ݿ�����ӷ���
		PreparedStatement ptmt = conn.prepareStatement(sql);
		for (int i = 0; i < parameter.length; i++) {
			ptmt.setObject(i + 1, parameter[i]);
		}
		ResultSet rs = ptmt.executeQuery();
		while (rs.next()) {
			exist = true;
		}
		closeMySQL();// �ر�����
		return exist;
	}
}