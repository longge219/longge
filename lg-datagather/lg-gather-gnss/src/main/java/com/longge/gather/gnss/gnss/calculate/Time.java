package com.longge.gather.gnss.gnss.calculate;
import com.longge.gather.gnss.gnss.constant.GnssConstants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
/**
 * Description:时间系统转换
 * User: jianglong
 * Date: 2018年7月11号
 */
public class Time {
	
	//毫秒整数(unix时间1970年1月1号起算)
	private long msec; 
	
	//毫秒小数
	private double fraction; 

	//跳秒日期
	private Date[] leapDates;
	
    //日历标准切换，修改起算日期
	private Calendar gc =  GregorianCalendar.getInstance();
	
	//时区
	private TimeZone zone = TimeZone.getTimeZone("GMT Time");
	
	//日期格式
	private DateFormat df = new SimpleDateFormat("yyyy MM dd HH mm ss.SSS");

	void initleapDates() throws ParseException{
		leapDates = new Date[19];
		leapDates[0]  = df.parse("1980 01 06 00 00 00.0");
		leapDates[1]  = df.parse("1981 07 01 00 00 00.0");
		leapDates[2]  = df.parse("1982 07 01 00 00 00.0");
		leapDates[3]  = df.parse("1983 07 01 00 00 00.0");
		leapDates[4]  = df.parse("1985 07 01 00 00 00.0");
		leapDates[5]  = df.parse("1988 01 01 00 00 00.0");
		leapDates[6]  = df.parse("1990 01 01 00 00 00.0");
		leapDates[7]  = df.parse("1991 01 01 00 00 00.0");
		leapDates[8]  = df.parse("1992 07 01 00 00 00.0");
		leapDates[9]  = df.parse("1993 07 01 00 00 00.0");
		leapDates[10] = df.parse("1994 07 01 00 00 00.0");
		leapDates[11] = df.parse("1996 01 01 00 00 00.0");
		leapDates[12] = df.parse("1997 07 01 00 00 00.0");
		leapDates[13] = df.parse("1999 01 01 00 00 00.0");
		leapDates[14] = df.parse("2006 01 01 00 00 00.0");
		leapDates[15] = df.parse("2009 01 01 00 00 00.0");
		leapDates[16] = df.parse("2012 07 01 00 00 00.0");
		leapDates[17] = df.parse("2015 07 01 00 00 00.0");
		leapDates[18] = df.parse("2017 01 01 00 00 00.0");
	}

	//构造函数
	public Time(long msec){
		df.setTimeZone(zone);
		gc.setTimeZone(zone);
		this.gc.setTimeInMillis(msec);
		this.msec = msec;
		this.fraction = 0;
	}
	
	public Time(long msec, double fraction){
		df.setTimeZone(zone);
		gc.setTimeZone(zone);
		this.msec = msec;
		this.gc.setTimeInMillis(msec);
		this.fraction = fraction;
	}
	
	public Time(String dateStr) throws ParseException{
		df.setTimeZone(zone);
		gc.setTimeZone(zone);
		this.msec = dateStringToTime(dateStr);
		this.gc.setTimeInMillis(this.msec);
		this.fraction = 0;
	}
	
	public Time(int gpsWeek, double weekSec){
		df.setTimeZone(zone);
		gc.setTimeZone(zone);
		double fullTime = (GnssConstants.UNIX_GPS_DAYS_DIFF * GnssConstants.SEC_IN_DAY + gpsWeek*GnssConstants.DAYS_IN_WEEK*GnssConstants.SEC_IN_DAY + weekSec) * 1000L;
		this.msec = (long) (fullTime);
		this.fraction = fullTime - this.msec;
		this.gc.setTimeInMillis(this.msec);
	}
	
