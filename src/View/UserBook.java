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
import javax.swing.table.DefaultTableModel;

import Controller.BookCon;
import Controller.BorrowCon;
import Controller.PageQueryCon;
import Controller.ReaderTypeCon;
import Tool.InputLimit;
import Tool.TableTool;
import Tool.TimeTool;

/**
 * ͼ���ѯ���������
 * 
 * @author rsw
 *
 */
public class UserBook extends JPanel implements ActionListener, ItemListener {
	BookCon bookcon = new BookCon();
	JTable table;
	JTextField jtext, jtext_page;
	JScrollPane scrollPane;
	JPanel jpandown;
	DefaultTableModel dfttable;
	JComboBox<String> jcb;
	String b_type, fieldAlter;// b_type ͼ������ fieldAlter�����޸���Ϣ���ֶ�
	String[] columnStr = { "���", "ISBN", "����", "ͼ������", "����", "������", "�۸�", "�����" };
	String getname;// �������� ������У�
	int b_id = -1, inventory;
	JTextField[] borrow = new JTextField[2];
	BorrowCon borrowcon = new BorrowCon();
	int row_get, maxday, maxcount, borrowingCount;// maxday���߽�������
	JButton jbt, jbtBorrow;
	Vector<String> columnName;
	JButton[] page_jbt = { new JButton("��ҳ"), new JButton("��һҳ"), new JButton("��һҳ"), new JButton("βҳ"),
			new JButton("��ת") };
	JLabel jlab_book = new JLabel();
	int pageIndex = 1, pageCount;
	ReaderTypeCon readerTypeCon = new ReaderTypeCon();

