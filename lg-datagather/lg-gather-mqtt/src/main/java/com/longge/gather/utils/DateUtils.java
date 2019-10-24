package com.longge.gather.utils;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
/**
 * 日期工具类
 * @author jianglong
 */
public class DateUtils {

	private final static String defaultFormat = "yyyy-MM-dd HH:mm:ss";

	/**获取当前时间*/
	public static String getCurrentDate(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	public static Date getCurrentDateT(String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String dateStr = sdf.format(new Date());
		return sdf.parse(dateStr);
	}

	/**获取制定日期的格式化字符串*/
	public static String getFormatedDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**将字符串转换成日期*/
	public static Date parseDateFromString(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(date);
	}

	public static Date parseDateFromString(String date, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}

	/**获取当前时刻时间*/
	public static long getCurrentTime(){
		return System.currentTimeMillis();
	}

	/**日期与时间转换*/
	public static long dateToTime(String dateStr){
		try{
			Date date = parseDateFromString(dateStr,defaultFormat);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.getTimeInMillis();
		}catch(Exception e){
			e.printStackTrace();
		}
        return 0L;
	}


	/**时间转日期*/
	public static String dateToTime(long dataL){
		try{
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(dataL);
			String dayStr = new SimpleDateFormat(defaultFormat).format(cal.getTime());
			return dayStr;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	/**判断哪个日期在前 日过日期一在日期二之前，返回true,否则返回false*/
	public static boolean isBefore(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);

		if (c1.before(c2))
			return true;
		return false;
	}

	/**获取指定月份的最后一天*/
	public static Date lastDayOfMonth(Date date, String date1) throws ParseException {
		Date _date = null;
		if (null != date)
			_date = date;
		if (null == date && null != date1)
			_date = parseDateFromString(date1);

		Calendar cal = Calendar.getInstance();
		cal.setTime(_date);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**是否是闰年*/
	public static boolean isLeapYear(int year) {
		GregorianCalendar calendar = new GregorianCalendar();
		return calendar.isLeapYear(year);
	}

	/**获得指定日期的前一天*/
	public static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	/**获得指定日期的后一天*/
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayAfter;
	}

	/**获取一天开始时间 如 2014-12-12 00:00:00*/
	public static Date getDayStart() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**获取一天结束时间 如 2014-12-12 23:59:59*/
	public static Date getDayEnd() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 时间分段 比如：2014-12-12 10:00:00 ～ 2014-12-12 14:00:00 分成两段就是 2014-12-12
	 * 10：00：00 ～ 2014-12-12 12：00：00 和2014-12-12 12：00：00 ～ 2014-12-12 14：00：00
	 * @param start 起始日期
	 * @param end 结束日期
	 * @param pieces 分成几段
	 */
	public static Date[] getDatePiece(Date start, Date end, int pieces) {
		Long sl = start.getTime();
		Long el = end.getTime();
		Long diff = el - sl;
		Long segment = diff / pieces;
		Date[] dateArray = new Date[pieces + 1];
		for (int i = 1; i <= pieces + 1; i++) {
			dateArray[i - 1] = new Date(sl + (i - 1) * segment);
		}
		// 校正最后结束日期的误差，可能会出现偏差，比如14:00:00 ,会变成13:59:59之类的
		dateArray[pieces] = end;
		return dateArray;
	}

	/**
	 * 计算两个日期之间相差的天数
	 * @param smdate 较小的时间
	 * @param bdate 较大的时间
	 * @return 相差天数
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**获得当前日期的前几天一个日期*/
	public static Date getDateFrontDays(int frontDays) {
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - frontDays);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(c.getTime());
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static Timestamp getTimestamp(int year, int month, int dayOfMonth, int hour, int minute, int second) {
		try {
			String timeStr = year + "-" + month + "-" + dayOfMonth + " " + hour + ":" + minute + ":" + second;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = format.parse(timeStr);
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**UTC时间转北京时间*/
	public static String getBeijinTime(String time) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int hour = c.get(Calendar.HOUR);
		c.set(Calendar.HOUR, hour + 8);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
		return dayAfter;
	}


	/**计算时间与当前时间的差值*/
	public static int getDifferSeconds(Date date1, Date date2) {
		if (date1 != null && date2 != null) {
			long a = date1.getTime();
			long b = date2.getTime();
			int c = (int) ((a - b) / 1000);
			return c;
		}
		return 0;
	}

}
