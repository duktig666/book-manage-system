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
 * 图书信息管理面板
 * 
 * @author rsw
 *
 */
public class ManageBook extends JPanel implements ActionListener, ItemListener {
	String ISBN, b_name, author, press, b_type;
	int b_id = -1, inventory, row;
	double price;
	boolean isCompile, refresh;// 是否可以编辑
	JButton[] jbt_bookFind = { new JButton("查询图书"), new JButton("新增图书"), new JButton("删除图书"), new JButton("修改图书") };
	JButton[] page_jbt = { new JButton("首页"), new JButton("上一页"), new JButton("下一页"), new JButton("尾页"),
			new JButton("跳转") };
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
		// 默认在首页，所以“上一页”和“首页”按钮不可用
		page_jbt[0].setEnabled(false);
		page_jbt[1].setEnabled(false);
		jtext_find.setBounds(160, 20, 200, 30);
		jtext_page.setBounds(650, 70, 80, 30);
		jtext_page.setDocument(new InputLimit(4));// 限制输入
		jtext_page.addFocusListener(new InputLimit(jtext_page, "页数"));// 设置文诓提示的外部类监听
		jtext_find.addFocusListener(new InputLimit(jtext_find, "ISBN/书名/作者"));// 设置文诓提示的外部类监听
		jcb_bookType.setBounds(50, 20, 80, 30);
		jcb_bookType.addItem("全部");
		jcb_bookType.addItemListener(this);
		for (int k = 0; k < bookCon.getB_type().size(); k++) {
			jcb_bookType.addItem(bookCon.getB_type().get(k));
		}
		jcb_bookType.setVisible(true);
		jpanup_book.add(jcb_bookType);
		// 分页显示
		jlab_book.setBounds(400, 140, 150, 30);
		pageCount = new PageQueryCon(bookCon.seleBook()).pageCount();
		jlab_book.setText("第" + pageIndex + "页/" + "共" + pageCount + "页");
		jpanup_book.add(jlab_book);
		columnNameBook = new Vector<String>();// 字段名
		String[] columnBook = { "序号", "ISBN", "书名", "图书类型", "作者", "出版社", "价格", "库存量" };
		Vector<Vector<Object>> bookData = null;
		bookData = bookCon.getVector(jtext_find.getText(), jtext_find.getText(), jtext_find.getText());// 调用查询图书的方法
		for (int k = 0; k < columnBook.length; k++) {
			columnNameBook.add(columnBook[k]);
		}
		dfttable_book = new DefaultTableModel(new PageQueryCon(bookCon.seleBook()).setCurentPageIndex(),
				columnNameBook);
		table_book = new JTable(dfttable_book) {
			public boolean isCellEditable(int row, int column) {
				return false;// 表格不允许被编辑f
			}
		};
		ListSelectionModel selectionModel = table_book.getSelectionModel();// 创建表格选择器
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 一次只能选择一个列表索引
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
		// 设置表格的公共属性
		TableTool.setTable(table_book, dfttable_book);
		table_book.getColumn("书名").setPreferredWidth(170);// 设置指定列的宽度
		table_book.getColumn("出版社").setPreferredWidth(120);// 设置指定列的宽度
		table_book.getTableHeader().setReorderingAllowed(false); // 设置整列不可移动
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
	 * 将查询到的数据显示到表格中
	 */
	public void setTableMolel(Vector<Vector<Object>> vector) {
		for (int i = 0; i < vector.size(); i++) {
			dfttable_book.setDataVector(vector, columnNameBook);// 设定模型数据和字段,初始化该表
		}
		table_book.getColumn("书名").setPreferredWidth(170);// 设置指定列的宽度
		table_book.getColumn("出版社").setPreferredWidth(120);// 设置指定列的宽度
		table_book.setRowHeight(20);
	}

