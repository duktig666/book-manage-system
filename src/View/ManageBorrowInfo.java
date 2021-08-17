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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Controller.BorrowCon;
import Tool.InputLimit;
import Tool.TableTool;

/**
 * 借阅归还信息管理面板
 * @author rsw
 *
 */
public class ManageBorrowInfo {
	BorrowCon borrowcon = new BorrowCon();
	String adm_count;
	Object[] columnBorrow = { "序号", "ISBN", "书名", "图书类型", "作者", "借阅时间", "应还时间", "实际归还时间" };

	protected JPanel addPanel4() throws SQLException {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel jpanup_borrow = new JPanel();
		jpanup_borrow.setLayout(null);
		jpanup_borrow.setPreferredSize(new Dimension(1000, 80));
		JButton[] jbt_borrow = { new JButton("查询借阅信息"), new JButton("历史借阅信息") };
		JTextField jtext_borrow = new JTextField();
		for (int i = 0; i < jbt_borrow.length; i++) {
			jbt_borrow[i].setBounds(280 + i * 140, 20, 120, 30);
			jpanup_borrow.add(jbt_borrow[i]);
		}
		jtext_borrow.setBounds(60, 20, 200, 30);
		jtext_borrow.addFocusListener(new InputLimit(jtext_borrow, "学号"));// 设置文诓提示的外部类监听
		DefaultTableModel dfttableBorrow = new DefaultTableModel();
		JTable table_borrow = new JTable(dfttableBorrow) {
			public boolean isCellEditable(int row, int column) {
				return false;// 表格不允许被编辑
			}
		};
		//设置表格的公共属性
		TableTool.setTable(table_borrow, dfttableBorrow);	
		table_borrow.setPreferredScrollableViewportSize(new Dimension(900, 700));// 超过范围出现滚动条
		table_borrow.getTableHeader().setReorderingAllowed(false); // 设置整列不可移动

		JScrollPane scrollPane = new JScrollPane(table_borrow);
		jpanup_borrow.add(jtext_borrow);
		panel.add(jpanup_borrow, BorderLayout.NORTH);
		panel.add(scrollPane);
		//查询借阅信息
		jbt_borrow[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm_count = jtext_borrow.getText();
				try {
					if (adm_count.equals("学号") || adm_count.equals("")) {
						JOptionPane.showMessageDialog(null, "请输入学号！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
					} else {
						dfttableBorrow.setDataVector(borrowcon.queryBorrowInfo(adm_count, adm_count, false),
								columnBorrow);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		//查寻历史借阅信息
		jbt_borrow[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm_count = jtext_borrow.getText();
				try {
					if (adm_count.equals("学号") || adm_count.equals("")) {
						JOptionPane.showMessageDialog(null, "请输入学号！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
					} else {
						dfttableBorrow.setDataVector(borrowcon.queryBorrowReturnDate(adm_count, adm_count, true),
								columnBorrow);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		return panel;
	}
}
