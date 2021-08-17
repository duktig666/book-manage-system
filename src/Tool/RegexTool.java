package Tool;

import java.util.regex.Pattern;

/**
 * 正则表达式验证
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
	 * 验证姓名
	 */
	public static boolean proveName(String name) {
		return Pattern.matches(NAME, name);
	}
	
	/**
	 * 验证中文
	 */
	public static boolean proveChinese(String chinese) {
		return Pattern.matches(CHINESE, chinese);
	}
	
	/**
	 * 验证手机号
	 */
	public static boolean proveTele(String tele) {
		return Pattern.matches(TELE, tele);
	}
	
	/**
	 * 验证邮箱
	 */
	public static boolean proveEmail(String email) {
		return Pattern.matches(EMAIL, email);
	}
	
	/**
	 * 验证整数
	 */
	public static boolean proveINT(String figure) {
		return Pattern.matches(INT, figure);
	}
	
	/**
	 * 验证小数
	 */
	public static boolean proveDECIMAL(String decimal) {
		return Pattern.matches(DECIMAL, decimal);
	}
	
	/**
	 * 验证身份证号
	 */
	public static boolean proveIDCard(String idCard) {
		return Pattern.matches(IDCARD, idCard);
	}
	
	/**
	 * 验证密码
	 */
	public static boolean provePassword(String password) {
		return Pattern.matches(PASSWORD, password);
	}
	
	/**
	 * 验证中文和数字
	 */
	public static boolean proveChineseInt(String chineseInt) {
		return Pattern.matches(CHINESEMATH, chineseInt);
	}
	
	/**
	 * 验证中文和英文
	 */
	public static boolean proveChineseEnglish(String chineseEnglish) {
		return Pattern.matches(CHINESEENGLISH, chineseEnglish);
	}
}