	/**
	 * 模糊查询、精确查询图书
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
			dfttable_book.setDataVector(bookData, columnNameBook);// 设定模型数据和字段,初始化该表
		}
		table_book.getColumn("书名").setPreferredWidth(170);// 设置指定列的宽度
		table_book.getColumn("出版社").setPreferredWidth(120);// 设置指定列的宽度
		table_book.setRowHeight(20);
	}

	// 按钮事件
	public void actionPerformed(ActionEvent e) {
		// 查看图书
		if (e.getSource() == jbt_bookFind[0]) {
			if (b_type == "*" || b_type == null) {
				try {
					if (jtext_find.getText().equals("") || jtext_find.getText().equals("ISBN/书名/作者")) {
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
				if (jtext_find.getText().equals("ISBN/书名/作者")) {
					jtext_find.setText("");
				}
				setTableModels();// 调用精确查询、模糊查询的方法
			}
		}
		// 新增图书
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
		// 删除图书
		else if (e.getSource() == jbt_bookFind[2]) {
			System.out.println(b_id);
			try {
				if (b_id != -1) {
					int c = JOptionPane.showConfirmDialog(null, "是否确定删除此图书", "验证操作", JOptionPane.YES_NO_OPTION);
					if (c == JOptionPane.YES_OPTION) {
						if (!borrowCon.queryExistBook(b_id)) {
							bookCon.dropBook(b_id);
							dfttable_book.removeRow(table_book.getSelectedRow());
						} else {
							JOptionPane.showMessageDialog(null, "此图书已经被借阅不能删除!!!", "操作失败", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "您没有选中图书！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		// 更新图书
		else if (e.getSource() == jbt_bookFind[3]) {
			JLabel[] jlab_book = { new JLabel("图书ID："), new JLabel("ISBN："), new JLabel("书名："), new JLabel("作者："),
					new JLabel("出版社："), new JLabel("价格："), new JLabel("库存量：") };
			JLabel[] jlab_hint = { new JLabel("不可修改"), new JLabel("10位ISBN号"), new JLabel("中文汉字或者字母"),
					new JLabel("中文汉字"), new JLabel("中文汉字或者字母"), new JLabel("1-2位小数"), new JLabel("整数") };
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
				JOptionPane.showMessageDialog(null, "您没有选中图书！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
			}
		}

		// 首页
		else if (e.getSource() == page_jbt[0]) {
			try {
				setTableMolel(new PageQueryCon(bookCon.seleBook()).setCurentPageIndex());
				pageIndex = 1;
				jlab_book.setText("第" + pageIndex + "页/" + "共" + pageCount + "页");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// 上一页
		} else if (e.getSource() == page_jbt[1]) {
			try {
				setTableMolel(new PageQueryCon(bookCon.seleBook()).previousPage());
				if (pageIndex > 1) {
					pageIndex--;
					jlab_book.setText("第" + pageIndex + "页/" + "共" + pageCount + "页");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// 下一页
		} else if (e.getSource() == page_jbt[2]) {
			try {
				setTableMolel(new PageQueryCon(bookCon.seleBook()).nextPage());
				if (pageIndex < pageCount) {
					pageIndex++;
					jlab_book.setText("第" + pageIndex + "页/" + "共" + pageCount + "页");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// 尾页
		} else if (e.getSource() == page_jbt[3]) {
			try {
				setTableMolel(new PageQueryCon(bookCon.seleBook()).lastPage());
				pageIndex = pageCount;
				jlab_book.setText("第" + pageIndex + "页/" + "共" + pageCount + "页");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// 跳转页数
		else if (e.getSource() == page_jbt[4]) {
			try {
				String isInt = jtext_page.getText();
				isInt = isInt.replaceAll("[0-9]", "");// 将所有的数字型字符替换为空
				// 长度为0等于说数字
				if (isInt.length() == 0) {
					if (Integer.valueOf(jtext_page.getText()) > 0
							&& Integer.valueOf(jtext_page.getText()) <= pageCount) {
						setTableMolel(
								new PageQueryCon(bookCon.seleBook()).jumpPage(Integer.valueOf(jtext_page.getText())));
						pageIndex = Integer.valueOf(jtext_page.getText());
						jlab_book.setText("第" + pageIndex + "页/" + "共" + pageCount + "页");
					} else {
						JOptionPane.showMessageDialog(null, "请输入正确的页数", "操作失败", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "请输入整数", "操作失败", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		/*
		 * 每次点击按钮将图书id设为-1，即默认没有选中图书 并取消表格的选中状态 必须写在else if语句后，并且所有动作事件都是用else if语句来写的
		 */
		b_id = TableTool.cancelTableSelected(table_book, b_id);
		// 尾页时，“下一页”和“尾页”按钮不可用
		if (pageIndex == pageCount) {
			page_jbt[2].setEnabled(false);
			page_jbt[3].setEnabled(false);
		} else {
			page_jbt[2].setEnabled(true);
			page_jbt[3].setEnabled(true);
		}
		// 首页时，“上一页”和“首页”不可用
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
				if (jcb_bookType.getSelectedItem() == "全部") {
					b_type = "*";
				} else {
					b_type = jcb_bookType.getSelectedItem().toString();
				}
			}
		}

	}
}
