
package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Controller.BookCon;
import Controller.BookTypeCon;
import Tool.InputLimit;
import Tool.TableTool;

/**
 * 图书类型信息管理面板
 * @author rsw
 */
public class ManageBookType {
	BookTypeCon bookTypeCon = new BookTypeCon();
	BookCon bookCon = new BookCon();
	int bt_id = -1;
	String bt_name;

	protected JPanel addPanel1() throws SQLException {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel jpanup_bookType = new JPanel();
		jpanup_bookType.setLayout(null);
		jpanup_bookType.setPreferredSize(new Dimension(1000, 80));
		JButton[] jbt_bookType = { new JButton("新增图书类型"), new JButton("删除图书类型"),
				new JButton("修改图书类型") };
		for (int i = 0; i < jbt_bookType.length; i++) {
			jbt_bookType[i].setBounds(300 + i * 150, 20, 120, 30);
			jpanup_bookType.add(jbt_bookType[i]);
		}

		String[] columnBook = { "序号", "图书类型" };
		Object[][] bookTypeData = bookTypeCon.queryBookType();
		DefaultTableModel dfttable_bookType = new DefaultTableModel(bookTypeData, columnBook);
		JTable table_bookType = new JTable(dfttable_bookType) {
			public boolean isCellEditable(int row, int column) {
				return false;// 表格不允许被编辑
			}
		};
		ListSelectionModel selectionModel = table_bookType.getSelectionModel();// 创建表格选择器
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 一次只能选择一个列表索引
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (selectionModel.getValueIsAdjusting()) {
					bt_id = Integer.valueOf(table_bookType.getValueAt(table_bookType.getSelectedRow(), 0).toString());
					bt_name = table_bookType.getValueAt(table_bookType.getSelectedRow(), 0).toString();
				}
			}
		});
		// 设置表格的公共属性
		TableTool.setTable(table_bookType, dfttable_bookType);

		JScrollPane scrollPane = new JScrollPane(table_bookType);
		panel.add(jpanup_bookType, BorderLayout.NORTH);
		panel.add(scrollPane);
		// 新增图书类型
		jbt_bookType[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input_bookType = JOptionPane.showInputDialog(null, "请您输入所要添加的图书类型", "新增图书类型",
						JOptionPane.YES_NO_OPTION);
				try {
					if (input_bookType != null && !input_bookType.equals("")) {
						String regex =InputLimit.CHINESE ;
						boolean result = InputLimit.regular(regex, input_bookType);
						if (result) {
							int c = JOptionPane.showConfirmDialog(null, "是否确定新增此图书类型", "验证操作",
									JOptionPane.YES_NO_OPTION);
							if (c == JOptionPane.YES_OPTION) {
								bookTypeCon.insertBookType(input_bookType);
								Object[] obj= {bookTypeCon.insertBookType(input_bookType),input_bookType};
								dfttable_bookType.addRow(obj);
								JOptionPane.showMessageDialog(null, "新增图书类型——"+input_bookType, "操作成功", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "您所输入的数据格式错误！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "您所输入的数据为空！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				bt_id = TableTool.cancelTableSelected(table_bookType, bt_id);
			}
		});
		// 删除图书类型
		jbt_bookType[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (bt_id != -1) {
						int c = JOptionPane.showConfirmDialog(null, "是否确定删除此图书类型", "验证操作", JOptionPane.YES_NO_OPTION);
						if (c == JOptionPane.YES_OPTION) {
							if (bookCon.existBooktype(bt_id)) {
								JOptionPane.showMessageDialog(null, "此图书类型已经有图书使用，请尝试将此图书类型的图书删除后在删除此图书类型！！！", "操作失败",
										JOptionPane.ERROR_MESSAGE);
							} else {
								bookTypeCon.deleteBookType(bt_id);
								dfttable_bookType.removeRow(table_bookType.getSelectedRow());
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "您没有选中图书类型！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				bt_id = TableTool.cancelTableSelected(table_bookType, bt_id);
			}
		});
		// 修改图书类型
		jbt_bookType[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bt_id != -1) {
					String input_bookType = JOptionPane.showInputDialog(null, "请您输入所要修改的图书类型(只能输入汉字)", "修改图书类型",
							JOptionPane.YES_NO_OPTION);
					try {
						if (input_bookType != null && !input_bookType.equals("")) {
							String regex = "^[\\u4e00-\\u9fa5]{0,}$";
							boolean result = InputLimit.regular(regex, input_bookType);
							if (result) {
								int c = JOptionPane.showConfirmDialog(null, "是否确定修改此图书类型", "验证操作",
										JOptionPane.YES_NO_OPTION);
								if (c == JOptionPane.YES_OPTION) {
									bookTypeCon.updateBookType(input_bookType, bt_id);
									dfttable_bookType.setValueAt(input_bookType, table_bookType.getSelectedRow(), 1);// 将修改的信息放进表格
								}

							} else {
								JOptionPane.showMessageDialog(null, "您所输入的数据格式错误！！！", "操作失败",
										JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "您所输入的数据为空！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "未选择图书类型！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
				}
				bt_id = TableTool.cancelTableSelected(table_bookType, bt_id);
			}
		});
		return panel;
	}
}
