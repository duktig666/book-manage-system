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
 * ��������Ա���
 * 
 * @author rsw
 *
 */
public class ManageSuper implements ActionListener {
	JButton[] jbt_super = { new JButton("��ѯ����Ա��Ϣ"), new JButton("��������Ա��Ϣ"), new JButton("ɾ������Ա��Ϣ"),
			new JButton("�޸Ĺ���Ա��Ϣ") };
	JTextField jtext;
	JTable table_super;
	JPanel jpan;
	DefaultTableModel dfttable_super;
	String count, superNumber, adm_tele, adm_email;
	String[] columnStr = { "�˺�", "����", "���֤��", "�ֻ���", "����" };
	JButton[] jbt_proveSuper = { new JButton("�޸ĸ�����Ϣ"), new JButton("�޸ĸ�������"), new JButton("��������Ա��֤") };
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
		columnName = new Vector<String>();// �ֶ���
		for (int i = 0; i < columnStr.length; i++) {
			columnName.add(columnStr[i]);
		}
		// ���ñ��ģ�͵�����
		dfttable_super = new DefaultTableModel(admiCon.queryAdmi(count), columnName);

		// ���ñ��ı༭״̬
		table_super = new JTable(dfttable_super) {
			public boolean isCellEditable(int row, int column) {
				return false;// ��������༭
			}
		};
		ListSelectionModel selectionModel = table_super.getSelectionModel();// �������ѡ����
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// һ��ֻ��ѡ��һ���б�����
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
		// ���ñ��Ĺ�������
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
		// ��ѯ
		if (e.getSource() == jbt_super[0]) {
			count = jtext.getText();
			// ���ñ�ͷ
			refresh = true;
			dfttable_super.fireTableDataChanged();
			dfttable_super.setRowCount(0);
			try {
				if (count.equals("")) {
					dfttable_super.setDataVector(admiCon.seleAdmi(), columnName);
				} else {
					dfttable_super.setDataVector(admiCon.queryAdmi(count), columnName);// �趨ģ�����ݺ��ֶ�,��ʼ���ñ�
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			refresh = false;
		}
		// ����
		else if (e.getSource() == jbt_super[1]) {
			boolean is = false;
			try {
				new PubJdialog(is).setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// ɾ��
		else if (e.getSource() == jbt_super[2]) {
			if (superNumber != null) {
				try {
					// �ж�ɾ���Ĺ���Ա�˺��Ƿ�ΪĿǰ����¼���˺ţ�����ǡ��������Ի��򡢽������򡢴򿪵�¼����
					if (superNumber.equals(count)) {
						int c = JOptionPane.showConfirmDialog(null, "���˺�Ϊ������¼�Ĺ���Ա�˺ţ����ɾ���˹���Ա�˺ţ�����Ҫ���µ�¼���Ƿ�ִ�д˲���������",
								"���棡����", JOptionPane.WARNING_MESSAGE);
						if (c == JOptionPane.YES_OPTION) {
							admiCon.deleAdmi(superNumber);
							dfttable_super.removeRow(table_super.getSelectedRow());// ɾ������е���һ��
							System.exit(0);
							new Lading();
						}
					} else {
						int c = JOptionPane.showConfirmDialog(null, "�Ƿ�Ҫɾ�����˺�", "���棡����", JOptionPane.WARNING_MESSAGE);
						if (c == JOptionPane.YES_OPTION) {
							admiCon.deleAdmi(superNumber);
							dfttable_super.removeRow(table_super.getSelectedRow());// ɾ������е���һ��
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "��û��ѡ�й���Ա������", "����ʧ��", JOptionPane.WARNING_MESSAGE);
			}
		}
		// ����
		else if (e.getSource() == jbt_super[3]) {
			JLabel[] jlab_super = { new JLabel("�˺ţ�"), new JLabel("�ֻ��ţ�"), new JLabel("���䣺") };
			JTextField[] jtext_super = new JTextField[3];
			JLabel[] jlab_hint = { new JLabel("�����޸�"), new JLabel("13��14��15��17��18��ͷ"), new JLabel("�����ʽ") };
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
				JOptionPane.showMessageDialog(null, "��û��ѡ�й���Ա������", "����ʧ��", JOptionPane.ERROR_MESSAGE);
			}
		}
		// �޸ĸ�����Ϣ
		else if (e.getSource() == jbt_proveSuper[0]) {
			JLabel[] jlab_super = { new JLabel("�˺ţ�"), new JLabel("�ֻ��ţ�"), new JLabel("���䣺") };
			JLabel[] jlab_hint = { new JLabel("�����޸�"), new JLabel("13��14��15��17��18��ͷ"), new JLabel("�����ʽ") };
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
		// �޸ĸ�������
		else if (e.getSource() == jbt_proveSuper[1]) {
			new PubJdialog(count, false).setVisible(true);
		}
		// ��������Ա��֤
		else if (e.getSource() == jbt_proveSuper[2]) {
			try {
				new PubJdialog(jtext, jbt_super).setVisible(true);
				if (PubJdialog.success) {
					dfttable_super.setDataVector(admiCon.seleAdmi(), columnName);
					for (int i = 0; i < jbt_proveSuper.length; i++) {
						jbt_proveSuper[i].setEnabled(false);// ��������Ա��֤�ɹ������˹���Ա��Ϣ��ť������
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
