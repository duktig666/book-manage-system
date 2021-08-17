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
import Tool.InputLimit;
import Tool.PubJdialog;
import Tool.TableTool;

/**
 * ͼ����Ϣ�������
 * 
 * @author rsw
 *
 */
public class ManageBook extends JPanel implements ActionListener, ItemListener {
	String ISBN, b_name, author, press, b_type;
	int b_id = -1, inventory, row;
	double price;
	boolean isCompile, refresh;// �Ƿ���Ա༭
	JButton[] jbt_bookFind = { new JButton("��ѯͼ��"), new JButton("����ͼ��"), new JButton("ɾ��ͼ��"), new JButton("�޸�ͼ��") };
	JButton[] page_jbt = { new JButton("��ҳ"), new JButton("��һҳ"), new JButton("��һҳ"), new JButton("βҳ"),
			new JButton("��ת") };
	JTextField jtext_find, jtext_page;
	public JTable table_book;
	DefaultTableModel dfttable_book;
	JComboBox<String> jcb_bookType;
	Vector<String> columnNameBook;
	JLabel jlab_book = new JLabel();
	int pageIndex = 1, pageCount;
	UserBook userBook = new UserBook();
	BookCon bookCon = new BookCon();
	BorrowCon borrowCon = new BorrowCon();

