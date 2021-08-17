package Tool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Controller.AdmiCon;
import Controller.BookCon;
import Controller.BookTypeCon;
import Controller.ReaderCon;
import Controller.ReaderTypeCon;
import Tool.InputLimit;
import Tool.MD5Tool;

/**
 * 公共的对话框类
 */
public class PubJdialog extends JDialog {
	ReaderCon readercon = new ReaderCon();
	BookCon bookCon = new BookCon();
	BookTypeCon bookTypeCon = new BookTypeCon();
	ReaderTypeCon readerTypeCon = new ReaderTypeCon();
	AdmiCon admiCon = new AdmiCon();
	String number, b_type;// r_number 读者账号 b_type 图书类型
	int booktype = 1, a;// 图书类型的id
	JTextField[] jtext_readerType;

	/**
	 * 用户端修改读者密码的输入对话框
	 */
	public PubJdialog(String number, boolean isUser) {
		this.number = number;
		JLabel[] jlab = { new JLabel("旧密码："), new JLabel("密保："), new JLabel("新密码："), new JLabel("确认密码：") };
		JPasswordField[] jpassword = new JPasswordField[4];
		JPanel jpan = new JPanel(new GridLayout(4, 2));
		JButton jbt = new JButton("确人更改密码");
		setTitle("修改读者密码");
		setModal(true);// 是否阻止在显示的时候将内容输入其他窗口,只能操作此对话框
		setSize(350, 220);// 对话框的大小
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// 关闭后销毁对话框
		setLocationRelativeTo(null);
		for (int i = 0; i < jpassword.length; i++) {
			jpassword[i] = new JPasswordField();
			jlab[i].setHorizontalAlignment(0);
			jpan.add(jlab[i]);
			jpan.add(jpassword[i]);
			jpassword[i].setDocument(new InputLimit(16));// 限制输入
		}
		jpassword[1].setEchoChar((char) 0);
		jpassword[1].enableInputMethods(true);// 设置密码框可以使用输入法，输入汉字
		jbt.setPreferredSize(new Dimension(100, 30));
		jbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean resurt = InputLimit.regular("^[0-9a-zA-Z]{6,16}$", new String(jpassword[2].getPassword()));
				String alterPass = MD5Tool.string2MD5(new String(jpassword[2].getPassword()));
				String password = MD5Tool.string2MD5(new String(jpassword[0].getPassword()));
				String keepPass = new String(jpassword[1].getPassword());
				if (e.getSource() == jbt) {
					try {
						if (resurt && new String(jpassword[2].getPassword())
								.equals(new String(jpassword[3].getPassword()))) {
							if (isUser == true) {
								readercon.updateReaderPass(alterPass, number, password, keepPass);
							} else {
								admiCon.updateAdmiPass(alterPass, number, password, keepPass);
							}
							dispose();
							JOptionPane.showMessageDialog(null, "您已成功修改密码", "提示", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "您所输入的信息有误或者格式不正确", "信息错误", JOptionPane.ERROR_MESSAGE);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		add(jbt, BorderLayout.SOUTH);
		add(jpan);
	}

	/**
	 * 管理员端新增图书的输入对话框 构造方法的重载
	 * 
	 * 新增图书的对话框
	 */
	public PubJdialog() throws SQLException {
		JLabel[] jlab = { new JLabel("ISBN："), new JLabel("书名："), new JLabel("图书类型："), new JLabel("作者："),
				new JLabel("出版社："), new JLabel("价格："), new JLabel("库存量：") };
		JTextField[] jtext_insterBook = new JTextField[6];
		JComboBox<String> jcb_bookType = new JComboBox<String>();
		JButton jbt_interBook = new JButton("确人");
		String[] hint = { "10位数字", "中文或英语字母或数字", "中文和英语字母", "中文和英语字母", "1-2位小数", "整数" };
		JPanel jpan = new JPanel();

		setTitle("新增图书");
		setModal(true);// 是否阻止在显示的时候将内容输入其他窗口,只能操作此对话框
		setSize(400, 450);// 对话框的大小
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// 关闭后销毁对话框
		setLocationRelativeTo(null);
		jpan.setLayout(null);
		int[] inuput_int = { 10, 15, 10, 10, 10, 3 };// 输入框限制输入位数
		for (int i = 0; i < jlab.length; i++) {
			jlab[i].setBounds(20, 20 + i * 40, 100, 30);
			jpan.add(jlab[i]);
		}
		for (int i = 0; i < jtext_insterBook.length; i++) {
			jtext_insterBook[i] = new JTextField();
			if (i < 2)
				jtext_insterBook[i].setBounds(120, 20 + i * 40, 150, 30);
			if (i >= 2)
				jtext_insterBook[i].setBounds(120, 140 + (i - 2) * 40, 150, 30);
			jtext_insterBook[i].setDocument(new InputLimit(inuput_int[i]));// 限制输入
			jpan.add(jtext_insterBook[i]);
			jtext_insterBook[i].addFocusListener(new InputLimit(jtext_insterBook[i], hint[i]));// 设置文诓提示的外部类监听
		}
		jcb_bookType.setBounds(120, 100, 100, 30);
		for (int k = 0; k < bookCon.getB_type().size(); k++) {
			jcb_bookType.addItem(bookCon.getB_type().get(k));
		}
		jcb_bookType.setVisible(true);
		jbt_interBook.setBounds(150, 330, 100, 40);
		jpan.add(jcb_bookType);
		jpan.add(jbt_interBook);
		add(jpan);
		// 获取下拉框的数据
		jcb_bookType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				b_type = jcb_bookType.getSelectedItem().toString();
				try {
					if (e.getStateChange() == ItemEvent.SELECTED) {// 防止下拉框选中两次
						booktype = bookTypeCon.queryBTid(b_type);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		// 新增图书
		jbt_interBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] regex = { InputLimit.ISBN, InputLimit.CHINESEENGLISHMATH, InputLimit.CHINESEENGLISH,
						InputLimit.CHINESEENGLISH, InputLimit.DECIMAL, InputLimit.INT };
				String[] input = { jtext_insterBook[0].getText(), jtext_insterBook[1].getText(),
						jtext_insterBook[2].getText(), jtext_insterBook[3].getText(), jtext_insterBook[4].getText(),
						jtext_insterBook[5].getText() };
				String[] hintError = { "ISBN格式错误", "书名格式错误", "作者格式出错", "出版社格式出错", "价格格式错误", "库存量格式错误" };
				boolean result[] = InputLimit.regular(regex, input);
				String message = "";
				int c = JOptionPane.showConfirmDialog(null, "是否确定新增此图书", "验证操作", JOptionPane.YES_NO_OPTION);
				if (c == JOptionPane.YES_OPTION) {
					try {
						for (int i = 0; i < result.length; i++) {
							if (!result[i]) {
								message += "\n" + hintError[i];
							}
						}
						if (message.equals("")) {// 进行正则验证
							// ISBN不存在才可以新增
							if (!bookCon.isISBN(jtext_insterBook[0].getText())) {
								bookCon.insterBook(jtext_insterBook[0].getText(), jtext_insterBook[1].getText(),
										booktype, jtext_insterBook[2].getText(), jtext_insterBook[3].getText(),
										Double.valueOf(jtext_insterBook[4].getText()),
										Integer.parseInt(jtext_insterBook[5].getText()));
								JOptionPane.showMessageDialog(null, "新增图书成功", "操作成功", JOptionPane.ERROR_MESSAGE);
								dispose();
							} else {
								JOptionPane.showMessageDialog(null, "ISBN已经存在\n请重新更换ISBN进行新增图书", "操作失败",
										JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, message, "输入信息错误", JOptionPane.ERROR_MESSAGE);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		});
	}

	/**
	 * 管理员端新增读者类型的对话框方法
	 */
	public PubJdialog(int a) throws SQLException {
		this.a = a;
		JLabel[] jlab_readerType = { new JLabel("读者类型："), new JLabel("最大借阅数量："), new JLabel("最大借阅天数：") };
		jtext_readerType = new JTextField[3];
		JPanel jpan_admi = new JPanel(new GridLayout(3, 2));
		JButton jbt_readerType = new JButton("确人新增读者类型");
		String[] hint = { "中文汉字", "整数", "整数" };
		setTitle("新增读者类型");
		setModal(true);// 是否阻止在显示的时候将内容输入其他窗口,只能操作此对话框
		setSize(300, 180);// 对话框的大小
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// 关闭后销毁对话框
		setLocationRelativeTo(null);
		int[] inuput_int = { 10, 3, 3 };// 输入框限制输入位数
		for (int i = 0; i < jtext_readerType.length; i++) {
			jlab_readerType[i].setHorizontalAlignment(0);
			jtext_readerType[i] = new JTextField();
			jpan_admi.add(jlab_readerType[i]);
			jpan_admi.add(jtext_readerType[i]);
			jtext_readerType[i].setDocument(new InputLimit(inuput_int[i]));// 限制输入
			jtext_readerType[i].addFocusListener(new InputLimit(jtext_readerType[i], hint[i]));// 设置文诓提示的外部类监听
		}
		add(jbt_readerType, BorderLayout.SOUTH);
		add(jpan_admi);
		jbt_readerType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!InputLimit.inputIsNull(jtext_readerType)) {
					int c = JOptionPane.showConfirmDialog(null, "是否确定新增此读者类型", "验证操作", JOptionPane.YES_NO_OPTION);
					if (c == JOptionPane.YES_OPTION) {
						try {
							String[] regex = { InputLimit.CHINESE, InputLimit.INT, InputLimit.INT };
							String[] input = { jtext_readerType[0].getText(), jtext_readerType[1].getText(),
									jtext_readerType[2].getText() };
							String[] hintError = { "读者类型格式错误", "最大借阅数量格式错误", "最大借阅天数格式出错" };
							String message = "";
							boolean result[] = InputLimit.regular(regex, input);
							for (int i = 0; i < result.length; i++) {
								if (!result[i]) {
									message += "\n" + hintError[i];
								}
							}
							if (message.equals("")) {
								readerTypeCon.insertReaderType(jtext_readerType[0].getText().toString(),
										Integer.parseInt(jtext_readerType[1].getText()),
										Integer.parseInt(jtext_readerType[2].getText()));
								success=true;
								JOptionPane.showMessageDialog(null, "信息修改成功", "操作成功", JOptionPane.INFORMATION_MESSAGE);
								dispose();
							} else {
								JOptionPane.showMessageDialog(null, message, "输入信息错误", JOptionPane.ERROR_MESSAGE);
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
	}

	/**
	 * 忘记密码的账号、密保验证
	 */
	public PubJdialog(JLabel[] jlab, JTextField[] jtext) throws SQLException {
		setTitle("账号、密保验证");
		setModal(true);// 是否阻止在显示的时候将内容输入其他窗口,只能操作此对话框
		setSize(300, 180);// 对话框的大小
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// 关闭后销毁对话框
		setLocationRelativeTo(null);
		JPanel jpan = new JPanel(new GridLayout(3, 2));
		JButton jbt = new JButton("确定");
		for (int i = 0; i < jtext.length; i++) {
			jlab[i].setHorizontalAlignment(0);
			jtext[i] = new JTextField();
			jpan.add(jlab[i]);
			jpan.add(jtext[i]);
		}
		add(jbt, BorderLayout.SOUTH);
		add(jpan);
		jbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String count = jtext[0].getText();
				String keeppass = jtext[1].getText();
				if (count == null || keeppass == null) {
					JOptionPane.showMessageDialog(null, "您所输入的账号为空", "格式错误", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						if (readercon.queryKeeppass(keeppass, count)) {
							// 重新设置密码,调用其对话框
							new PubJdialog(keeppass, count).setVisible(true);
							dispose();
						} else
							JOptionPane.showMessageDialog(null, "您所输入的账号和密保验证不匹配", "验证失败", JOptionPane.ERROR_MESSAGE);
					} catch (HeadlessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * 忘记密码的功能
	 */
	public PubJdialog(String forgetPass, String count) throws SQLException {
		JLabel[] jlab = { new JLabel("新密码："), new JLabel("确认密码：") };
		JPasswordField[] jpassword = new JPasswordField[2];
		JPanel jpan = new JPanel(new GridLayout(2, 2));
		JButton jbt = new JButton("确人");
		setTitle("修改读者密码,请输入6-16位的数字或者字母");
		setModal(true);// 是否阻止在显示的时候将内容输入其他窗口,只能操作此对话框
		setSize(350, 150);// 对话框的大小
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// 关闭后销毁对话框
		setLocationRelativeTo(null);
		for (int i = 0; i < jpassword.length; i++) {
			jpassword[i] = new JPasswordField();
			jlab[i].setHorizontalAlignment(0);
			jpan.add(jlab[i]);
			jpan.add(jpassword[i]);
		}
		jbt.setPreferredSize(new Dimension(100, 30));
		jbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean resurt = InputLimit.regular("^[0-9a-zA-Z]{6,16}$", new String(jpassword[0].getPassword()));
				String newPass = MD5Tool.string2MD5(new String(jpassword[0].getPassword()));
				try {
					// 重置密码
					if (resurt && newPass != null) {
						if (new String(jpassword[0].getPassword()).equals(new String(jpassword[1].getPassword()))) {
							readercon.resetPass(forgetPass, count, newPass);
							dispose();
							JOptionPane.showMessageDialog(null, "密码重置成功", "操作成功", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "您两次输入的密码不一致", "操作失败", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "您所输入密码为空或者格式不正确", "操作失败", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		add(jpan);
		add(jbt, BorderLayout.SOUTH);
	}

	/**
	 * 新增管理员的对话框
	 */
	public PubJdialog(boolean is) throws SQLException {
		JLabel[] jlab_admi = { new JLabel("账号："), new JLabel("姓名："), new JLabel("身份证号："), new JLabel("手机号："),
				new JLabel("邮箱："), new JLabel("密保："), new JLabel("密码："), new JLabel("验证密码：") };
		JTextField[] jtext_admi = new JTextField[6];
		JPasswordField[] jpass_admi = new JPasswordField[2];
		JPanel jpan_admi = new JPanel();
		JButton jbt_admi = new JButton("确人");
		String[] hint = { "账号（6-16位数字字母）", "中文汉字", "身份证号", "手机号格式", "邮箱格式", "任意输入" };
		setTitle("新增管理员");
		setModal(true);// 是否阻止在显示的时候将内容输入其他窗口,只能操作此对话框
		setSize(300, 330);// 对话框的大小
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// 关闭后销毁对话框
		setLocationRelativeTo(null);
		jpan_admi.setLayout(null);
		int[] inuput_int = { 16, 5, 18, 11, 20, 15 };// 输入框限制输入位数
		for (int i = 0; i < jlab_admi.length; i++) {
			jlab_admi[i].setHorizontalAlignment(0);
			jlab_admi[i].setBounds(0, i * 30, 100, 30);
			jpan_admi.add(jlab_admi[i]);
		}
		for (int i = 0; i < jtext_admi.length; i++) {
			jtext_admi[i] = new JTextField();
			jtext_admi[i].setBounds(130, i * 30, 150, 30);
			jpan_admi.add(jtext_admi[i]);
			jtext_admi[i].setDocument(new InputLimit(inuput_int[i]));// 限制输入
			jtext_admi[i].addFocusListener(new InputLimit(jtext_admi[i], hint[i]));// 设置文诓提示的外部类监听
		}
		for (int i = 0; i < jpass_admi.length; i++) {
			jpass_admi[i] = new JPasswordField();
			jpass_admi[i].setBounds(130, 180 + i * 30, 150, 30);
			jpan_admi.add(jpass_admi[i]);
			jpass_admi[i].setDocument(new InputLimit(16));// 限制输入
		}
		add(jbt_admi, BorderLayout.SOUTH);
		add(jpan_admi);
		jbt_admi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] regex = { InputLimit.COUNT, InputLimit.NAME, InputLimit.IDCARD, InputLimit.TELE,
						InputLimit.EMAIL, InputLimit.PASSWORD };// 验证身份证号、手机号、邮箱、密码
				String[] input = { jtext_admi[0].getText(), jtext_admi[1].getText(), jtext_admi[2].getText(),
						jtext_admi[3].getText(), jtext_admi[4].getText(), new String(jpass_admi[0].getPassword()) };
				String[] hintError = { "账号格式错误", "姓名格式错误", "身份证号格式错误", "手机号格式错误", "邮箱格式出错", "密码格式错误" };
				String message = "";
				boolean result[] = InputLimit.regular(regex, input);
				for (int i = 0; i < result.length; i++) {
					if (!result[i]) {
						message += "\n" + hintError[i];
					}
				}
				if (message.equals("")) {// 进行正则验证
					if (new String(jpass_admi[0].getPassword()).equals(new String(jpass_admi[1].getPassword()))) {
						// 两次密码输入一致，调用注册读者信息的方法
						try {
							admiCon.insterAdmi(jtext_admi[0].getText(), jtext_admi[1].getText(),
									jtext_admi[2].getText(), jtext_admi[3].getText(), jtext_admi[4].getText(),
									jtext_admi[5].getText(),
									MD5Tool.string2MD5(new String(jpass_admi[0].getPassword())));
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// 将密码进行MD5加密后在添加信息
						JOptionPane.showMessageDialog(null, "您已成功新增管理员", "操作成功", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "两次输入密码不一致", "信息错误", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, message, "输入信息错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public static boolean success = false;

	// 超级管理员验证
	public PubJdialog(JTextField jtext_super, JButton[] jbt_super) throws SQLException {
		JLabel[] jlab = { new JLabel("账号："), new JLabel("密码：") };
		JPasswordField[] jpassword = new JPasswordField[2];
		JButton jbt = new JButton("确人");
		JPanel jpan = new JPanel(new GridLayout(2, 2));
		setTitle("超级管理员验证");
		setModal(true);// 是否阻止在显示的时候将内容输入其他窗口,只能操作此对话框
		setSize(350, 150);// 对话框的大小
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// 关闭后销毁对话框
		setLocationRelativeTo(null);
		for (int i = 0; i < jpassword.length; i++) {
			jpassword[i] = new JPasswordField();
			jlab[i].setHorizontalAlignment(0);
			jpan.add(jlab[i]);
			jpan.add(jpassword[i]);
		}
		jpassword[0].setEchoChar((char) 0);
		jbt.setPreferredSize(new Dimension(100, 30));
		jbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					boolean isSuper = admiCon.proveSuper(new String(jpassword[0].getPassword()),
							MD5Tool.string2MD5(new String(jpassword[1].getPassword())));
					if (isSuper) {
						jtext_super.setEnabled(true);
						for (int i = 0; i < jbt_super.length; i++) {
							jbt_super[i].setEnabled(true);
						}
						JOptionPane.showMessageDialog(null, "进入超级管理员模式！！！", "操作成功", JOptionPane.INFORMATION_MESSAGE);
						success = true;
					} else {
						JOptionPane.showMessageDialog(null, "超级管理员验证失败！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}
		});
		add(jpan);
		add(jbt, BorderLayout.SOUTH);
	}

	/**
	 * 修改图书信息 修改读者信息 修改读者类型信息 公共对话框
	 */
	int isAlter_int = 0;

	public PubJdialog(int dialogHeight, int dialogRow, JLabel[] jlab_update, JTextField[] jtext_update,
			Object[] alterData, int tablePanelIndex, JLabel[] jlab_hint) throws SQLException {
		JPanel jpan_update = new JPanel(new GridLayout(dialogRow, 3));
		JButton jbt_update = new JButton("确人");
		boolean[] isAlter_boolean = new boolean[jtext_update.length];
		setTitle("修改功能");
		setModal(true);// 是否阻止在显示的时候将内容输入其他窗口,只能操作此对话框
		setSize(500, dialogHeight);// 对话框的大小
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// 关闭后销毁对话框
		setLocationRelativeTo(null);
		for (int i = 0; i < jtext_update.length; i++) {
			jtext_update[i] = new JTextField();
			if (tablePanelIndex == 0) {
				int[] inuput_int = { 11, 10, 10, 10, 11, 10, 3 };// 输入框限制输入位数
				jtext_update[i].setDocument(new InputLimit(inuput_int[i]));// 限制输入
			} else if (tablePanelIndex == 2) {
				int[] inuput_int = { 11, 10, 10, 11, 20 };// 输入框限制输入位数
				jtext_update[i].setDocument(new InputLimit(inuput_int[i]));// 限制输入
			} else if (tablePanelIndex == 3) {
				int[] inuput_int = { 11, 10, 3, 3 };// 输入框限制输入位数
				jtext_update[i].setDocument(new InputLimit(inuput_int[i]));// 限制输入
			} else if (tablePanelIndex == 10) {
				int[] inuput_int = { 11, 11, 20 };// 输入框限制输入位数
				jtext_update[i].setDocument(new InputLimit(inuput_int[i]));// 限制输入
			} else if (tablePanelIndex == 11) {
				int[] inuput_int = { 11, 10, 10, 11, 20 };// 输入框限制输入位数
				jtext_update[i].setDocument(new InputLimit(inuput_int[i]));// 限制输入
			}
			jlab_update[i].setHorizontalAlignment(0);
			jtext_update[i].setText(alterData[i].toString());
			jlab_hint[i].setForeground(Color.RED);
			jpan_update.add(jlab_update[i]);
			jpan_update.add(jtext_update[i]);
			jpan_update.add(jlab_hint[i]);
		}
		jtext_update[0].setEditable(false);
		add(jbt_update, BorderLayout.SOUTH);
		add(jpan_update);
		jbt_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// InputLimit.inputIsNull()判断文本框输入信息是否为空
					if (!InputLimit.inputIsNull(jtext_update)) {
						// 遍历每个文本框，看是否进行了信息修改
						for (int i = 0; i < jtext_update.length; i++) {
							isAlter_boolean[i] = jtext_update[i].getText().equals(alterData[i].toString());// 是否对数据进行了修改
							if (!isAlter_boolean[i]) {
								isAlter_int++;// 如果有一个没修改+1
							}
						}
						// 如果有修改执行
						if (isAlter_int != 0) {
							/*
							 * 判断第几个选项卡窗格的修改功能 0为修改图书信息
							 */
							if (tablePanelIndex == 0) {
								String[] regex = { InputLimit.ISBN, InputLimit.CHINESEENGLISHMATH,
										InputLimit.CHINESEENGLISH, InputLimit.CHINESEENGLISH, InputLimit.DECIMAL,
										InputLimit.INT };
								String[] input = { jtext_update[1].getText(), jtext_update[2].getText(),
										jtext_update[3].getText(), jtext_update[4].getText(), jtext_update[5].getText(),
										jtext_update[6].getText() };
								String[] hintError = { "ISBN格式错误", "书名格式错误", "作者格式出错", "出版社格式出错", "价格格式错误", "库存量格式错误" };
								String message = "";
								boolean result[] = InputLimit.regular(regex, input);
								for (int i = 0; i < result.length; i++) {
									if (!result[i]) {
										message += "\n" + hintError[i];
									}
								}
								if (message.equals("")) {
									bookCon.updateBook(jtext_update[1].getText().toString(),
											jtext_update[2].getText().toString(), jtext_update[3].getText().toString(),
											jtext_update[4].getText().toString(),
											Double.valueOf(jtext_update[5].getText().toString()),
											Integer.valueOf(jtext_update[6].getText().toString()),
											Integer.valueOf(jtext_update[0].getText().toString()));
									JOptionPane.showMessageDialog(null, "信息修改成功", "操作成功",
											JOptionPane.INFORMATION_MESSAGE);
									dispose();
									success = true;
								} else {
									JOptionPane.showMessageDialog(null, message, "输入信息错误", JOptionPane.ERROR_MESSAGE);
								}
							}
							/*
							 * 2为修改读者信息
							 */
							else if (tablePanelIndex == 2) {
								String[] regex = { InputLimit.CHINESE, InputLimit.CHINESEMATH, InputLimit.TELE,
										InputLimit.EMAIL };
								String[] input = { jtext_update[1].getText(), jtext_update[2].getText(),
										jtext_update[3].getText(), jtext_update[4].getText() };
								String[] hintError = { "院系格式错误", "班级格式错误", "手机号格式出错", "邮箱格式出错" };
								String message = "";
								boolean result[] = InputLimit.regular(regex, input);
								for (int i = 0; i < result.length; i++) {
									if (!result[i]) {
										message += "\n" + hintError[i];
									}
								}
								if (message.equals("")) {
									readercon.updateReader(jtext_update[1].getText(), jtext_update[2].getText(),
											jtext_update[3].getText(), jtext_update[4].getText(),
											jtext_update[0].getText());
									JOptionPane.showMessageDialog(null, "信息修改成功", "操作成功",
											JOptionPane.INFORMATION_MESSAGE);
									dispose();
									success = true;
								} else {
									JOptionPane.showMessageDialog(null, message, "输入信息错误", JOptionPane.ERROR_MESSAGE);
								}
							}
							/*
							 * 3为修改读者类型信息
							 */

							else if (tablePanelIndex == 3) {
								String[] regex = { InputLimit.CHINESE, InputLimit.INT, InputLimit.INT, };
								String[] input = { jtext_update[1].getText(), jtext_update[2].getText(),
										jtext_update[3].getText() };
								String[] hintError = { "读者类型格式错误", "最大借阅数量格式错误", "最大借阅天数格式出错" };
								String message = "";
								boolean result[] = InputLimit.regular(regex, input);
								for (int i = 0; i < result.length; i++) {
									if (!result[i]) {
										message += "\n" + hintError[i];
									}
								}
								if (message.equals("")) {
									readerTypeCon.updateRederType(jtext_update[1].getText(),
											Integer.valueOf(jtext_update[2].getText()),
											Integer.valueOf(jtext_update[3].getText()),
											Integer.valueOf(jtext_update[0].getText()));
									JOptionPane.showMessageDialog(null, "信息修改成功", "操作成功",
											JOptionPane.INFORMATION_MESSAGE);
									dispose();
									success = true;
								} else {
									JOptionPane.showMessageDialog(null, message, "输入信息错误", JOptionPane.ERROR_MESSAGE);
								}
							}
							/*
							 * 10为超级管理员修改管理员信息
							 */
							else if (tablePanelIndex == 10) {
								String[] regex = { InputLimit.TELE, InputLimit.EMAIL };
								String[] input = { jtext_update[1].getText(), jtext_update[2].getText() };
								String[] hintError = { "手机号格式错误", "邮箱格式错误" };
								String message = "";
								boolean result[] = InputLimit.regular(regex, input);

								for (int i = 0; i < result.length; i++) {
									if (!result[i]) {
										message += "\n" + hintError[i];
									}
								}
								if (message.equals("")) {
									admiCon.updateAdmi(jtext_update[1].getText(), jtext_update[2].getText(),
											jtext_update[0].getText());
									JOptionPane.showMessageDialog(null, "信息修改成功", "操作成功",
											JOptionPane.INFORMATION_MESSAGE);
									dispose();
									success = true;
								} else {
									JOptionPane.showMessageDialog(null, message, "输入信息错误", JOptionPane.ERROR_MESSAGE);
								}
							}
							/*
							 * 11为修改用户信息
							 */
							else if (tablePanelIndex == 11) {
								String[] regex = { InputLimit.CHINESE, InputLimit.CHINESEMATH, InputLimit.TELE,
										InputLimit.EMAIL };
								String[] input = { jtext_update[1].getText(), jtext_update[2].getText(),
										jtext_update[3].getText(), jtext_update[4].getText() };
								String[] hintError = { "院系格式错误", "班级格式错误", "手机号格式出错", "邮箱格式出错" };
								String message = "";
								boolean result[] = InputLimit.regular(regex, input);
								for (int i = 0; i < result.length; i++) {
									if (!result[i]) {
										message += "\n" + hintError[i];
									}
								}
								if (message.equals("")) {
									readercon.updateReader(jtext_update[1].getText(), jtext_update[2].getText(),
											jtext_update[3].getText(), jtext_update[4].getText(),
											jtext_update[0].getText());
									JOptionPane.showMessageDialog(null, "信息修改成功", "操作成功",
											JOptionPane.INFORMATION_MESSAGE);
									dispose();
									success = true;
								} else {
									JOptionPane.showMessageDialog(null, message, "输入信息错误", JOptionPane.ERROR_MESSAGE);
								}
							}
						} else {
							JOptionPane.showMessageDialog(null, "您未对信息进行修改", "操作失败", JOptionPane.ERROR_MESSAGE);
						}
						isAlter_int = 0;
					}
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
}