package View;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * 管理员界面
 * @author rsw
 *
 */
public class ManageFace extends JFrame {
	final int WIDTH = 1000, HEIGHT = 730;
	JTabbedPane jtab = new JTabbedPane(JTabbedPane.TOP);// 创建选项卡窗格，选项卡标题在上方
	JPanel[] jpan = new JPanel[6];
	static String count;
	ManageBook manageBook =new ManageBook();
	ManageBookType manageBookType=new ManageBookType();
	ManageReader manageReader=new ManageReader();
	ManageReaderType  manageReaderType=new ManageReaderType();
	ManageBorrowInfo manageBorrowInfo =new  ManageBorrowInfo();
	ManageSuper manageSuper =new  ManageSuper();	
	public ManageFace(String count) throws SQLException {
		this.count=count;
		this.setLayout(null);
		this.setTitle("图书管理系统（管理员端）");
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);// 设置窗体居中显示
		this.setResizable(false);// 窗口不能改变大小
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// 单击窗口关闭按钮，结束程序
		this.setVisible(true);// 使窗口显示
		
		jtab.setSize(WIDTH, HEIGHT);

		jpan[0] = manageBook.addPanel0();
		jtab.addTab("图书信息管理", jpan[0]);
		jtab.setSelectedIndex(0);

		jpan[1] = manageBookType.addPanel1();
		jtab.addTab("图书类型管理", jpan[1]);

		jpan[2] = manageReader.addPanel2();
		jtab.addTab("读者信息管理", jpan[2]);

		jpan[3] = manageReaderType.addPanel3();
		jtab.addTab("读者类型管理   ", jpan[3]);

		jpan[4] = manageBorrowInfo.addPanel4();
		jtab.addTab("图书借阅管理", jpan[4]);
		
		jpan[5] = manageSuper.addPanel5(count);
		jtab.addTab("超级管理员", jpan[5]);

		this.add(jtab);
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
}