	protected JPanel addPanel0() throws SQLException {
		JPanel jpanup = new JPanel();
		jbt = new JButton("����");
		jbtBorrow = new JButton("����");
		jcb = new JComboBox<String>();
		jtext = new JTextField();
		jtext_page = new JTextField();
		jpanup.setLayout(null);
		jpanup.setPreferredSize(new Dimension(1000, 180));
		jbt.setBounds(570, 20, 80, 30);
		jbtBorrow.setBounds(670, 20, 80, 30);
		jtext.setBounds(330, 20, 200, 30);
		jtext_page.setBounds(650, 70, 80, 30);
		jtext_page.setDocument(new InputLimit(4));// ��������
		jtext_page.addFocusListener(new InputLimit(jtext_page, "ҳ��"));// ������ڲ��ʾ���ⲿ�����
		jtext.addFocusListener(new InputLimit(jtext, "ISBN/����/����"));// ������ڲ��ʾ���ⲿ�����
		jcb.setBounds(200, 20, 80, 30);
		jlab_book.setBounds(400, 140, 150, 30);
		pageCount = new PageQueryCon(bookcon.seleBook()).pageCount();
		jlab_book.setText("��" + pageIndex + "ҳ/" + "��" + pageCount + "ҳ");
		/*
		 * forѭ���ͼ������
		 */
		jcb.addItem("ȫ��");
		for (int k = 0; k < bookcon.getB_type().size(); k++) {
			jcb.addItem(bookcon.getB_type().get(k));
		}
		jcb.setVisible(true);

		for (int i = 0; i < page_jbt.length; i++) {
			if (i == 4) {
				page_jbt[i].setBounds(730, 70, 80, 30);
			} else {
				page_jbt[i].setBounds(250 + i * 100, 70, 80, 30);
			}
			jpanup.add(page_jbt[i]);
			page_jbt[i].addActionListener(this);
		}
		// Ĭ������ҳ�����ԡ���һҳ���͡���ҳ����ť������
		page_jbt[0].setEnabled(false);
		page_jbt[1].setEnabled(false);
		// ע�����
		jbt.addActionListener(this);
		jbtBorrow.addActionListener(this);
		jcb.addItemListener(this);
		// ���ñ�ͷ
		columnName = new Vector<String>();// �ֶ���
		for (int i = 0; i < columnStr.length; i++) {
			columnName.add(columnStr[i]);
		}
		// ���ñ��ģ�͵�����
		dfttable = new DefaultTableModel(new PageQueryCon(bookcon.seleBook()).setCurentPageIndex(), columnName);

		// ���ñ��ı༭״̬
		table = new JTable(dfttable) {
			public boolean isCellEditable(int row, int column) {
				return false;// ��������༭
			}
		};
		ListSelectionModel selectionModel = table.getSelectionModel();// �������ѡ����
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// һ��ֻ��ѡ��һ���б�����
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (selectionModel.getValueIsAdjusting()) {
					if (table.getSelectedRow() < 0) {
						return;
					}
					getname = table.getValueAt(table.getSelectedRow(), 2).toString();// ��ȡ���ȡ�кŵ�ĳһ��
					b_id = Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString());
					row_get = table.getSelectedRow();
					inventory = Integer.valueOf(table.getValueAt(table.getSelectedRow(), 7).toString());
				}
			}
		});
		// ���ñ��Ĺ�������
		TableTool.setTable(table, dfttable);
		table.getColumn("����").setPreferredWidth(170);// ����ָ���еĿ��
		table.getColumn("������").setPreferredWidth(120);// ����ָ���еĿ��
		table.setRowHeight(20);
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(1000, 500));
		jpanup.add(jbt);
		jpanup.add(jbtBorrow);
		jpanup.add(jcb);
		jpanup.add(jtext);
		jpanup.add(jtext_page);
		jpanup.add(jlab_book);
		this.add(jpanup, BorderLayout.NORTH);
		this.add(scrollPane);
		// �������¼�
		return this;
	}

	/**
	 * ����ѯ����������ʾ�������
	 */
	public void setTableMolel(Vector<Vector<Object>> vector) {
		for (int i = 0; i < vector.size(); i++) {
			dfttable.setDataVector(vector, columnName);// �趨ģ�����ݺ��ֶ�,��ʼ���ñ�
		}
		table.getColumn("����").setPreferredWidth(170);// ����ָ���еĿ��
		table.getColumn("������").setPreferredWidth(120);// ����ָ���еĿ��
		table.setRowHeight(20);
	}

	/**
	 * ģ����ѯ����ȷ��ѯͼ��
	 */
	public void setTableModels() {
		String input = jtext.getText();
		Vector<Vector<Object>> bookData = null;
		try {
			bookData = bookcon.getBook(input, input, input, b_type);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < bookData.size(); i++) {
			dfttable.setDataVector(bookData, columnName);// �趨ģ�����ݺ��ֶ�,��ʼ���ñ�
		}
		table.getColumn("����").setPreferredWidth(170);// ����ָ���еĿ��
		table.getColumn("������").setPreferredWidth(120);// ����ָ���еĿ��
		table.setRowHeight(20);
	}

	public void actionPerformed(ActionEvent e) {
		// ����
		if (e.getSource() == jbt) {
			if (b_type == "*" || b_type == null) {
				try {
					if (jtext.getText().equals("") || jtext.getText().equals("ISBN/����/����")) {
						setTableMolel(new PageQueryCon(bookcon.seleBook()).setCurentPageIndex());
					} else {
						dfttable.setDataVector(bookcon.getVector(jtext.getText(), jtext.getText(), jtext.getText()),
								columnName);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				if (jtext.getText().equals("ISBN/����/����")) {
					jtext.setText("");
				}
				setTableModels();// ���þ�ȷ��ѯ��ģ����ѯ�ķ���
			}
			// ͼ�����
		} else if (e.getSource() == jbtBorrow) {
			if (b_id != -1) {
				int c = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ�����Ĵ�ͼ��", "��֤����", JOptionPane.YES_NO_OPTION);
				if (c == JOptionPane.YES_OPTION) {
					try {
						for (int i = 0; i < readerTypeCon.queryPersonalType(UserFace.count).size(); i++) {
							// �����˺Ų�ѯ���������͵�ÿ����Ŀɽ�������
							maxday = Integer.valueOf(readerTypeCon.queryPersonalType(UserFace.count).elementAt(i)
									.elementAt(2).toString());
							// ͬ���ÿɽ�������
							maxcount = Integer.valueOf(readerTypeCon.queryPersonalType(UserFace.count).elementAt(i)
									.elementAt(1).toString());
						}
						// �ѽ��ĵ�ͼ������
						borrowingCount = borrowcon.queryBorrowInfo(UserFace.count, UserFace.count, false).length;
						int borrowdate = TimeTool.getNewStamep();
						int duedate = TimeTool.getNewStamep() + maxday * 86400;// 1��86400��
						if (borrowingCount <= maxcount) {
							if (borrowcon.queryIsBorrowBook(b_id, UserFace.count) == false) {
								// �������ʱ���Ӧ�ù黹ʱ�䣬����ͼ���Ӧ�Ŀ����-1������
								if (borrowcon.insertBorrow(UserFace.count, b_id, borrowdate, duedate, b_id)) {

									if (inventory > 1) {
										dfttable.setValueAt(
												Integer.valueOf(table.getValueAt(row_get, 7).toString()) - 1, row_get,
												7);
										JOptionPane.showMessageDialog(null, "���Ѿ��ɹ����ġ�" + getname + "��", "�����ɹ�",
												JOptionPane.INFORMATION_MESSAGE);
									} else {
										JOptionPane.showMessageDialog(null, "ͼ��ݿ����Ϊ1�����ܽ��Ĵ�ͼ�飡����", "����ʧ��",
												JOptionPane.ERROR_MESSAGE);
									}
								} else {
									JOptionPane.showMessageDialog(null, "���ܽ��Ĵ�ͼ�飡����", "����ʧ��",
											JOptionPane.ERROR_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "�����ظ����Ĵ�ͼ�飡����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "�������ĵ�ͼ�������Ѿ���������Ȩ�ޣ�����", "����ʧ��",
									JOptionPane.ERROR_MESSAGE);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "��û��ѡ��ͼ�飡����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
			}
			// ��ҳ
		} else if (e.getSource() == page_jbt[0]) {
			try {
				setTableMolel(new PageQueryCon(bookcon.seleBook()).setCurentPageIndex());
				pageIndex = 1;
				jlab_book.setText("��" + pageIndex + "ҳ/" + "��" + pageCount + "ҳ");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// ��һҳ
		} else if (e.getSource() == page_jbt[1]) {
			try {
				setTableMolel(new PageQueryCon(bookcon.seleBook()).previousPage());
				if (pageIndex > 1) {
					pageIndex--;
					jlab_book.setText("��" + pageIndex + "ҳ/" + "��" + pageCount + "ҳ");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// ��һҳ
		} else if (e.getSource() == page_jbt[2]) {
			try {
				setTableMolel(new PageQueryCon(bookcon.seleBook()).nextPage());
				if (pageIndex < pageCount) {
					pageIndex++;
					jlab_book.setText("��" + pageIndex + "ҳ/" + "��" + pageCount + "ҳ");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// βҳ
		} else if (e.getSource() == page_jbt[3]) {
			try {
				setTableMolel(new PageQueryCon(bookcon.seleBook()).lastPage());
				pageIndex = pageCount;
				jlab_book.setText("��" + pageIndex + "ҳ/" + "��" + pageCount + "ҳ");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// ��תҳ��
		else if (e.getSource() == page_jbt[4]) {
			try {
				String isInt = jtext_page.getText();
				isInt = isInt.replaceAll("[0-9]", "");// �����е��������ַ��滻Ϊ��
				// ����Ϊ0����˵����
				if (isInt.length() == 0) {
					if (Integer.valueOf(jtext_page.getText()) > 0
							&& Integer.valueOf(jtext_page.getText()) <= pageCount) {
						setTableMolel(
								new PageQueryCon(bookcon.seleBook()).jumpPage(Integer.valueOf(jtext_page.getText())));
						pageIndex = Integer.valueOf(jtext_page.getText());
						jlab_book.setText("��" + pageIndex + "ҳ/" + "��" + pageCount + "ҳ");
					} else {
						JOptionPane.showMessageDialog(null, "��������ȷ��ҳ��", "����ʧ��", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "����������", "����ʧ��", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		/*
		 * ÿ�ε����ť��ͼ��id��Ϊ-1����Ĭ��û��ѡ��ͼ�� ��ȡ������ѡ��״̬ ����д��else if���󣬲������ж����¼�������else if�����д��
		 */
		b_id = TableTool.cancelTableSelected(table, b_id);
		// βҳʱ������һҳ���͡�βҳ����ť������
		if (pageIndex == pageCount) {
			page_jbt[2].setEnabled(false);
			page_jbt[3].setEnabled(false);
		} else {
			page_jbt[2].setEnabled(true);
			page_jbt[3].setEnabled(true);
		}
		// ��ҳʱ������һҳ���͡���ҳ��������
		if (pageIndex == 1) {
			page_jbt[0].setEnabled(false);
			page_jbt[1].setEnabled(false);
		} else {
			page_jbt[0].setEnabled(true);
			page_jbt[1].setEnabled(true);
		}
	}

	// ���������¼�
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if (e.getStateChange() == ItemEvent.SELECTED) {
			for (int i = 0; i < page_jbt.length; i++) {
				if (jcb.getSelectedItem() == "ȫ��") {
					b_type = "*";
				} else {
					b_type = jcb.getSelectedItem().toString();
				}
			}
		}
	}
}
