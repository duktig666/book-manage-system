package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Controller.AdmiCon;
import Tool.PubJdialog;
import Tool.TableTool;

/**
 * 超级管理员面板
 * 
 * @author rsw
 *
 */
public class ManageSuper implements ActionListener {
	JButton[] jbt_super = { new JButton("查询管理员信息"), new JButton("新增管理员信息"), new JButton("删除管理员信息"),
			new JButton("修改管理员信息") };
	JTextField jtext;
	JTable table_super;
	JPanel jpan;
	DefaultTableModel dfttable_super;
	String count, superNumber, adm_tele, adm_email;
	String[] columnStr = { "账号", "姓名", "身份证号", "手机号", "邮箱" };
	JButton[] jbt_proveSuper = { new JButton("修改个人信息"), new JButton("修改个人密码"), new JButton("超级管理员验证") };
	boolean isCompile, refresh;
	int row;
	Vector<String> columnName;
	AdmiCon admiCon = new AdmiCon();

	public ManageSuper() {

	}

	protected JPanel addPanel5(String count) throws SQLException {
		this.count = count;
		JPanel panel = new JPanel(new BorderLayout());
		JPanel jpanup_super = new JPanel();
		jpanup_super.setLayout(null);
		jpanup_super.setPreferredSize(new Dimension(1000, 300));
		panel.setPreferredSize(new Dimension(1000, 700));

		jtext = new JTextField();
		jtext.setBounds(30, 250, 150, 30);
		jtext.setEnabled(false);
		for (int i = 0; i < jbt_super.length; i++) {
			jbt_super[i].setBounds(200 + i * 160, 250, 150, 30);
			jpanup_super.add(jbt_super[i]);
			jbt_super[i].setEnabled(false);
			jbt_super[i].addActionListener(this);
		}
		for (int i = 0; i < jbt_proveSuper.length; i++) {
			jbt_proveSuper[i].setBounds(200 + i * 180, 100, 150, 30);
			jpanup_super.add(jbt_proveSuper[i]);
			jbt_proveSuper[i].addActionListener(this);
		}
		columnName = new Vector<String>();// 字段名
		for (int i = 0; i < columnStr.length; i++) {
			columnName.add(columnStr[i]);
		}
		// 设置表格模型的数据
		dfttable_super = new DefaultTableModel(admiCon.queryAdmi(count), columnName);

		// 设置表格的编辑状态
		table_super = new JTable(dfttable_super) {
			public boolean isCellEditable(int row, int column) {
				return false;// 表格不允许被编辑
			}
		};
		ListSelectionModel selectionModel = table_super.getSelectionModel();// 创建表格选择器
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 一次只能选择一个列表索引
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (selectionModel.getValueIsAdjusting()) {
					if (table_super.getSelectedRow() < 0) {
						return;
					}
					row=table_super.getSelectedRow();
					superNumber = table_super.getValueAt(table_super.getSelectedRow(), 0).toString();
					adm_tele = table_super.getValueAt(table_super.getSelectedRow(), 3).toString();
					adm_email = table_super.getValueAt(table_super.getSelectedRow(), 4).toString();
				}
			}
		});
		// 设置表格的公共属性
		TableTool.setTable(table_super, dfttable_super);

		JScrollPane scrollPane = new JScrollPane(table_super);
		jpanup_super.add(jtext);
		panel.add(jpanup_super, BorderLayout.NORTH);
		panel.add(scrollPane);

		return panel;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// 查询
		if (e.getSource() == jbt_super[0]) {
			count = jtext.getText();
			// 设置表头
			refresh = true;
			dfttable_super.fireTableDataChanged();
			dfttable_super.setRowCount(0);
			try {
				if (count.equals("")) {
					dfttable_super.setDataVector(admiCon.seleAdmi(), columnName);
				} else {
					dfttable_super.setDataVector(admiCon.queryAdmi(count), columnName);// 设定模型数据和字段,初始化该表
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			refresh = false;
		}
		// 新增
		else if (e.getSource() == jbt_super[1]) {
			boolean is = false;
			try {
				new PubJdialog(is).setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// 删除
		else if (e.getSource() == jbt_super[2]) {
			if (superNumber != null) {
				try {
					// 判断删除的管理员账号是否为目前所登录的账号，如果是——弹出对话框、结束程序、打开登录界面
					if (superNumber.equals(count)) {
						int c = JOptionPane.showConfirmDialog(null, "此账号为您所登录的管理员账号，如果删除此管理员账号，您需要重新登录。是否执行此操作？？？",
								"警告！！！", JOptionPane.WARNING_MESSAGE);
						if (c == JOptionPane.YES_OPTION) {
							admiCon.deleAdmi(superNumber);
							dfttable_super.removeRow(table_super.getSelectedRow());// 删除表格中的这一行
							System.exit(0);
							new Lading();
						}
					} else {
						int c = JOptionPane.showConfirmDialog(null, "是否要删除此账号", "警告！！！", JOptionPane.WARNING_MESSAGE);
						if (c == JOptionPane.YES_OPTION) {
							admiCon.deleAdmi(superNumber);
							dfttable_super.removeRow(table_super.getSelectedRow());// 删除表格中的这一行
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "您没有选中管理员！！！", "操作失败", JOptionPane.WARNING_MESSAGE);
			}
		}
		// 更新
		else if (e.getSource() == jbt_super[3]) {
			JLabel[] jlab_super = { new JLabel("账号："), new JLabel("手机号："), new JLabel("邮箱：") };
			JTextField[] jtext_super = new JTextField[3];
			JLabel[] jlab_hint = { new JLabel("不可修改"), new JLabel("13、14、15、17、18开头"), new JLabel("邮箱格式") };
			Object[] superUpdata = { superNumber, adm_tele, adm_email };
			if (superNumber != null) {
				try {
					new PubJdialog(150, 3, jlab_super, jtext_super, superUpdata, 10, jlab_hint).setVisible(true);
					if (PubJdialog.success) {
						table_super.setValueAt(jtext_super[1].getText(), row, 3);
						table_super.setValueAt(jtext_super[2].getText(), row, 4);
						PubJdialog.success=false;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "您没有选中管理员！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
			}
		}
		// 修改个人信息
		else if (e.getSource() == jbt_proveSuper[0]) {
			JLabel[] jlab_super = { new JLabel("账号："), new JLabel("手机号："), new JLabel("邮箱：") };
			JLabel[] jlab_hint = { new JLabel("不可修改"), new JLabel("13、14、15、17、18开头"), new JLabel("邮箱格式") };
			JTextField[] jtext_super = new JTextField[3];
			try {
				Object[] superUpdata = { count, admiCon.queryAdmi(count).elementAt(0).elementAt(3),
						admiCon.queryAdmi(count).elementAt(0).elementAt(4) };
				new PubJdialog(150, 3, jlab_super, jtext_super, superUpdata, 10, jlab_hint).setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// 修改个人密码
		else if (e.getSource() == jbt_proveSuper[1]) {
			new PubJdialog(count, false).setVisible(true);
		}
		// 超级管理员验证
		else if (e.getSource() == jbt_proveSuper[2]) {
			try {
				new PubJdialog(jtext, jbt_super).setVisible(true);
				if (PubJdialog.success) {
					dfttable_super.setDataVector(admiCon.seleAdmi(), columnName);
					for (int i = 0; i < jbt_proveSuper.length; i++) {
						jbt_proveSuper[i].setEnabled(false);// 超级管理员验证成功，个人管理员信息按钮不可用
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		superNumber = TableTool.setNull(table_super, superNumber);
	}
}
