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
 * ���Ĺ黹��Ϣ�������
 * @author rsw
 *
 */
public class ManageBorrowInfo {
	BorrowCon borrowcon = new BorrowCon();
	String adm_count;
	Object[] columnBorrow = { "���", "ISBN", "����", "ͼ������", "����", "����ʱ��", "Ӧ��ʱ��", "ʵ�ʹ黹ʱ��" };

	protected JPanel addPanel4() throws SQLException {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel jpanup_borrow = new JPanel();
		jpanup_borrow.setLayout(null);
		jpanup_borrow.setPreferredSize(new Dimension(1000, 80));
		JButton[] jbt_borrow = { new JButton("��ѯ������Ϣ"), new JButton("��ʷ������Ϣ") };
		JTextField jtext_borrow = new JTextField();
		for (int i = 0; i < jbt_borrow.length; i++) {
			jbt_borrow[i].setBounds(280 + i * 140, 20, 120, 30);
			jpanup_borrow.add(jbt_borrow[i]);
		}
		jtext_borrow.setBounds(60, 20, 200, 30);
		jtext_borrow.addFocusListener(new InputLimit(jtext_borrow, "ѧ��"));// ������ڲ��ʾ���ⲿ�����
		DefaultTableModel dfttableBorrow = new DefaultTableModel();
		JTable table_borrow = new JTable(dfttableBorrow) {
			public boolean isCellEditable(int row, int column) {
				return false;// ��������༭
			}
		};
		//���ñ��Ĺ�������
		TableTool.setTable(table_borrow, dfttableBorrow);	
		table_borrow.setPreferredScrollableViewportSize(new Dimension(900, 700));// ������Χ���ֹ�����
		table_borrow.getTableHeader().setReorderingAllowed(false); // �������в����ƶ�

		JScrollPane scrollPane = new JScrollPane(table_borrow);
		jpanup_borrow.add(jtext_borrow);
		panel.add(jpanup_borrow, BorderLayout.NORTH);
		panel.add(scrollPane);
		//��ѯ������Ϣ
		jbt_borrow[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm_count = jtext_borrow.getText();
				try {
					if (adm_count.equals("ѧ��") || adm_count.equals("")) {
						JOptionPane.showMessageDialog(null, "������ѧ�ţ�����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
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
		//��Ѱ��ʷ������Ϣ
		jbt_borrow[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm_count = jtext_borrow.getText();
				try {
					if (adm_count.equals("ѧ��") || adm_count.equals("")) {
						JOptionPane.showMessageDialog(null, "������ѧ�ţ�����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
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
