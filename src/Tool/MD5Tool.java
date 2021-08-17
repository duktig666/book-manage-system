package Tool;

import java.security.MessageDigest;

/**
 * MD5加密及解密
 * 
 * @author rsw
 *
 */
public class MD5Tool {
	/***
	 * MD5加码 生成32位md5码
	 */
	public static String string2MD5(String inStr) {
		MessageDigest md5 = null;// MessageDigest 类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。
		// 信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
		try {
			md5 = MessageDigest.getInstance("MD5");// 返回实现指定摘要算法的 MessageDigest 对象
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			// return "";
		}
		char[] charArray = inStr.toCharArray();// char 字符型数组 toCharArray()将字符串转换成字符数组，便于对每一个字符进行单独操作，比如加个密什么的、这仅用来增加灵活性，
		byte[] byteArray = new byte[charArray.length];// 将字符型数组转换成字节型数组

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];// 将字符型数组中每个字符转换成字节
		byte[] md5Bytes = md5.digest(byteArray);// 使用指定的 byte 数组对摘要进行最后更新，然后完成摘要计算（转换成MD5码）
		StringBuffer hexValue = new StringBuffer();// 创建字符串变量
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;// "byte[i] & 0xFF"只是保证了v的前三个字节是0，只有最后一个字节有数。
			// 0xFF是个整数常数，只是用16进制形式表示出来了
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));// 转换为16进制
		}
		return hexValue.toString();// 将对象转换为字符串
	}

	/**
	 * 加密解密算法 执行一次加密，两次解密
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
