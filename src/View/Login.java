package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Controller.ReaderCon;
import Controller.ReaderTypeCon;
import Model.table.Reader;
import Tool.InputLimit;
import Tool.MD5Tool;

/**
 * 注册界面
 * 
 * @author rsw
 *
 */
public class Login extends JFrame implements ActionListener, ItemListener {
	JLabel[] jlab = { new JLabel("学号："), new JLabel("姓名："), new JLabel("性别："), new JLabel("读者类型："), new JLabel("院系："),
			new JLabel("班级："), new JLabel("手机号码："), new JLabel("电子邮箱："), new JLabel("密保："), new JLabel("密码："),
			new JLabel("确认密码：") };// 声明标签数组
	JButton jbt = new JButton("确定");
	ReaderCon readercon = new ReaderCon();
	JTextField jtext[] = new JTextField[7];
	JComboBox<String> jcb_readerType = new JComboBox<String>();
	JComboBox<String> jcb_gender = new JComboBox<String>();
	JPasswordField jpassword[] = new JPasswordField[2];
	String r_type = "本科", gender = "男";
	String[] hint = { "20开头的11位数字", "中文汉字", "中文汉字", "中文汉字加数字", "手机号格式", "邮箱格式", "任意输入" };
	Reader reader = new Reader();
	ReaderTypeCon readerTypeCon = new ReaderTypeCon();

	public Login() throws SQLException {
		int[] inuput_int = { 11, 5, 10, 10, 11, 20, 15 };// 输入框限制输入位数
		for (int i = 0; i < jtext.length; i++) {
			jtext[i] = new JTextField();
			if (i < 2) {
				jtext[i].setBounds(150, 20 + i * 40, 150, 30);
			}
			if (i >= 2) {
				jtext[i].setBounds(150, 20 + (i + 2) * 40, 150, 30);
			}
			jtext[i].setDocument(new InputLimit(inuput_int[i]));// 限制输入
			jtext[i].addFocusListener(new InputLimit(jtext[i], hint[i]));// 设置文诓提示的外部类监听
			this.add(jtext[i]);
		}
		jcb_gender.setBounds(150, 100, 80, 30);
		jcb_readerType.setBounds(150, 140, 80, 30);
		jcb_gender.addItem("男");
		jcb_gender.addItem("女");
		String[] readerType = readerTypeCon.getReaderType();
		for (int k = 0; k < readerType.length; k++) {
			jcb_readerType.addItem(readerType[k]);
		}
		jcb_readerType.setVisible(true);
		jcb_gender.setVisible(true);
		for (int j = 0; j < jpassword.length; j++) {
			jpassword[j] = new JPasswordField();
			jpassword[j].setBounds(150, 380 + j * 40, 150, 30);
			this.add(jpassword[j]);
			jpassword[j].setDocument(new InputLimit(16));
		}
		for (int k = 0; k < jlab.length; k++) {
			jlab[k].setBounds(20, 20 + k * 40, 100, 40);
			jlab[k].setHorizontalAlignment(4);
			this.add(jlab[k]);
		}
		jbt.setBounds(160, 500, 100, 40);
		this.setLayout(null);
		this.setTitle("图书管理系统");
		this.setSize(400, 600);
		this.setLocationRelativeTo(null);// 设置窗体居中显示
		this.setResizable(false);// 窗口不能改变大小
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 单击窗口关闭按钮,自动隐藏并释放该窗体
		this.setVisible(true);// 使窗口显示
		// 窗口添加组件
		this.add(jbt);
		this.add(jcb_readerType);
		this.add(jcb_gender);

		// 添加监听
		jbt.addActionListener(this);// 设置按钮的监听者
		jcb_readerType.addItemListener(this);
		jcb_gender.addItemListener(this);
		// 窗口关闭事件
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (ManageFace.count != null) {
					dispose();
				} else {
					new Lading().jtext.setText(jtext[0].getText());
				}
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		String[] regex = { InputLimit.STUDENTNUMBER, InputLimit.NAME, InputLimit.CHINESE, InputLimit.CHINESEMATH,
				InputLimit.TELE, InputLimit.EMAIL, InputLimit.PASSWORD };// 验证学号（以20开头的11位数字）、验证姓名（中文）、验证手机号、验证邮箱、验证密码（6-16位数字或者字母）
		String[] input = { jtext[0].getText(), jtext[1].getText(), jtext[2].getText(), jtext[3].getText(),
				jtext[4].getText(), jtext[5].getText(), new String(jpassword[0].getPassword()) };
		String[] hintError = { "学号格式错误", "姓名格式错误", "院系格式出错", "班级格式出错", "手机号格式错误", "邮箱格式错误", "密码格式错误（6-16位数字或字母）" };
		String message = "";
		boolean result[] = InputLimit.regular(regex, input);
		Object obj = e.getSource();
		if (obj == jbt) {
			try {
				for (int i = 0; i < jtext.length; i++) {
					for (int j = 0; j < jpassword.length; j++) {
						if (jtext[i].getText().equals("") || new String(jpassword[j].getPassword()).equals("")) {
							JOptionPane.showMessageDialog(null, "您所注册的信息中不能为空", "信息错误", JOptionPane.ERROR_MESSAGE);
							return;// 跳出循环，防止一直循环弹出对话框
						}
					}
				}
				for (int i = 0; i < result.length; i++) {
					if (!result[i]) {
						message += "\n" + hintError[i];
					}
				}
				if (message.equals("")) {// 进行正则验证
					// 两次密码输入一致，调用注册读者信息的方法
					if (new String(jpassword[0].getPassword()).equals(new String(jpassword[1].getPassword()))) {
						// 检查账号是否被注册
						if (!readercon.isNumber(jtext[0].getText())) {
							readercon.insertReader(jtext[0].getText(), jtext[1].getText(), gender,
									readerTypeCon.queryReaderTypeID(r_type), jtext[2].getText(), jtext[3].getText(),
									jtext[4].getText(), jtext[5].getText(), jtext[6].getText(),
									MD5Tool.string2MD5(new String(jpassword[0].getPassword())));
							// 将密码进行MD5加密后在添加信息
							JOptionPane.showMessageDialog(null, "账号已成功注册", "操作成功", JOptionPane.INFORMATION_MESSAGE);
							this.dispose();
							int c = JOptionPane.showConfirmDialog(null, "是否要直接进行登录操作", "验证操作",
									JOptionPane.YES_NO_OPTION);
							if (c == JOptionPane.YES_OPTION) {
								new Lading().jtext.setText(jtext[0].getText());// 将刚注册的账号set进登录页面
							}
						} else {
							JOptionPane.showMessageDialog(null, "此账号已经被注册\n请更换学号后再进行注册", "操作失败",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "两次输入密码不一致", "信息错误", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, message, "输入信息错误", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 下拉框点击事件
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if (e.getStateChange() == ItemEvent.SELECTED) {
			r_type = jcb_readerType.getSelectedItem().toString();
			gender = jcb_gender.getSelectedItem().toString();
		}
	}
}
