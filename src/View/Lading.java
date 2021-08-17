package View;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Controller.AdmiCon;
import Controller.ReaderCon;
import Model.table.Administrator;
import Tool.MD5Tool;
import Tool.PubJdialog;
import Tool.InputLimit;

/**
 * 登录界面
 * @author rsw
 */
public class Lading extends JFrame implements ActionListener {
	boolean isuser = true;
	String count;
	final int WIDTH = 700, HEIGHT = 530;
	boolean identity;// 确定是否为用户 identity身份
	int type;
	JPanel panel = new JPanel();
	JTextField jtext = new JTextField();
	JPasswordField jpassword = new JPasswordField();
	JLabel backImage;
	JLabel[] jlab = { new JLabel("账号："), new JLabel("密码：") };// 声明标签数组
	Font fnt = new Font("Microsoft YaHei", Font.BOLD, 20);
	ImageIcon img_lading = new ImageIcon("src/Images/lading.jpg");
	ImageIcon img_login = new ImageIcon("src/Images/login.jpg");
	ImageIcon img_forgetPass = new ImageIcon("src/Images/forgetPass.jpg");
	JButton jbt_lading, jbt_login, jbt_forgetPass;
	JRadioButton jrb1 = new JRadioButton("用户");
	JRadioButton jrb2 = new JRadioButton("管理员");
	AdmiCon admiCon = new AdmiCon();
	Administrator admi = new Administrator();
	ReaderCon readercon = new ReaderCon();

	/*
	 * 登录界面
	 */
	public Lading() {
		ButtonGroup grp = new ButtonGroup();// 实例化单选按钮组
		backImage = new JLabel(new ImageIcon("src/Images/LadingInterface.jpg"));
		panel.setLayout(null);// 取消默认布局管理器
		jbt_lading = new JButton(img_lading);// 登录按钮
		jbt_login = new JButton(img_login);// 注册按钮
		jbt_forgetPass = new JButton(img_forgetPass);
		this.setLayout(null);
		this.setTitle("图书管理系统");
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);// 设置窗体居中显示
		this.setResizable(false);// 窗口不能改变大小
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// 关闭窗口不执行任何操作
		this.setVisible(true);// 使窗口显示
		jtext.setBounds(250, 200, 250, 40);
		jpassword.setBounds(250, 260, 250, 40);
		jtext.setFont(fnt);
		jpassword.setFont(fnt);
		jtext.setDocument(new InputLimit(16));
		jpassword.setDocument(new InputLimit(16));// 匿名调用工具类——“限制输入”工具，限制输入16位

		panel.setBounds(0, 0, WIDTH, HEIGHT);
		jbt_lading.setBounds(280, 330, 200, 40);
		jbt_login.setBounds(500, 200, 100, 40);
		jbt_forgetPass.setBounds(500, 260, 100, 40);
		jbt_login.setFocusable(false);// 去焦点
		jlab[0].setBounds(180, 200, 80, 40);
		jlab[1].setBounds(180, 260, 80, 40);
		jlab[0].setFont(fnt);
		jlab[1].setFont(fnt);
		backImage.setSize(WIDTH, HEIGHT);
		jlab[0].setHorizontalAlignment(0);// 设置水平对齐方式 0居中
		jlab[1].setHorizontalAlignment(0);
		jrb1.setBounds(270, 150, 80, 40);
		jrb2.setBounds(370, 150, 80, 40);
		jrb1.setSelected(true);//设置用户单选按钮默认被选中
		grp.add(jrb1);// 将单选按钮添加到单选按钮组中（保证单选）
		grp.add(jrb2);
		panel.add(jrb1);
		panel.add(jrb2);
		panel.add(jtext);
		panel.add(jpassword);
		panel.add(jbt_lading);
		panel.add(jbt_login);
		panel.add(jbt_forgetPass);
		panel.add(jlab[0]);
		panel.add(jlab[1]);
		panel.add(backImage);
		this.add(panel);

		jrb1.addActionListener(this);// 设置单选框的监听者
		jrb2.addActionListener(this);
		jbt_lading.addActionListener(this);// 设置按钮的监听者
		jbt_login.addActionListener(this);
		jbt_forgetPass.addActionListener(this);
		jtext.addActionListener(this);// 设置账号监听者
		jpassword.addActionListener(this);// 设置密码监听者
		jtext.addFocusListener(new InputLimit(jtext, "11位的学号"));// 设置文诓提示的外部类监听
		// 点击结束程序时，弹出对话框
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int c = JOptionPane.showConfirmDialog(null, "是否要退出系统程序", "操作验证", JOptionPane.YES_NO_OPTION);
				if (c == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		String number = jtext.getText();
		String password = MD5Tool.string2MD5(new String(jpassword.getPassword()));
		boolean resurt = InputLimit.regular("^[0-9a-zA-Z]{6,16}$", number);// 调用工具类中的验证正则的方法，传递正则和验证正则的文本
		/*
		 * 管理员不可注册
		 */
		if (obj == jrb1) {
			isuser = true;
			jbt_login.setVisible(true);
			jbt_forgetPass.setVisible(true);
			panel.add(jbt_login);
			panel.add(jbt_forgetPass);
			jbt_login.repaint();
			jbt_forgetPass.repaint();
			jtext.addFocusListener(new InputLimit(jtext, "11位学号"));// 设置文诓提示的外部类监听
		} else if (obj == jrb2) {
			isuser = false;
			jbt_login.setVisible(false);
			jbt_forgetPass.setVisible(false);
			panel.remove(jbt_login);
			panel.remove(jbt_forgetPass);
			jtext.addFocusListener(new InputLimit(jtext, "6-16位管理员账号"));// 设置文诓提示的外部类监听
		}
		/*
		 * 判断登录的账号密码信息 加入正则验证
		 */
		if (obj == jbt_lading) {// 登录按钮
			try {
				if (number.equals("") || password.equals("")) { // 判断输入是否为空
					JOptionPane.showMessageDialog(null, "输入数据为空", "输入错误", JOptionPane.ERROR_MESSAGE);
				} else if (resurt) {
					if (readercon.queryRerader(number, password) && isuser == true) {
						count = number;// 将登录正确的账号传回数据库，方便查询信息，确保是哪个用户
						new UserFace(count);
						this.dispose();
					} else if (admiCon.queryAdmi(number, password) && isuser == false) {
						count = number;
						new ManageFace(count);
						this.dispose();
					} else {
						JOptionPane.showMessageDialog(null, "用户名或密码输入错误", "信息错误", JOptionPane.ERROR_MESSAGE);
						jpassword.setText(null);
					}
				} else {
					JOptionPane.showMessageDialog(null, "请输入6-16位的数字或者字母", "输入格式错误", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (obj == jbt_login) {
			try {
				new Login();
				this.dispose();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (obj == jbt_forgetPass) {
			JLabel[] jlab_forget = { new JLabel("学号："), new JLabel("密保：") };
			JTextField[] jtext_forget = new JTextField[2];
			try {
				new PubJdialog(jlab_forget, jtext_forget).setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws ParseException {
		new Lading();
	}
}
