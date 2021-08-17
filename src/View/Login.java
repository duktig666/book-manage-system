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
 * ע�����
 * 
 * @author rsw
 *
 */
public class Login extends JFrame implements ActionListener, ItemListener {
	JLabel[] jlab = { new JLabel("ѧ�ţ�"), new JLabel("������"), new JLabel("�Ա�"), new JLabel("�������ͣ�"), new JLabel("Ժϵ��"),
			new JLabel("�༶��"), new JLabel("�ֻ����룺"), new JLabel("�������䣺"), new JLabel("�ܱ���"), new JLabel("���룺"),
			new JLabel("ȷ�����룺") };// ������ǩ����
	JButton jbt = new JButton("ȷ��");
	ReaderCon readercon = new ReaderCon();
	JTextField jtext[] = new JTextField[7];
	JComboBox<String> jcb_readerType = new JComboBox<String>();
	JComboBox<String> jcb_gender = new JComboBox<String>();
	JPasswordField jpassword[] = new JPasswordField[2];
	String r_type = "����", gender = "��";
	String[] hint = { "20��ͷ��11λ����", "���ĺ���", "���ĺ���", "���ĺ��ּ�����", "�ֻ��Ÿ�ʽ", "�����ʽ", "��������" };
	Reader reader = new Reader();
	ReaderTypeCon readerTypeCon = new ReaderTypeCon();

	public Login() throws SQLException {
		int[] inuput_int = { 11, 5, 10, 10, 11, 20, 15 };// �������������λ��
		for (int i = 0; i < jtext.length; i++) {
			jtext[i] = new JTextField();
			if (i < 2) {
				jtext[i].setBounds(150, 20 + i * 40, 150, 30);
			}
			if (i >= 2) {
				jtext[i].setBounds(150, 20 + (i + 2) * 40, 150, 30);
			}
			jtext[i].setDocument(new InputLimit(inuput_int[i]));// ��������
			jtext[i].addFocusListener(new InputLimit(jtext[i], hint[i]));// ������ڲ��ʾ���ⲿ�����
			this.add(jtext[i]);
		}
		jcb_gender.setBounds(150, 100, 80, 30);
		jcb_readerType.setBounds(150, 140, 80, 30);
		jcb_gender.addItem("��");
		jcb_gender.addItem("Ů");
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
		this.setTitle("ͼ�����ϵͳ");
		this.setSize(400, 600);
		this.setLocationRelativeTo(null);// ���ô��������ʾ
		this.setResizable(false);// ���ڲ��ܸı��С
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// �������ڹرհ�ť,�Զ����ز��ͷŸô���
		this.setVisible(true);// ʹ������ʾ
		// ����������
		this.add(jbt);
		this.add(jcb_readerType);
		this.add(jcb_gender);

		// ��Ӽ���
		jbt.addActionListener(this);// ���ð�ť�ļ�����
		jcb_readerType.addItemListener(this);
		jcb_gender.addItemListener(this);
		// ���ڹر��¼�
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
				InputLimit.TELE, InputLimit.EMAIL, InputLimit.PASSWORD };// ��֤ѧ�ţ���20��ͷ��11λ���֣�����֤���������ģ�����֤�ֻ��š���֤���䡢��֤���루6-16λ���ֻ�����ĸ��
		String[] input = { jtext[0].getText(), jtext[1].getText(), jtext[2].getText(), jtext[3].getText(),
				jtext[4].getText(), jtext[5].getText(), new String(jpassword[0].getPassword()) };
		String[] hintError = { "ѧ�Ÿ�ʽ����", "������ʽ����", "Ժϵ��ʽ����", "�༶��ʽ����", "�ֻ��Ÿ�ʽ����", "�����ʽ����", "�����ʽ����6-16λ���ֻ���ĸ��" };
		String message = "";
		boolean result[] = InputLimit.regular(regex, input);
		Object obj = e.getSource();
		if (obj == jbt) {
			try {
				for (int i = 0; i < jtext.length; i++) {
					for (int j = 0; j < jpassword.length; j++) {
						if (jtext[i].getText().equals("") || new String(jpassword[j].getPassword()).equals("")) {
							JOptionPane.showMessageDialog(null, "����ע�����Ϣ�в���Ϊ��", "��Ϣ����", JOptionPane.ERROR_MESSAGE);
							return;// ����ѭ������ֹһֱѭ�������Ի���
						}
					}
				}
				for (int i = 0; i < result.length; i++) {
					if (!result[i]) {
						message += "\n" + hintError[i];
					}
				}
				if (message.equals("")) {// ����������֤
					// ������������һ�£�����ע�������Ϣ�ķ���
					if (new String(jpassword[0].getPassword()).equals(new String(jpassword[1].getPassword()))) {
						// ����˺��Ƿ�ע��
						if (!readercon.isNumber(jtext[0].getText())) {
							readercon.insertReader(jtext[0].getText(), jtext[1].getText(), gender,
									readerTypeCon.queryReaderTypeID(r_type), jtext[2].getText(), jtext[3].getText(),
									jtext[4].getText(), jtext[5].getText(), jtext[6].getText(),
									MD5Tool.string2MD5(new String(jpassword[0].getPassword())));
							// ���������MD5���ܺ��������Ϣ
							JOptionPane.showMessageDialog(null, "�˺��ѳɹ�ע��", "�����ɹ�", JOptionPane.INFORMATION_MESSAGE);
							this.dispose();
							int c = JOptionPane.showConfirmDialog(null, "�Ƿ�Ҫֱ�ӽ��е�¼����", "��֤����",
									JOptionPane.YES_NO_OPTION);
							if (c == JOptionPane.YES_OPTION) {
								new Lading().jtext.setText(jtext[0].getText());// ����ע����˺�set����¼ҳ��
							}
						} else {
							JOptionPane.showMessageDialog(null, "���˺��Ѿ���ע��\n�����ѧ�ź��ٽ���ע��", "����ʧ��",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "�����������벻һ��", "��Ϣ����", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, message, "������Ϣ����", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * ���������¼�
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
