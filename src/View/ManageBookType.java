
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
 * ͼ��������Ϣ�������
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
		JButton[] jbt_bookType = { new JButton("����ͼ������"), new JButton("ɾ��ͼ������"),
				new JButton("�޸�ͼ������") };
		for (int i = 0; i < jbt_bookType.length; i++) {
			jbt_bookType[i].setBounds(300 + i * 150, 20, 120, 30);
			jpanup_bookType.add(jbt_bookType[i]);
		}

		String[] columnBook = { "���", "ͼ������" };
		Object[][] bookTypeData = bookTypeCon.queryBookType();
		DefaultTableModel dfttable_bookType = new DefaultTableModel(bookTypeData, columnBook);
		JTable table_bookType = new JTable(dfttable_bookType) {
			public boolean isCellEditable(int row, int column) {
				return false;// ��������༭
			}
		};
		ListSelectionModel selectionModel = table_bookType.getSelectionModel();// �������ѡ����
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// һ��ֻ��ѡ��һ���б�����
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (selectionModel.getValueIsAdjusting()) {
					bt_id = Integer.valueOf(table_bookType.getValueAt(table_bookType.getSelectedRow(), 0).toString());
					bt_name = table_bookType.getValueAt(table_bookType.getSelectedRow(), 0).toString();
				}
			}
		});
		// ���ñ��Ĺ�������
		TableTool.setTable(table_bookType, dfttable_bookType);

		JScrollPane scrollPane = new JScrollPane(table_bookType);
		panel.add(jpanup_bookType, BorderLayout.NORTH);
		panel.add(scrollPane);
		// ����ͼ������
		jbt_bookType[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input_bookType = JOptionPane.showInputDialog(null, "����������Ҫ��ӵ�ͼ������", "����ͼ������",
						JOptionPane.YES_NO_OPTION);
				try {
					if (input_bookType != null && !input_bookType.equals("")) {
						String regex =InputLimit.CHINESE ;
						boolean result = InputLimit.regular(regex, input_bookType);
						if (result) {
							int c = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ��������ͼ������", "��֤����",
									JOptionPane.YES_NO_OPTION);
							if (c == JOptionPane.YES_OPTION) {
								bookTypeCon.insertBookType(input_bookType);
								Object[] obj= {bookTypeCon.insertBookType(input_bookType),input_bookType};
								dfttable_bookType.addRow(obj);
								JOptionPane.showMessageDialog(null, "����ͼ�����͡���"+input_bookType, "�����ɹ�", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "������������ݸ�ʽ���󣡣���", "����ʧ��", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "�������������Ϊ�գ�����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				bt_id = TableTool.cancelTableSelected(table_bookType, bt_id);
			}
		});
		// ɾ��ͼ������
		jbt_bookType[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (bt_id != -1) {
						int c = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ��ɾ����ͼ������", "��֤����", JOptionPane.YES_NO_OPTION);
						if (c == JOptionPane.YES_OPTION) {
							if (bookCon.existBooktype(bt_id)) {
								JOptionPane.showMessageDialog(null, "��ͼ�������Ѿ���ͼ��ʹ�ã��볢�Խ���ͼ�����͵�ͼ��ɾ������ɾ����ͼ�����ͣ�����", "����ʧ��",
										JOptionPane.ERROR_MESSAGE);
							} else {
								bookTypeCon.deleteBookType(bt_id);
								dfttable_bookType.removeRow(table_bookType.getSelectedRow());
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "��û��ѡ��ͼ�����ͣ�����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				bt_id = TableTool.cancelTableSelected(table_bookType, bt_id);
			}
		});
		// �޸�ͼ������
		jbt_bookType[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bt_id != -1) {
					String input_bookType = JOptionPane.showInputDialog(null, "����������Ҫ�޸ĵ�ͼ������(ֻ�����뺺��)", "�޸�ͼ������",
							JOptionPane.YES_NO_OPTION);
					try {
						if (input_bookType != null && !input_bookType.equals("")) {
							String regex = "^[\\u4e00-\\u9fa5]{0,}$";
							boolean result = InputLimit.regular(regex, input_bookType);
							if (result) {
								int c = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ���޸Ĵ�ͼ������", "��֤����",
										JOptionPane.YES_NO_OPTION);
								if (c == JOptionPane.YES_OPTION) {
									bookTypeCon.updateBookType(input_bookType, bt_id);
									dfttable_bookType.setValueAt(input_bookType, table_bookType.getSelectedRow(), 1);// ���޸ĵ���Ϣ�Ž����
								}

							} else {
								JOptionPane.showMessageDialog(null, "������������ݸ�ʽ���󣡣���", "����ʧ��",
										JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "�������������Ϊ�գ�����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "δѡ��ͼ�����ͣ�����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
				}
				bt_id = TableTool.cancelTableSelected(table_bookType, bt_id);
			}
		});
		return panel;
	}
}