	protected JPanel addPanel0() throws SQLException {
		JPanel jpanup_book = new JPanel();
		JPanel jpandown_book = new JPanel(new BorderLayout());
		jpanup_book.setLayout(null);
		jpanup_book.setPreferredSize(new Dimension(1000, 180));
		jtext_page = new JTextField();
		jtext_find = new JTextField();
		jcb_bookType = new JComboBox<String>();
		for (int i = 0; i < jbt_bookFind.length; i++) {
			jbt_bookFind[i].setBounds(400 + i * 110, 20, 100, 30);
			jpanup_book.add(jbt_bookFind[i]);
			jbt_bookFind[i].addActionListener(this);
		}
		for (int i = 0; i < page_jbt.length; i++) {
			if (i == 4) {
				page_jbt[i].setBounds(730, 70, 80, 30);
			} else {
				page_jbt[i].setBounds(250 + i * 100, 70, 80, 30);
			}
			jpanup_book.add(page_jbt[i]);
			page_jbt[i].addActionListener(this);
		}
		// Ĭ������ҳ�����ԡ���һҳ���͡���ҳ����ť������
		page_jbt[0].setEnabled(false);
		page_jbt[1].setEnabled(false);
		jtext_find.setBounds(160, 20, 200, 30);
		jtext_page.setBounds(650, 70, 80, 30);
		jtext_page.setDocument(new InputLimit(4));// ��������
		jtext_page.addFocusListener(new InputLimit(jtext_page, "ҳ��"));// ������ڲ��ʾ���ⲿ�����
		jtext_find.addFocusListener(new InputLimit(jtext_find, "ISBN/����/����"));// ������ڲ��ʾ���ⲿ�����
		jcb_bookType.setBounds(50, 20, 80, 30);
		jcb_bookType.addItem("ȫ��");
		jcb_bookType.addItemListener(this);
		for (int k = 0; k < bookCon.getB_type().size(); k++) {
			jcb_bookType.addItem(bookCon.getB_type().get(k));
		}
		jcb_bookType.setVisible(true);
		jpanup_book.add(jcb_bookType);
		// ��ҳ��ʾ
		jlab_book.setBounds(400, 140, 150, 30);
		pageCount = new PageQueryCon(bookCon.seleBook()).pageCount();
		jlab_book.setText("��" + pageIndex + "ҳ/" + "��" + pageCount + "ҳ");
		jpanup_book.add(jlab_book);
		columnNameBook = new Vector<String>();// �ֶ���
		String[] columnBook = { "���", "ISBN", "����", "ͼ������", "����", "������", "�۸�", "�����" };
		Vector<Vector<Object>> bookData = null;
		bookData = bookCon.getVector(jtext_find.getText(), jtext_find.getText(), jtext_find.getText());// ���ò�ѯͼ��ķ���
		for (int k = 0; k < columnBook.length; k++) {
			columnNameBook.add(columnBook[k]);
		}
		dfttable_book = new DefaultTableModel(new PageQueryCon(bookCon.seleBook()).setCurentPageIndex(),
				columnNameBook);
		table_book = new JTable(dfttable_book) {
			public boolean isCellEditable(int row, int column) {
				return false;// ��������༭f
			}
		};
		ListSelectionModel selectionModel = table_book.getSelectionModel();// �������ѡ����
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// һ��ֻ��ѡ��һ���б�����
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (selectionModel.getValueIsAdjusting()) {
					if (table_book.getSelectedRow() < 0) {
						return;
					}
					row = table_book.getSelectedRow();
					b_id = Integer.valueOf(table_book.getValueAt(table_book.getSelectedRow(), 0).toString());
					ISBN = table_book.getValueAt(table_book.getSelectedRow(), 1).toString();
					b_name = table_book.getValueAt(table_book.getSelectedRow(), 2).toString();
					author = table_book.getValueAt(table_book.getSelectedRow(), 4).toString();
					press = table_book.getValueAt(table_book.getSelectedRow(), 5).toString();
					price = Double.valueOf(table_book.getValueAt(table_book.getSelectedRow(), 6).toString());
					inventory = Integer.valueOf(table_book.getValueAt(table_book.getSelectedRow(), 7).toString());
				}
			}
		});
		// ���ñ��Ĺ�������
		TableTool.setTable(table_book, dfttable_book);
		table_book.getColumn("����").setPreferredWidth(170);// ����ָ���еĿ��
		table_book.getColumn("������").setPreferredWidth(120);// ����ָ���еĿ��
		table_book.getTableHeader().setReorderingAllowed(false); // �������в����ƶ�
		JScrollPane scrollPane = new JScrollPane(table_book);
		scrollPane.setPreferredSize(new Dimension(1000, 500));
		jpanup_book.add(jcb_bookType);
		jpanup_book.add(jtext_find);
		jpanup_book.add(jtext_page);
		jpandown_book.add(scrollPane);
		this.add(jpanup_book, BorderLayout.NORTH);
		this.add(scrollPane);

		return this;
	}

	/**
	 * ����ѯ����������ʾ�������
	 */
	public void setTableMolel(Vector<Vector<Object>> vector) {
		for (int i = 0; i < vector.size(); i++) {
			dfttable_book.setDataVector(vector, columnNameBook);// �趨ģ�����ݺ��ֶ�,��ʼ���ñ�
		}
		table_book.getColumn("����").setPreferredWidth(170);// ����ָ���еĿ��
		table_book.getColumn("������").setPreferredWidth(120);// ����ָ���еĿ��
		table_book.setRowHeight(20);
	}

	/**
	 * ģ����ѯ����ȷ��ѯͼ��
	 */
	public void setTableModels() {
		String input = jtext_find.getText();
		Vector<Vector<Object>> bookData = null;
		try {
			bookData = bookCon.getBook(input, input, input, b_type);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < bookData.size(); i++) {
			dfttable_book.setDataVector(bookData, columnNameBook);// �趨ģ�����ݺ��ֶ�,��ʼ���ñ�
		}
		table_book.getColumn("����").setPreferredWidth(170);// ����ָ���еĿ��
		table_book.getColumn("������").setPreferredWidth(120);// ����ָ���еĿ��
		table_book.setRowHeight(20);
	}

	// ��ť�¼�
	public void actionPerformed(ActionEvent e) {
		// �鿴ͼ��
		if (e.getSource() == jbt_bookFind[0]) {
			if (b_type == "*" || b_type == null) {
				try {
					if (jtext_find.getText().equals("") || jtext_find.getText().equals("ISBN/����/����")) {
						setTableMolel(new PageQueryCon(bookCon.seleBook()).setCurentPageIndex());
					} else {
						dfttable_book.setDataVector(
								bookCon.getVector(jtext_find.getText(), jtext_find.getText(), jtext_find.getText()),
								columnNameBook);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				if (jtext_find.getText().equals("ISBN/����/����")) {
					jtext_find.setText("");
				}
				setTableModels();// ���þ�ȷ��ѯ��ģ����ѯ�ķ���
			}
		}
		// ����ͼ��
		else if (e.getSource() == jbt_bookFind[1]) {
			if (e.getSource() == jbt_bookFind[1]) {
				try {
					new PubJdialog().setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			TableTool.cancelTableSelected(table_book, b_id);
		}
		// ɾ��ͼ��
		else if (e.getSource() == jbt_bookFind[2]) {
			System.out.println(b_id);
			try {
				if (b_id != -1) {
					int c = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ��ɾ����ͼ��", "��֤����", JOptionPane.YES_NO_OPTION);
					if (c == JOptionPane.YES_OPTION) {
						if (!borrowCon.queryExistBook(b_id)) {
							bookCon.dropBook(b_id);
							dfttable_book.removeRow(table_book.getSelectedRow());
						} else {
							JOptionPane.showMessageDialog(null, "��ͼ���Ѿ������Ĳ���ɾ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "��û��ѡ��ͼ�飡����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		// ����ͼ��
		else if (e.getSource() == jbt_bookFind[3]) {
			JLabel[] jlab_book = { new JLabel("ͼ��ID��"), new JLabel("ISBN��"), new JLabel("������"), new JLabel("���ߣ�"),
					new JLabel("�����磺"), new JLabel("�۸�"), new JLabel("�������") };
			JLabel[] jlab_hint = { new JLabel("�����޸�"), new JLabel("10λISBN��"), new JLabel("���ĺ��ֻ�����ĸ"),
					new JLabel("���ĺ���"), new JLabel("���ĺ��ֻ�����ĸ"), new JLabel("1-2λС��"), new JLabel("����") };
			JTextField[] jtext_book = new JTextField[7];
			Object[] bookUpdata = { b_id, ISBN, b_name, author, press, price, inventory };
			if (b_id != -1) {
				try {
					new PubJdialog(270, 7, jlab_book, jtext_book, bookUpdata, 0, jlab_hint).setVisible(true);
					if (PubJdialog.success) {
						table_book.setValueAt(jtext_book[1].getText(), row, 1);
						table_book.setValueAt(jtext_book[2].getText(), row, 2);
						table_book.setValueAt(jtext_book[3].getText(), row, 4);
						table_book.setValueAt(jtext_book[4].getText(), row, 5);
						table_book.setValueAt(jtext_book[5].getText(), row, 6);
						table_book.setValueAt(jtext_book[6].getText(), row, 7);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "��û��ѡ��ͼ�飡����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
			}
		}

		// ��ҳ
		else if (e.getSource() == page_jbt[0]) {
			try {
				setTableMolel(new PageQueryCon(bookCon.seleBook()).setCurentPageIndex());
				pageIndex = 1;
				jlab_book.setText("��" + pageIndex + "ҳ/" + "��" + pageCount + "ҳ");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// ��һҳ
		} else if (e.getSource() == page_jbt[1]) {
			try {
				setTableMolel(new PageQueryCon(bookCon.seleBook()).previousPage());
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
				setTableMolel(new PageQueryCon(bookCon.seleBook()).nextPage());
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
				setTableMolel(new PageQueryCon(bookCon.seleBook()).lastPage());
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
								new PageQueryCon(bookCon.seleBook()).jumpPage(Integer.valueOf(jtext_page.getText())));
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
		b_id = TableTool.cancelTableSelected(table_book, b_id);
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

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			for (int i = 0; i < page_jbt.length; i++) {
				if (jcb_bookType.getSelectedItem() == "ȫ��") {
					b_type = "*";
				} else {
					b_type = jcb_bookType.getSelectedItem().toString();
				}
			}
		}

	}
}
