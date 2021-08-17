package Tool;

import java.util.regex.Pattern;

/**
 * ������ʽ��֤
 * @author rsw
 *
 */
public class RegexTool {
	private static final String NAME="^([\u4e00-\u9fa5]{2,5})$";
	private static final String CHINESE="^[\\u4e00-\\u9fa5]$";
	private static final String TELE="^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
	private static final String EMAIL="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	private static final String INT="^[0-9]*$";
	private static final String DECIMAL="^[0-9]+(.[0-9]{1,2})?$";
	private static final String IDCARD="(^[1-8][0-7]{2}\\d{3}([12]\\d{3})(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}([0-9Xx])$)";
	private static final String PASSWORD="^[a-zA-Z0-9]{6,16}$";
	private static final String CHINESEENGLISH="^[a-zA-Z\\u4e00-\\u9fa5 ]{2,20}$";
	private static final String CHINESEMATH="^[a-zA-Z0-9\\u4e00-\\u9fa5 ]{2,20}$";
	
	/**
	 * ��֤����
	 */
	public static boolean proveName(String name) {
		return Pattern.matches(NAME, name);
	}
	
	/**
	 * ��֤����
	 */
	public static boolean proveChinese(String chinese) {
		return Pattern.matches(CHINESE, chinese);
	}
	
	/**
	 * ��֤�ֻ���
	 */
	public static boolean proveTele(String tele) {
		return Pattern.matches(TELE, tele);
	}
	
	/**
	 * ��֤����
	 */
	public static boolean proveEmail(String email) {
		return Pattern.matches(EMAIL, email);
	}
	
	/**
	 * ��֤����
	 */
	public static boolean proveINT(String figure) {
		return Pattern.matches(INT, figure);
	}
	
	/**
	 * ��֤С��
	 */
	public static boolean proveDECIMAL(String decimal) {
		return Pattern.matches(DECIMAL, decimal);
	}
	
	/**
	 * ��֤���֤��
	 */
	public static boolean proveIDCard(String idCard) {
		return Pattern.matches(IDCARD, idCard);
	}
	
	/**
	 * ��֤����
	 */
	public static boolean provePassword(String password) {
		return Pattern.matches(PASSWORD, password);
	}
	
	/**
	 * ��֤���ĺ�����
	 */
	public static boolean proveChineseInt(String chineseInt) {
		return Pattern.matches(CHINESEMATH, chineseInt);
	}
	
	/**
	 * ��֤���ĺ�Ӣ��
	 */
	public static boolean proveChineseEnglish(String chineseEnglish) {
		return Pattern.matches(CHINESEENGLISH, chineseEnglish);
	}
}
