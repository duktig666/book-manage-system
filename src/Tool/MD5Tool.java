package Tool;

import java.security.MessageDigest;

/**
 * MD5���ܼ�����
 * 
 * @author rsw
 *
 */
public class MD5Tool {
	/***
	 * MD5���� ����32λmd5��
	 */
	public static String string2MD5(String inStr) {
		MessageDigest md5 = null;// MessageDigest ��ΪӦ�ó����ṩ��ϢժҪ�㷨�Ĺ��ܣ��� MD5 �� SHA �㷨��
		// ��ϢժҪ�ǰ�ȫ�ĵ����ϣ�����������������С�����ݣ�������̶����ȵĹ�ϣֵ��
		try {
			md5 = MessageDigest.getInstance("MD5");// ����ʵ��ָ��ժҪ�㷨�� MessageDigest ����
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			// return "";
		}
		char[] charArray = inStr.toCharArray();// char �ַ������� toCharArray()���ַ���ת�����ַ����飬���ڶ�ÿһ���ַ����е�������������Ӹ���ʲô�ġ����������������ԣ�
		byte[] byteArray = new byte[charArray.length];// ���ַ�������ת�����ֽ�������

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];// ���ַ���������ÿ���ַ�ת�����ֽ�
		byte[] md5Bytes = md5.digest(byteArray);// ʹ��ָ���� byte �����ժҪ���������£�Ȼ�����ժҪ���㣨ת����MD5�룩
		StringBuffer hexValue = new StringBuffer();// �����ַ�������
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;// "byte[i] & 0xFF"ֻ�Ǳ�֤��v��ǰ�����ֽ���0��ֻ�����һ���ֽ�������
			// 0xFF�Ǹ�����������ֻ����16������ʽ��ʾ������
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));// ת��Ϊ16����
		}
		return hexValue.toString();// ������ת��Ϊ�ַ���
	}

	/**
	 * ���ܽ����㷨 ִ��һ�μ��ܣ����ν���
	 */
	public static String convertMD5(String inStr) {

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;
	}
}
