package Model.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * 读者类型信息表的增删改查
 * @author rsw
 *
 */
public class ReaderTypeAccess {

	/**
	 * 查询读者类型
	 * @throws SQLException 
	 */
	public Object[][] queryReaderType() throws SQLException{
		Connection conn = Connect.connectMySQL();
		String sql = "SELECT * from bookms.readertype";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		int len = 0;
		while(rs.next()){
			len++;//遍历结果集，知道表中有多少行数据
		}
		Object[][] data_readerType = new Object[len][4];
		rs.beforeFirst();
		for(int i=0;i<len ;i++) {
			rs.next();
			data_readerType[i][0] = rs.getInt("rt_id");
			data_readerType[i][1] = rs.getString("rt_name");
			data_readerType[i][2] = rs.getInt("maxcount");
			data_readerType[i][3] = rs.getInt("maxday");
		}
		Connect.closeMySQL();// 关闭连接
		return data_readerType;
	}
	/**
	 * 查询读者类型的ID
	 */
	public int queryReaderTypeID(String reader_type) throws SQLException {
		Connection conn = Connect.connectMySQL();
		String sql = "SELECT rt_id from bookms.readertype WHERE rt_name=?";
		PreparedStatement ptmt = conn.prepareStatement(sql);
		ptmt.setString(1, reader_type);
		ResultSet rs = ptmt.executeQuery();
		int rt_id=0;
		while (rs.next()) {
			rt_id=rs.getInt("rt_id");
		}
		Connect.closeMySQL();// 关闭连接
		return rt_id;
	}
	/**
	 * 查询个人权限
	 */
	public Vector<Vector<Object>> queryPersonalType(String count) throws SQLException {
		int column=3;
		String sql="SELECT rt_name,maxcount,maxday FROM bookms.readertype "
				+ "WHERE rt_id IN ( SELECT reader_type FROM bookms.reader WHERE number=? )";
		return Connect.queryExact_public(sql,count);	
	}	
	/**
	 * 新增读者类型
	 * @throws SQLException 
	 */
	public void insertReaderType(String rt_name,int maxcont,int maxday) throws SQLException {
		String sql = "INSERT INTO bookms.readertype(rt_name,maxcount,maxday) VALUES(?,?,?)";
		Connect.update_public(sql, rt_name,maxcont,maxday);
	}
	/**
	 *删除读者类型
	 */
	public void deleteRederType(int rt_id) throws SQLException {
		String sql = "DELETE FROM bookms.readertype WHERE rt_id=?";
		Connect.update_public(sql, rt_id);
	}
	/**
	 * 修改读者类型
	 */
	public void updateRederType(String readerType,int maxcount,int maxday,int rt_id) throws SQLException {
		String sql = "UPDATE bookms.readertype SET rt_name=?,maxcount=?,maxday=? WHERE rt_id=?";
		Connect.update_public(sql,readerType,maxcount ,maxday,rt_id);
	}
}
