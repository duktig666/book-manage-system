package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Controller.PageQueryCon;
import Controller.ReaderCon;
import Controller.ReaderTypeCon;
import Tool.InputLimit;
import Tool.PubJdialog;
import Tool.TableTool;

/**
 * ������Ϣ�������
 */
public class ManageReader {
	int row;
	String studentNumber, dept, classes, tele, email;
	String readerTypeInfo;
	boolean isCompile, refresh;
	ReaderTypeCon readerTypeCon = new ReaderTypeCon();
	ReaderCon readerCon = new ReaderCon();

	protected JPanel addPanel2() throws SQLException {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel jpanup_reader = new JPanel();
		jpanup_reader.setLayout(null);
		jpanup_reader.setPreferredSize(new Dimension(1000, 80));
		JButton[] jbt_reader = { new JButton("��ѯ������Ϣ"), new JButton("����������Ϣ"), new JButton("ɾ��������Ϣ"),
				new JButton("�޸Ķ�����Ϣ") };
		JTextField jtext_reader = new JTextField();
		JComboBox<String> jcb_reader = new JComboBox<String>();
		for (int i = 0; i < jbt_reader.length; i++) {
			jbt_reader[i].setBounds(370 + i * 130, 20, 120, 30);
			jpanup_reader.add(jbt_reader[i]);
		}
		jtext_reader.setBounds(160, 20, 200, 30);
		jtext_reader.addFocusListener(new InputLimit(jtext_reader, "ѧ��/����/Ժϵ/�༶"));//
		// ������ڲ��ʾ���ⲿ�����
		jcb_reader.setBounds(50, 20, 80, 30);
		jcb_reader.addItem("ȫ��");
		String[] readerType = readerTypeCon.getReaderType();
		for (int k = 0; k < readerType.length; k++) {
			jcb_reader.addItem(readerType[k]);
		}
		jcb_reader.setVisible(true);
		// ���������¼�
		jcb_reader.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (jcb_reader.getSelectedItem() == "ȫ��") {
						readerTypeInfo = "*";
					} else {
						readerTypeInfo = jcb_reader.getSelectedItem().toString();
					}
				}
			}
		});
		Vector<String> columnNameReader = new Vector<String>();// �ֶ���
		String[] columnReader = { "ѧ��", "����", "�Ա�", "��������", "Ժϵ", "�༶", "�ֻ�����", "��������" };
		for (int k = 0; k < columnReader.length; k++) {
			columnNameReader.add(columnReader[k]);
		}
		DefaultTableModel dfttable_reader = new DefaultTableModel(readerCon.seleReader(), columnNameReader);
		JTable table_reader = new JTable(dfttable_reader) {
			public boolean isCellEditable(int row, int column) {
				return false;// ��������༭
			}
		};
		ListSelectionModel selectionModel = table_reader.getSelectionModel();// �������ѡ����
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// һ��ֻ��ѡ��һ���б�����
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (selectionModel.getValueIsAdjusting()) {
					if(table_reader.getSelectedRow()<0) {
						return;
					}
					row=table_reader.getSelectedRow();
					studentNumber = table_reader.getValueAt(table_reader.getSelectedRow(), 0).toString();
					dept = table_reader.getValueAt(table_reader.getSelectedRow(), 4).toString();
					classes = table_reader.getValueAt(table_reader.getSelectedRow(), 5).toString();
					tele = table_reader.getValueAt(table_reader.getSelectedRow(), 6).toString();
					email = table_reader.getValueAt(table_reader.getSelectedRow(), 7).toString();
				}
			}
		});
		//���ñ��Ĺ�������
		TableTool.setTable(table_reader, dfttable_reader);
		table_reader.setPreferredScrollableViewportSize(new Dimension(900, 700));// ������Χ���ֹ�����
		JScrollPane scrollPane = new JScrollPane(table_reader);
		jpanup_reader.add(jcb_reader);
		jpanup_reader.add(jtext_reader);
		panel.add(jpanup_reader, BorderLayout.NORTH);
		panel.add(scrollPane);
		// ��ѯ
		jbt_reader[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dfttable_reader.setRowCount(0);
				dfttable_reader.fireTableDataChanged();
				try {
					if (readerTypeInfo == "*" || readerTypeInfo == null) {
						if (jtext_reader.getText().equals("ѧ��/����/Ժϵ/�༶") || jtext_reader.getText().equals("")) {
							dfttable_reader.setDataVector(readerCon.seleReader(), columnNameReader);
						} else {
							dfttable_reader.setDataVector(readerCon.queryReaderInfo(jtext_reader.getText(),
									jtext_reader.getText(), jtext_reader.getText(), jtext_reader.getText()),
									columnNameReader);
						}
					} else {
						if (jtext_reader.getText().equals("ѧ��/����/Ժϵ/�༶")) {
							jtext_reader.setText("");
						}
						dfttable_reader.setDataVector(
								readerCon.seleReaderInfo(jtext_reader.getText(), jtext_reader.getText(),
										jtext_reader.getText(), jtext_reader.getText(), readerTypeInfo),
								columnNameReader);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				studentNumber = TableTool.setNull(table_reader, studentNumber);
			}
		});
		// ����
		jbt_reader[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new Login();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				studentNumber = TableTool.setNull(table_reader, studentNumber);
			}
		});
		// ɾ��
		jbt_reader[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (studentNumber != null) {
					try {
						int c = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ��ɾ����ͼ������", "��֤����", JOptionPane.YES_NO_OPTION);
						if (c == JOptionPane.YES_OPTION) {
							readerCon.dropReader(studentNumber);
							dfttable_reader.removeRow(table_reader.getSelectedRow());// ɾ������е���һ��
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "��û��ѡ�ж��ߣ�����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
				}
				studentNumber = TableTool.setNull(table_reader, studentNumber);
			}
		});
		// ����
		jbt_reader[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JLabel[] jlab_reader = { new JLabel("ѧ�ţ�"), new JLabel("Ժϵ��"), new JLabel("�༶��"), new JLabel("�ֻ��ţ�"),
						new JLabel("���䣺") };
				JLabel[] jlab_hint = { new JLabel("�����޸�"), new JLabel("���ĺ���"), new JLabel("���ĺ��ֻ�������"),
						new JLabel("13��14��15��17��18��ͷ"), new JLabel("�����ʽ")};
				JTextField[] jtext_reader = new JTextField[5];
				Object[] readerUpdata = { studentNumber, dept, classes, tele, email };
				if (studentNumber != null) {
					try {
						new PubJdialog(210, 5, jlab_reader, jtext_reader, readerUpdata, 2,jlab_hint).setVisible(true);
						if (PubJdialog.success) {
							table_reader.setValueAt(jtext_reader[1].getText(), row, 4);
							table_reader.setValueAt(jtext_reader[2].getText(), row, 5);
							table_reader.setValueAt(jtext_reader[3].getText(), row, 6);
							table_reader.setValueAt(jtext_reader[4].getText(), row, 7);
							PubJdialog.success=false;
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "��û��ѡ�ж��ߣ�����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
				}
				studentNumber = TableTool.setNull(table_reader, studentNumber);
			}
		});
		return panel;
	}
}