	/**字符串日期转时间*/
	private long dateStringToTime(String dateStr) throws ParseException {

		long dateTime = 0;

		try {
			Date dateObj = df.parse(dateStr);
			dateTime = dateObj.getTime();
		} catch (ParseException e) {
			throw e;
		}

		return dateTime;
	}

	/**GPS时间转unix时间
	 * GPS时间(1980年1月6号)
	 * unix时间(1970年1月1号)
	 * */
	public static long gpsToUnixTime(double time, int week) {
		time = (time + (week * GnssConstants.DAYS_IN_WEEK + GnssConstants.UNIX_GPS_DAYS_DIFF) * GnssConstants.SEC_IN_DAY) * GnssConstants.MILLISEC_IN_SEC;

		return (long)time;
	}

	/**unix时间转GPS秒*/
	private static double unixToGpsTime(double time) {
		time = time / GnssConstants.MILLISEC_IN_SEC - GnssConstants.UNIX_GPS_DAYS_DIFF * GnssConstants.SEC_IN_DAY;
		time = time%(GnssConstants.DAYS_IN_WEEK * GnssConstants.SEC_IN_DAY);
		return time;
	}

	/**获取GPS周数*/
	public int getGpsWeek(){
		long time = msec / GnssConstants.MILLISEC_IN_SEC - GnssConstants.UNIX_GPS_DAYS_DIFF * GnssConstants.SEC_IN_DAY;
		return (int)(time/(GnssConstants.DAYS_IN_WEEK * GnssConstants.SEC_IN_DAY))%1024;
	}
	
	/**获取GPS周内秒*/
	public int getGpsWeekSec(){
		long time = msec / GnssConstants.MILLISEC_IN_SEC - GnssConstants.UNIX_GPS_DAYS_DIFF * GnssConstants.SEC_IN_DAY;
		return (int)(time%(GnssConstants.DAYS_IN_WEEK * GnssConstants.SEC_IN_DAY));
	}
	
	/**获取GPS周天*/
	public int getGpsWeekDay(){
		return (int)(getGpsWeekSec()/GnssConstants.SEC_IN_DAY);
	}
	
	/**获取GPS天小时*/
	public int getGpsHourInDay(){
		long time = msec / GnssConstants.MILLISEC_IN_SEC - GnssConstants.UNIX_GPS_DAYS_DIFF * GnssConstants.SEC_IN_DAY;
		return (int)((time%(GnssConstants.SEC_IN_DAY))/GnssConstants.SEC_IN_HOUR);
	}
	
	public int getYear(){
		return gc.get(Calendar.YEAR);
	}
	public int getYear2c(){
		return gc.get(Calendar.YEAR)-2000;
	}
	public int getDayOfYear(){
		return gc.get(Calendar.DAY_OF_YEAR);
	}
	public String getHourOfDayLetter(){
		char c = (char)('a'+getGpsHourInDay());
		return ""+c;
	}

	public double getGpsTime(){
		return unixToGpsTime(msec);
	}

	public double getRoundedGpsTime(){
		double tow = unixToGpsTime((msec+499)/1000*1000);
		return tow;
	}

	//获取GPS跳秒
	public int getLeapSeconds(){
		if( leapDates == null )
			try {
				initleapDates();
			} catch (Exception e) {
				e.printStackTrace();
			}
		int leapSeconds = leapDates.length - 1;
		double delta;
		for (int d = 0; d < leapDates.length; d++) {
			delta = leapDates[d].getTime() - msec;
			if (delta > 0) {
				leapSeconds = d - 1;
				break;
			}
		}
		return leapSeconds;
	}
	
	public long getMsec() {
		return msec;
	}

	public void setMsec(long msec) {
		this.msec = msec;
	}

	public double getFraction() {
		return fraction;
	}

	public void setFraction(double fraction) {
		this.fraction = fraction;
	}

	public Object clone(){
		return new Time(this.msec,this.fraction);
	}

	public String toString(){
		return df.format(gc.getTime())+" "+gc.getTime();
	}
}
