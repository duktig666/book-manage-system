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
 * �����ĶԻ�����
 */
public class PubJdialog extends JDialog {
	ReaderCon readercon = new ReaderCon();
	BookCon bookCon = new BookCon();
	BookTypeCon bookTypeCon = new BookTypeCon();
	ReaderTypeCon readerTypeCon = new ReaderTypeCon();
	AdmiCon admiCon = new AdmiCon();
	String number, b_type;// r_number �����˺� b_type ͼ������
	int booktype = 1, a;// ͼ�����͵�id
	JTextField[] jtext_readerType;

	/**
	 * �û����޸Ķ������������Ի���
	 */
	public PubJdialog(String number, boolean isUser) {
		this.number = number;
		JLabel[] jlab = { new JLabel("�����룺"), new JLabel("�ܱ���"), new JLabel("�����룺"), new JLabel("ȷ�����룺") };
		JPasswordField[] jpassword = new JPasswordField[4];
		JPanel jpan = new JPanel(new GridLayout(4, 2));
		JButton jbt = new JButton("ȷ�˸�������");
		setTitle("�޸Ķ�������");
		setModal(true);// �Ƿ���ֹ����ʾ��ʱ������������������,ֻ�ܲ����˶Ի���
		setSize(350, 220);// �Ի���Ĵ�С
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// �رպ����ٶԻ���
		setLocationRelativeTo(null);
		for (int i = 0; i < jpassword.length; i++) {
			jpassword[i] = new JPasswordField();
			jlab[i].setHorizontalAlignment(0);
			jpan.add(jlab[i]);
			jpan.add(jpassword[i]);
			jpassword[i].setDocument(new InputLimit(16));// ��������
		}
		jpassword[1].setEchoChar((char) 0);
		jpassword[1].enableInputMethods(true);// ������������ʹ�����뷨�����뺺��
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
							JOptionPane.showMessageDialog(null, "���ѳɹ��޸�����", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "�����������Ϣ������߸�ʽ����ȷ", "��Ϣ����", JOptionPane.ERROR_MESSAGE);
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
	 * ����Ա������ͼ�������Ի��� ���췽��������
	 * 
	 * ����ͼ��ĶԻ���
	 */
	public PubJdialog() throws SQLException {
		JLabel[] jlab = { new JLabel("ISBN��"), new JLabel("������"), new JLabel("ͼ�����ͣ�"), new JLabel("���ߣ�"),
				new JLabel("�����磺"), new JLabel("�۸�"), new JLabel("�������") };
		JTextField[] jtext_insterBook = new JTextField[6];
		JComboBox<String> jcb_bookType = new JComboBox<String>();
		JButton jbt_interBook = new JButton("ȷ��");
		String[] hint = { "10λ����", "���Ļ�Ӣ����ĸ������", "���ĺ�Ӣ����ĸ", "���ĺ�Ӣ����ĸ", "1-2λС��", "����" };
		JPanel jpan = new JPanel();

		setTitle("����ͼ��");
		setModal(true);// �Ƿ���ֹ����ʾ��ʱ������������������,ֻ�ܲ����˶Ի���
		setSize(400, 450);// �Ի���Ĵ�С
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// �رպ����ٶԻ���
		setLocationRelativeTo(null);
		jpan.setLayout(null);
		int[] inuput_int = { 10, 15, 10, 10, 10, 3 };// �������������λ��
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
			jtext_insterBook[i].setDocument(new InputLimit(inuput_int[i]));// ��������
			jpan.add(jtext_insterBook[i]);
			jtext_insterBook[i].addFocusListener(new InputLimit(jtext_insterBook[i], hint[i]));// ������ڲ��ʾ���ⲿ�����
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
		// ��ȡ�����������
		jcb_bookType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				b_type = jcb_bookType.getSelectedItem().toString();
				try {
					if (e.getStateChange() == ItemEvent.SELECTED) {// ��ֹ������ѡ������
						booktype = bookTypeCon.queryBTid(b_type);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		// ����ͼ��
		jbt_interBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] regex = { InputLimit.ISBN, InputLimit.CHINESEENGLISHMATH, InputLimit.CHINESEENGLISH,
						InputLimit.CHINESEENGLISH, InputLimit.DECIMAL, InputLimit.INT };
				String[] input = { jtext_insterBook[0].getText(), jtext_insterBook[1].getText(),
						jtext_insterBook[2].getText(), jtext_insterBook[3].getText(), jtext_insterBook[4].getText(),
						jtext_insterBook[5].getText() };
				String[] hintError = { "ISBN��ʽ����", "������ʽ����", "���߸�ʽ����", "�������ʽ����", "�۸��ʽ����", "�������ʽ����" };
				boolean result[] = InputLimit.regular(regex, input);
				String message = "";
				int c = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ��������ͼ��", "��֤����", JOptionPane.YES_NO_OPTION);
				if (c == JOptionPane.YES_OPTION) {
					try {
						for (int i = 0; i < result.length; i++) {
							if (!result[i]) {
								message += "\n" + hintError[i];
							}
						}
						if (message.equals("")) {// ����������֤
							// ISBN�����ڲſ�������
							if (!bookCon.isISBN(jtext_insterBook[0].getText())) {
								bookCon.insterBook(jtext_insterBook[0].getText(), jtext_insterBook[1].getText(),
										booktype, jtext_insterBook[2].getText(), jtext_insterBook[3].getText(),
										Double.valueOf(jtext_insterBook[4].getText()),
										Integer.parseInt(jtext_insterBook[5].getText()));
								JOptionPane.showMessageDialog(null, "����ͼ��ɹ�", "�����ɹ�", JOptionPane.ERROR_MESSAGE);
								dispose();
							} else {
								JOptionPane.showMessageDialog(null, "ISBN�Ѿ�����\n�����¸���ISBN��������ͼ��", "����ʧ��",
										JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, message, "������Ϣ����", JOptionPane.ERROR_MESSAGE);
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
	 * ����Ա�������������͵ĶԻ��򷽷�
	 */
	public PubJdialog(int a) throws SQLException {
		this.a = a;
		JLabel[] jlab_readerType = { new JLabel("�������ͣ�"), new JLabel("������������"), new JLabel("������������") };
		jtext_readerType = new JTextField[3];
		JPanel jpan_admi = new JPanel(new GridLayout(3, 2));
		JButton jbt_readerType = new JButton("ȷ��������������");
		String[] hint = { "���ĺ���", "����", "����" };
		setTitle("������������");
		setModal(true);// �Ƿ���ֹ����ʾ��ʱ������������������,ֻ�ܲ����˶Ի���
		setSize(300, 180);// �Ի���Ĵ�С
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// �رպ����ٶԻ���
		setLocationRelativeTo(null);
		int[] inuput_int = { 10, 3, 3 };// �������������λ��
		for (int i = 0; i < jtext_readerType.length; i++) {
			jlab_readerType[i].setHorizontalAlignment(0);
			jtext_readerType[i] = new JTextField();
			jpan_admi.add(jlab_readerType[i]);
			jpan_admi.add(jtext_readerType[i]);
			jtext_readerType[i].setDocument(new InputLimit(inuput_int[i]));// ��������
			jtext_readerType[i].addFocusListener(new InputLimit(jtext_readerType[i], hint[i]));// ������ڲ��ʾ���ⲿ�����
		}
		add(jbt_readerType, BorderLayout.SOUTH);
		add(jpan_admi);
		jbt_readerType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!InputLimit.inputIsNull(jtext_readerType)) {
					int c = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ�������˶�������", "��֤����", JOptionPane.YES_NO_OPTION);
					if (c == JOptionPane.YES_OPTION) {
						try {
							String[] regex = { InputLimit.CHINESE, InputLimit.INT, InputLimit.INT };
							String[] input = { jtext_readerType[0].getText(), jtext_readerType[1].getText(),
									jtext_readerType[2].getText() };
							String[] hintError = { "�������͸�ʽ����", "������������ʽ����", "������������ʽ����" };
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
								JOptionPane.showMessageDialog(null, "��Ϣ�޸ĳɹ�", "�����ɹ�", JOptionPane.INFORMATION_MESSAGE);
								dispose();
							} else {
								JOptionPane.showMessageDialog(null, message, "������Ϣ����", JOptionPane.ERROR_MESSAGE);
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
	 * ����������˺š��ܱ���֤
	 */
	public PubJdialog(JLabel[] jlab, JTextField[] jtext) throws SQLException {
		setTitle("�˺š��ܱ���֤");
		setModal(true);// �Ƿ���ֹ����ʾ��ʱ������������������,ֻ�ܲ����˶Ի���
		setSize(300, 180);// �Ի���Ĵ�С
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// �رպ����ٶԻ���
		setLocationRelativeTo(null);
		JPanel jpan = new JPanel(new GridLayout(3, 2));
		JButton jbt = new JButton("ȷ��");
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
					JOptionPane.showMessageDialog(null, "����������˺�Ϊ��", "��ʽ����", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						if (readercon.queryKeeppass(keeppass, count)) {
							// ������������,������Ի���
							new PubJdialog(keeppass, count).setVisible(true);
							dispose();
						} else
							JOptionPane.showMessageDialog(null, "����������˺ź��ܱ���֤��ƥ��", "��֤ʧ��", JOptionPane.ERROR_MESSAGE);
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
	 * ��������Ĺ���
	 */
	public PubJdialog(String forgetPass, String count) throws SQLException {
		JLabel[] jlab = { new JLabel("�����룺"), new JLabel("ȷ�����룺") };
		JPasswordField[] jpassword = new JPasswordField[2];
		JPanel jpan = new JPanel(new GridLayout(2, 2));
		JButton jbt = new JButton("ȷ��");
		setTitle("�޸Ķ�������,������6-16λ�����ֻ�����ĸ");
		setModal(true);// �Ƿ���ֹ����ʾ��ʱ������������������,ֻ�ܲ����˶Ի���
		setSize(350, 150);// �Ի���Ĵ�С
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// �رպ����ٶԻ���
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
					// ��������
					if (resurt && newPass != null) {
						if (new String(jpassword[0].getPassword()).equals(new String(jpassword[1].getPassword()))) {
							readercon.resetPass(forgetPass, count, newPass);
							dispose();
							JOptionPane.showMessageDialog(null, "�������óɹ�", "�����ɹ�", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "��������������벻һ��", "����ʧ��", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "������������Ϊ�ջ��߸�ʽ����ȷ", "����ʧ��", JOptionPane.ERROR_MESSAGE);
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
	 * ��������Ա�ĶԻ���
	 */
	public PubJdialog(boolean is) throws SQLException {
		JLabel[] jlab_admi = { new JLabel("�˺ţ�"), new JLabel("������"), new JLabel("���֤�ţ�"), new JLabel("�ֻ��ţ�"),
				new JLabel("���䣺"), new JLabel("�ܱ���"), new JLabel("���룺"), new JLabel("��֤���룺") };
		JTextField[] jtext_admi = new JTextField[6];
		JPasswordField[] jpass_admi = new JPasswordField[2];
		JPanel jpan_admi = new JPanel();
		JButton jbt_admi = new JButton("ȷ��");
		String[] hint = { "�˺ţ�6-16λ������ĸ��", "���ĺ���", "���֤��", "�ֻ��Ÿ�ʽ", "�����ʽ", "��������" };
		setTitle("��������Ա");
		setModal(true);// �Ƿ���ֹ����ʾ��ʱ������������������,ֻ�ܲ����˶Ի���
		setSize(300, 330);// �Ի���Ĵ�С
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// �رպ����ٶԻ���
		setLocationRelativeTo(null);
		jpan_admi.setLayout(null);
		int[] inuput_int = { 16, 5, 18, 11, 20, 15 };// �������������λ��
		for (int i = 0; i < jlab_admi.length; i++) {
			jlab_admi[i].setHorizontalAlignment(0);
			jlab_admi[i].setBounds(0, i * 30, 100, 30);
			jpan_admi.add(jlab_admi[i]);
		}
		for (int i = 0; i < jtext_admi.length; i++) {
			jtext_admi[i] = new JTextField();
			jtext_admi[i].setBounds(130, i * 30, 150, 30);
			jpan_admi.add(jtext_admi[i]);
			jtext_admi[i].setDocument(new InputLimit(inuput_int[i]));// ��������
			jtext_admi[i].addFocusListener(new InputLimit(jtext_admi[i], hint[i]));// ������ڲ��ʾ���ⲿ�����
		}
		for (int i = 0; i < jpass_admi.length; i++) {
			jpass_admi[i] = new JPasswordField();
			jpass_admi[i].setBounds(130, 180 + i * 30, 150, 30);
			jpan_admi.add(jpass_admi[i]);
			jpass_admi[i].setDocument(new InputLimit(16));// ��������
		}
		add(jbt_admi, BorderLayout.SOUTH);
		add(jpan_admi);
		jbt_admi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] regex = { InputLimit.COUNT, InputLimit.NAME, InputLimit.IDCARD, InputLimit.TELE,
						InputLimit.EMAIL, InputLimit.PASSWORD };// ��֤���֤�š��ֻ��š����䡢����
				String[] input = { jtext_admi[0].getText(), jtext_admi[1].getText(), jtext_admi[2].getText(),
						jtext_admi[3].getText(), jtext_admi[4].getText(), new String(jpass_admi[0].getPassword()) };
				String[] hintError = { "�˺Ÿ�ʽ����", "������ʽ����", "���֤�Ÿ�ʽ����", "�ֻ��Ÿ�ʽ����", "�����ʽ����", "�����ʽ����" };
				String message = "";
				boolean result[] = InputLimit.regular(regex, input);
				for (int i = 0; i < result.length; i++) {
					if (!result[i]) {
						message += "\n" + hintError[i];
					}
				}
				if (message.equals("")) {// ����������֤
					if (new String(jpass_admi[0].getPassword()).equals(new String(jpass_admi[1].getPassword()))) {
						// ������������һ�£�����ע�������Ϣ�ķ���
						try {
							admiCon.insterAdmi(jtext_admi[0].getText(), jtext_admi[1].getText(),
									jtext_admi[2].getText(), jtext_admi[3].getText(), jtext_admi[4].getText(),
									jtext_admi[5].getText(),
									MD5Tool.string2MD5(new String(jpass_admi[0].getPassword())));
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// ���������MD5���ܺ��������Ϣ
						JOptionPane.showMessageDialog(null, "���ѳɹ���������Ա", "�����ɹ�", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "�����������벻һ��", "��Ϣ����", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, message, "������Ϣ����", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public static boolean success = false;

	// ��������Ա��֤
	public PubJdialog(JTextField jtext_super, JButton[] jbt_super) throws SQLException {
		JLabel[] jlab = { new JLabel("�˺ţ�"), new JLabel("���룺") };
		JPasswordField[] jpassword = new JPasswordField[2];
		JButton jbt = new JButton("ȷ��");
		JPanel jpan = new JPanel(new GridLayout(2, 2));
		setTitle("��������Ա��֤");
		setModal(true);// �Ƿ���ֹ����ʾ��ʱ������������������,ֻ�ܲ����˶Ի���
		setSize(350, 150);// �Ի���Ĵ�С
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// �رպ����ٶԻ���
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
						JOptionPane.showMessageDialog(null, "���볬������Աģʽ������", "�����ɹ�", JOptionPane.INFORMATION_MESSAGE);
						success = true;
					} else {
						JOptionPane.showMessageDialog(null, "��������Ա��֤ʧ�ܣ�����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
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
	 * �޸�ͼ����Ϣ �޸Ķ�����Ϣ �޸Ķ���������Ϣ �����Ի���
	 */
	int isAlter_int = 0;

	public PubJdialog(int dialogHeight, int dialogRow, JLabel[] jlab_update, JTextField[] jtext_update,
			Object[] alterData, int tablePanelIndex, JLabel[] jlab_hint) throws SQLException {
		JPanel jpan_update = new JPanel(new GridLayout(dialogRow, 3));
		JButton jbt_update = new JButton("ȷ��");
		boolean[] isAlter_boolean = new boolean[jtext_update.length];
		setTitle("�޸Ĺ���");
		setModal(true);// �Ƿ���ֹ����ʾ��ʱ������������������,ֻ�ܲ����˶Ի���
		setSize(500, dialogHeight);// �Ի���Ĵ�С
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// �رպ����ٶԻ���
		setLocationRelativeTo(null);
		for (int i = 0; i < jtext_update.length; i++) {
			jtext_update[i] = new JTextField();
			if (tablePanelIndex == 0) {
				int[] inuput_int = { 11, 10, 10, 10, 11, 10, 3 };// �������������λ��
				jtext_update[i].setDocument(new InputLimit(inuput_int[i]));// ��������
			} else if (tablePanelIndex == 2) {
				int[] inuput_int = { 11, 10, 10, 11, 20 };// �������������λ��
				jtext_update[i].setDocument(new InputLimit(inuput_int[i]));// ��������
			} else if (tablePanelIndex == 3) {
				int[] inuput_int = { 11, 10, 3, 3 };// �������������λ��
				jtext_update[i].setDocument(new InputLimit(inuput_int[i]));// ��������
			} else if (tablePanelIndex == 10) {
				int[] inuput_int = { 11, 11, 20 };// �������������λ��
				jtext_update[i].setDocument(new InputLimit(inuput_int[i]));// ��������
			} else if (tablePanelIndex == 11) {
				int[] inuput_int = { 11, 10, 10, 11, 20 };// �������������λ��
				jtext_update[i].setDocument(new InputLimit(inuput_int[i]));// ��������
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
					// InputLimit.inputIsNull()�ж��ı���������Ϣ�Ƿ�Ϊ��
					if (!InputLimit.inputIsNull(jtext_update)) {
						// ����ÿ���ı��򣬿��Ƿ��������Ϣ�޸�
						for (int i = 0; i < jtext_update.length; i++) {
							isAlter_boolean[i] = jtext_update[i].getText().equals(alterData[i].toString());// �Ƿ�����ݽ������޸�
							if (!isAlter_boolean[i]) {
								isAlter_int++;// �����һ��û�޸�+1
							}
						}
						// ������޸�ִ��
						if (isAlter_int != 0) {
							/*
							 * �жϵڼ���ѡ�������޸Ĺ��� 0Ϊ�޸�ͼ����Ϣ
							 */
							if (tablePanelIndex == 0) {
								String[] regex = { InputLimit.ISBN, InputLimit.CHINESEENGLISHMATH,
										InputLimit.CHINESEENGLISH, InputLimit.CHINESEENGLISH, InputLimit.DECIMAL,
										InputLimit.INT };
								String[] input = { jtext_update[1].getText(), jtext_update[2].getText(),
										jtext_update[3].getText(), jtext_update[4].getText(), jtext_update[5].getText(),
										jtext_update[6].getText() };
								String[] hintError = { "ISBN��ʽ����", "������ʽ����", "���߸�ʽ����", "�������ʽ����", "�۸��ʽ����", "�������ʽ����" };
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
									JOptionPane.showMessageDialog(null, "��Ϣ�޸ĳɹ�", "�����ɹ�",
											JOptionPane.INFORMATION_MESSAGE);
									dispose();
									success = true;
								} else {
									JOptionPane.showMessageDialog(null, message, "������Ϣ����", JOptionPane.ERROR_MESSAGE);
								}
							}
							/*
							 * 2Ϊ�޸Ķ�����Ϣ
							 */
							else if (tablePanelIndex == 2) {
								String[] regex = { InputLimit.CHINESE, InputLimit.CHINESEMATH, InputLimit.TELE,
										InputLimit.EMAIL };
								String[] input = { jtext_update[1].getText(), jtext_update[2].getText(),
										jtext_update[3].getText(), jtext_update[4].getText() };
								String[] hintError = { "Ժϵ��ʽ����", "�༶��ʽ����", "�ֻ��Ÿ�ʽ����", "�����ʽ����" };
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
									JOptionPane.showMessageDialog(null, "��Ϣ�޸ĳɹ�", "�����ɹ�",
											JOptionPane.INFORMATION_MESSAGE);
									dispose();
									success = true;
								} else {
									JOptionPane.showMessageDialog(null, message, "������Ϣ����", JOptionPane.ERROR_MESSAGE);
								}
							}
							/*
							 * 3Ϊ�޸Ķ���������Ϣ
							 */

							else if (tablePanelIndex == 3) {
								String[] regex = { InputLimit.CHINESE, InputLimit.INT, InputLimit.INT, };
								String[] input = { jtext_update[1].getText(), jtext_update[2].getText(),
										jtext_update[3].getText() };
								String[] hintError = { "�������͸�ʽ����", "������������ʽ����", "������������ʽ����" };
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
									JOptionPane.showMessageDialog(null, "��Ϣ�޸ĳɹ�", "�����ɹ�",
											JOptionPane.INFORMATION_MESSAGE);
									dispose();
									success = true;
								} else {
									JOptionPane.showMessageDialog(null, message, "������Ϣ����", JOptionPane.ERROR_MESSAGE);
								}
							}
							/*
							 * 10Ϊ��������Ա�޸Ĺ���Ա��Ϣ
							 */
							else if (tablePanelIndex == 10) {
								String[] regex = { InputLimit.TELE, InputLimit.EMAIL };
								String[] input = { jtext_update[1].getText(), jtext_update[2].getText() };
								String[] hintError = { "�ֻ��Ÿ�ʽ����", "�����ʽ����" };
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
									JOptionPane.showMessageDialog(null, "��Ϣ�޸ĳɹ�", "�����ɹ�",
											JOptionPane.INFORMATION_MESSAGE);
									dispose();
									success = true;
								} else {
									JOptionPane.showMessageDialog(null, message, "������Ϣ����", JOptionPane.ERROR_MESSAGE);
								}
							}
							/*
							 * 11Ϊ�޸��û���Ϣ
							 */
							else if (tablePanelIndex == 11) {
								String[] regex = { InputLimit.CHINESE, InputLimit.CHINESEMATH, InputLimit.TELE,
										InputLimit.EMAIL };
								String[] input = { jtext_update[1].getText(), jtext_update[2].getText(),
										jtext_update[3].getText(), jtext_update[4].getText() };
								String[] hintError = { "Ժϵ��ʽ����", "�༶��ʽ����", "�ֻ��Ÿ�ʽ����", "�����ʽ����" };
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
									JOptionPane.showMessageDialog(null, "��Ϣ�޸ĳɹ�", "�����ɹ�",
											JOptionPane.INFORMATION_MESSAGE);
									dispose();
									success = true;
								} else {
									JOptionPane.showMessageDialog(null, message, "������Ϣ����", JOptionPane.ERROR_MESSAGE);
								}
							}
						} else {
							JOptionPane.showMessageDialog(null, "��δ����Ϣ�����޸�", "����ʧ��", JOptionPane.ERROR_MESSAGE);
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