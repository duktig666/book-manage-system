package Tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ������ʱ��ת����
 * @author rsw
 */
public class TimeTool {
	static SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ���Է����޸����ڸ�ʽ

	public TimeTool() throws ParseException {
		String newTime = sd.format(new Date());// ��ȡ��ǰʱ��
		System.out.println("�����ǰʱ��:" + newTime);
		Date date = sd.parse(newTime);// ��ǰʱ��ת��Ϊʱ���
		// parse() �����ַ������ı������� Date
		long unixTimestamp = date.getTime() / 1000;// ��ʱ���ת��Ϊ��
		System.out.println("���ʱ���:" + unixTimestamp);
		String ntime = sd.format(unixTimestamp * 1000);// ʱ���ת��Ϊ��ǰʱ��
		System.out.println("�����ǰʱ��[ʱ���ת��]:" + ntime);// �����ǰʱ��
	}

	/**
	 * ��ȡ��ǰʱ�䷽��
	 */
	public static String getNewTime() {
		String newTime = sd.format(new Date());// ��ȡ��ǰʱ��
		return newTime;
	}

	/**
	 * ��ȡ��ǰʱ�������
	 */
	public static int getNewStamep() {
		int newTimeStamep = (int) (new Date().getTime()/1000);
		return newTimeStamep;
	}

	/**
	 * ��ʱ��ת��Ϊʱ���
	 */
	public static String dateToStamp(String time) throws ParseException {
		String newTime;
		Date date = sd.parse(time);
		int ts = (int) (date.getTime()/1000);
		newTime = String.valueOf(ts);
		return newTime;
	}

	/**
	 * ��ʱ���ת��Ϊʱ��
	 */
	public static String stampToDate(String stamep) {
		String newStamep;
		long lt =  new Long(stamep)*1000;
		Date date = new Date(lt);
		newStamep = sd.format(date);
		return newStamep;
	}
	
	/**
	 * ��MySQL�е�TimeStamep����ת��ΪString
	 */
	public static String stampToDate(Object stamep) {
		String timeStr = sd.format(stamep);//��һ�� Date ��ʽ��Ϊ����/ʱ���ַ���
		return timeStr;
	}
}
