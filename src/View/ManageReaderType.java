package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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

import Controller.ReaderCon;
import Controller.ReaderTypeCon;
import Tool.PubJdialog;
import Tool.TableTool;

/**
 * ����������Ϣ�������
 * @author rsw
 *
 */
public class ManageReaderType {
	int a, rt_id=-1, maxcount, maxday,row;
	String readerType;
	ReaderTypeCon readerTypeCon = new ReaderTypeCon();
	ReaderCon readerCon =new ReaderCon();
	boolean isCompile, refresh;// �Ƿ���Ա༭

	protected JPanel addPanel3() throws SQLException {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel jpanup_readerType = new JPanel();
		jpanup_readerType.setLayout(null);
		jpanup_readerType.setPreferredSize(new Dimension(1000, 80));

		JButton[] jbt_readerType = {new JButton("������������"), new JButton("ɾ����������"),
				new JButton("�޸Ķ�������") };
		for (int i = 0; i < jbt_readerType.length; i++) {
			jbt_readerType[i].setBounds(300 + i * 150, 20, 120, 30);
			jpanup_readerType.add(jbt_readerType[i]);
		}
		String[] columnreaderType = { "���", "��������", "����������", "����������" };
		Object[][] readerTypeData = readerTypeCon.queryReaderType();
		DefaultTableModel dfttable_readerType = new DefaultTableModel(readerTypeData, columnreaderType);
		JTable table_readerType = new JTable(dfttable_readerType) {
			public boolean isCellEditable(int row, int column) {
				return false;// ��������༭
			}
		};
		ListSelectionModel selectionModel = table_readerType.getSelectionModel();// �������ѡ����
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// һ��ֻ��ѡ��һ���б�����
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (selectionModel.getValueIsAdjusting()) {
					if(table_readerType.getSelectedRow()<0) {
						return;
					}
					row=table_readerType.getSelectedRow();
					readerType = table_readerType.getValueAt(table_readerType.getSelectedRow(), 1).toString();
					rt_id = Integer
							.valueOf(table_readerType.getValueAt(table_readerType.getSelectedRow(), 0).toString());
					maxcount = Integer
							.valueOf(table_readerType.getValueAt(table_readerType.getSelectedRow(), 2).toString());
					maxday = Integer
							.valueOf(table_readerType.getValueAt(table_readerType.getSelectedRow(), 3).toString());
				}
			}
		});
		//���ñ��Ĺ�������
		TableTool.setTable(table_readerType, dfttable_readerType);	
		table_readerType.setPreferredScrollableViewportSize(new Dimension(900, 700));// ������Χ���ֹ���
		JScrollPane scrollPane = new JScrollPane(table_readerType);
		panel.add(jpanup_readerType, BorderLayout.NORTH);
		panel.add(scrollPane);
		// ������������
		jbt_readerType[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new PubJdialog(a).setVisible(true);
					if(PubJdialog.success) {
						dfttable_readerType.setDataVector(readerTypeCon.queryReaderType(), columnreaderType);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				rt_id = TableTool.cancelTableSelected(table_readerType, rt_id);
			}
		});
		// ɾ����������
		jbt_readerType[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rt_id != -1) {
					int c = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ��ɾ���˶�������", "��֤����", JOptionPane.YES_NO_OPTION);
					if (c == JOptionPane.YES_OPTION) {
						try {
							if(readerCon.existReadertype(rt_id)) {
								JOptionPane.showMessageDialog(null, "�˶��������Ѿ��ж���ʹ�ã��볢�Խ��˶������͵Ķ���ɾ������ɾ���˶������ͣ�����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
							}else {
								System.out.println(rt_id);
							readerTypeCon.deleteRederType(rt_id);
							dfttable_readerType.removeRow(table_readerType.getSelectedRow());
							}
						} catch (SQLException e1) {

							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "��û��ѡ�ж������ͣ�����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
				}
				rt_id = TableTool.cancelTableSelected(table_readerType, rt_id);
			}
		});
		// �޸Ķ�������
		jbt_readerType[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JLabel[] jlab_readerType = { new JLabel("����ID��"), new JLabel("�������ͣ�"), new JLabel("������������"),
						new JLabel("������������") };
				JLabel[] jlab_hint = { new JLabel("�����޸�"), new JLabel("���ĺ���"), new JLabel("����"),
						new JLabel("����")};
				JTextField[] jtext_readerType = new JTextField[4];
				Object[] readerTypeUpdata = { rt_id, readerType, maxcount, maxday };
				if (rt_id != -1) {
					try {
						// �����޸Ķ������͵ĶԻ���
						new PubJdialog(180, 4, jlab_readerType, jtext_readerType, readerTypeUpdata, 3,jlab_hint).setVisible(true);
						if (PubJdialog.success) {
							table_readerType.setValueAt(jtext_readerType[1].getText(), row, 1);
							table_readerType.setValueAt(jtext_readerType[2].getText(), row, 2);
							table_readerType.setValueAt(jtext_readerType[3].getText(), row, 3);
							PubJdialog.success=false;
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "��û��ѡ�ж������ͣ�����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
				}
				rt_id = TableTool.cancelTableSelected(table_readerType, rt_id);
			}
		});
		return panel;
	}
}
