package Tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 公共的时间转换类
 * @author rsw
 */
public class TimeTool {
	static SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 可以方便修改日期格式

	public TimeTool() throws ParseException {
		String newTime = sd.format(new Date());// 获取当前时间
		System.out.println("输出当前时间:" + newTime);
		Date date = sd.parse(newTime);// 当前时间转换为时间戳
		// parse() 解析字符串的文本，生成 Date
		long unixTimestamp = date.getTime() / 1000;// 将时间戳转换为秒
		System.out.println("输出时间戳:" + unixTimestamp);
		String ntime = sd.format(unixTimestamp * 1000);// 时间戳转换为当前时间
		System.out.println("输出当前时间[时间戳转换]:" + ntime);// 输出当前时间
	}

	/**
	 * 获取当前时间方法
	 */
	public static String getNewTime() {
		String newTime = sd.format(new Date());// 获取当前时间
		return newTime;
	}

	/**
	 * 获取当前时间戳方法
	 */
	public static int getNewStamep() {
		int newTimeStamep = (int) (new Date().getTime()/1000);
		return newTimeStamep;
	}

	/**
	 * 将时间转换为时间戳
	 */
	public static String dateToStamp(String time) throws ParseException {
		String newTime;
		Date date = sd.parse(time);
		int ts = (int) (date.getTime()/1000);
		newTime = String.valueOf(ts);
		return newTime;
	}

	/**
	 * 将时间戳转换为时间
	 */
	public static String stampToDate(String stamep) {
		String newStamep;
		long lt =  new Long(stamep)*1000;
		Date date = new Date(lt);
		newStamep = sd.format(date);
		return newStamep;
	}
	
	/**
	 * 将MySQL中的TimeStamep类型转换为String
	 */
	public static String stampToDate(Object stamep) {
		String timeStr = sd.format(stamep);//将一个 Date 格式化为日期/时间字符串
		return timeStr;
	}
}
