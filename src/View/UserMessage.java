package View;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Controller.ReaderCon;
import Controller.ReaderTypeCon;
import Tool.PubJdialog;
import Tool.TableTool;

/**
 * 用户信息界面
 * 
 * @author rsw
 *
 */
public class UserMessage {
	boolean isAlter = false;
	JButton alterX, alterM;
	ReaderCon readercon = new ReaderCon();
	ReaderTypeCon readerTypeCon = new ReaderTypeCon();

	/**
	 * 注释
	 * 
	 * @return
	 * @throws SQLException
	 */
	protected JPanel addPanel2() throws SQLException {
		JPanel panel = new JPanel();
		JPanel panel_table = new JPanel(new BorderLayout());
		JPanel panel_jurisdiction = new JPanel(new BorderLayout());
		JLabel jlab = new JLabel("个人权限信息");
		alterX = new JButton("修改个人信息");
		alterM = new JButton("修改密码");
		panel.setLayout(null);

		Vector<Vector<Object>> readerInfo = readercon.queryReaderInfo(UserFace.count);
		String[] columnReader = { "学号", "姓名", "性别", "院系", "班级", "手机号码", "电子邮箱" };
		String[] columnReaderType = { "读者类型", "最大可借阅数量", "最大可借阅天数" };
		Vector<String> columnNameReader = new Vector<String>();// 字段名
		Vector<String> columnNameReaderType = new Vector<String>();// 字段名
		for (int k = 0; k < columnReader.length; k++) {
			columnNameReader.add(columnReader[k]);
		}
		for (int k = 0; k < columnReaderType.length; k++) {
			columnNameReaderType.add(columnReaderType[k]);
		}
		DefaultTableModel dfttable_reader = new DefaultTableModel(readerInfo, columnNameReader);
		DefaultTableModel dfttable_readerType = new DefaultTableModel(readerTypeCon.queryPersonalType(UserFace.count),
				columnNameReaderType);

		JTable table_reader = new JTable(dfttable_reader) {
			public boolean isCellEditable(int row, int column) {
				return false;// 表格不允许被编辑
			}
		};
		JTable table_readerType = new JTable(dfttable_readerType) {
			public boolean isCellEditable(int row, int column) {
				return false;// 表格不允许被编辑
			}
		};
		//设置不可选中行
		table_reader.setRowSelectionAllowed(false);
		table_readerType.setRowSelectionAllowed(false);
		// 设置表格的公共属性
		TableTool.setTable(table_reader, dfttable_reader);
		TableTool.setTable(table_readerType, dfttable_readerType);

		alterX.setBounds(300, 20, 120, 40);
		alterM.setBounds(500, 20, 120, 40);
		jlab.setBounds(400, 200, 100, 30);
		jlab.setOpaque(false);
		panel_table.setBounds(0, 100, 1000, 50);
		panel_jurisdiction.setBounds(0, 300, 1000, 50);
		panel_table.add(table_reader.getTableHeader(), BorderLayout.NORTH);
		panel_table.add(table_reader, BorderLayout.CENTER);
		panel_jurisdiction.add(table_readerType.getTableHeader(), BorderLayout.NORTH);
		panel_jurisdiction.add(table_readerType, BorderLayout.CENTER);
		panel.add(panel_table);
		panel.add(panel_jurisdiction);
		panel.add(alterX);
		panel.add(alterM);
		panel.add(jlab);
		//修改个人信息
		alterX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JLabel[] jlab_reader = { new JLabel("学号："), new JLabel("院系："), new JLabel("班级："), new JLabel("手机号："),
						new JLabel("邮箱：") };
				JLabel[] jlab_hint = { new JLabel("不可修改"), new JLabel("中文汉字"), new JLabel("中文汉字加数字"),
						new JLabel("13、14、15、17、18开头"), new JLabel("邮箱格式") };
				JTextField[] jtext_reader = new JTextField[5];
				try {
					Object[] readerUpdata = { UserFace.count,
							readercon.queryReaderInfo(UserFace.count).elementAt(0).elementAt(3),
							readercon.queryReaderInfo(UserFace.count).elementAt(0).elementAt(4),
							readercon.queryReaderInfo(UserFace.count).elementAt(0).elementAt(5),
							readercon.queryReaderInfo(UserFace.count).elementAt(0).elementAt(6) };
					new PubJdialog(210, 5, jlab_reader, jtext_reader, readerUpdata, 11, jlab_hint).setVisible(true);
					if (PubJdialog.success) {
						table_reader.setValueAt(jtext_reader[1].getText(), 0, 3);
						table_reader.setValueAt(jtext_reader[2].getText(), 0, 4);
						table_reader.setValueAt(jtext_reader[3].getText(), 0, 5);
						table_reader.setValueAt(jtext_reader[4].getText(), 0, 6);
						PubJdialog.success=false;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		//修改密码
		alterM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PubJdialog(UserFace.count, true).setVisible(true);// 用对话框进行修改密码
			}
		});
		return panel;
	}
}