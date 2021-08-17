package Tool;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
/**
 * �����������ݼ���Ϣ��ʾ
 * @author rsw
 *
 */
public class InputLimit extends PlainDocument implements FocusListener {
	private int limit; // ���Ƶĳ���
	private String hintText;
	private JTextField textField;
	public static final String NAME="^([\u4e00-\u9fa5]{2,5})$";
	public static final String CHINESE="^[\\u4e00-\\u9fa5]{0,}$";
	public static final String TELE="^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
	public static final String EMAIL="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	public static final String INT="^[0-9]*$";
	public static final String DECIMAL="^[0-9]+(.[0-9]{1,2})?$";
	public static final String IDCARD="(^[1-8][0-7]{2}\\d{3}([12]\\d{3})(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}([0-9Xx])$)";
	public static final String PASSWORD="^[a-zA-Z0-9]{6,16}$";
	public static final String CHINESEENGLISHMATH="^[a-zA-Z0-9\\u4e00-\\u9fa5 ]{2,20}$";
	public static final String CHINESEENGLISH="^[a-zA-Z\\u4e00-\\u9fa5 ]{2,20}$";
	public static final String CHINESEMATH="^[0-9\\u4e00-\\u9fa5 ]{2,20}$";
	public static final String STUDENTNUMBER="^20[\\d]{9}$";
	public static final String ISBN="^[0-9]{10}$";
	public static final String COUNT="^[0-9a-zA-Z]{6,16}$";
	public InputLimit(int limit) {
		super(); // ���ø��๹��
		this.limit = limit;
	}

	/**
	 * �������볤�ȵķ��������ߣ�
	 */
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null)
			return;
		if ((getLength() + str.length()) <= limit) {
			super.insertString(offset, str, attr);// ���ø��෽��
		}
	}

	/**
	 * ����ΪString�͵�������֤���������ߣ�
	 * 
	 * @param regex
	 * @param input
	 * @return
	 */
	public static boolean regular(String regex, String input) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		boolean resurt = matcher.matches();
		return resurt;
	}

	/**
	 * ����ΪString[]�͵�������֤���������ߣ�
	 * 
	 * @param regex[]
	 * @param input[]
	 * @return
	 */
	public static boolean[] regular(String regex[], String input[]) {
		boolean[] resurt =new boolean[regex.length];
		for (int i = 0; i < regex.length; i++) {
			Pattern pattern = Pattern.compile(regex[i]);
			Matcher matcher = pattern.matcher(input[i]);
			resurt[i] = matcher.matches();
		}
		return resurt;
	}

	/**
	 * �������ʾ������Ϣ�Ĺ��� ���ع��췽��
	 */
	public InputLimit(JTextField jTextField, String hintText) {
		this.textField = jTextField;
		this.hintText = hintText;
		jTextField.setText(hintText); // Ĭ��ֱ����ʾ
		jTextField.setForeground(Color.GRAY);// ��ʾΪ��ɫ����
	}

	/**
	 * ��ȡ�����¼�
	 */
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		// ��ȡ����ʱ�������ʾ����
		String temp = textField.getText();
		if (temp.equals(hintText)) {
			textField.setText("");
			textField.setForeground(Color.BLACK);
		}
	}

	/**
	 * ʧȥ�����¼�
	 */
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		// ʧȥ����ʱ��û���������ݣ���ʾ��ʾ����

		String temp = textField.getText();
		if (temp.equals("")) {
			textField.setForeground(Color.GRAY);
			textField.setText(hintText);
		}
	}

	/**
	 * �ж�����������Ƿ�Ϊ��
	 */
	public static boolean inputIsNull(JTextField[] input) {
		for (int i = 0; i < input.length; i++) {
			if (input[i].getText().equals("")) {
				JOptionPane.showMessageDialog(null, "����ע�����Ϣ�в���Ϊ��", "��Ϣ����", JOptionPane.ERROR_MESSAGE);
				return true;// ����ѭ������ֹһֱѭ�������Ի���
			}
		}
		return false;
	}

	/**
	 * �ж�String���������Ƿ�Ϊ��
	 */
	public static boolean strIsNull(String str) {
		if (str.equals("")) {
			return true;
		}
		return false;
	}
}
