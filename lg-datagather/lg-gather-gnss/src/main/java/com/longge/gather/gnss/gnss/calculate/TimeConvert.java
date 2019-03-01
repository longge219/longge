package com.longge.gather.gnss.gnss.calculate;
import com.longge.gather.gnss.gnss.constant.GnssConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Description:时间系统转换
 * User: jianglong
 * Date: 2018年09月03号
 */
public class TimeConvert {

	//秒整数
	private long seconds = 0; 
	
	//秒小数
	private double secondRemain = 0.0d;
	
	public TimeConvert(){
		
	}
	/**构造函数*/
	public TimeConvert(int weeks, double second,char staType){
		switch(staType){
				case 'G': 
					TimeConvert timeStart = new TimeConvert(1980,1,6,0,0,0);
					long startSecond = timeStart.getSeconds();
					double startSecondRemain = timeStart.getSecondRemain();
					seconds = startSecond + ((weeks+1024) * GnssConstants.DAYS_IN_WEEK * GnssConstants.SEC_IN_DAY + (int)second);
					secondRemain = startSecondRemain + (second - (int)(second));
					break;
				case 'C': 
					TimeConvert time2Start = new TimeConvert(2006,1,1,0,0,0);
					long start2Second = time2Start.getSeconds();
					double start2SecondRemain = time2Start.getSecondRemain();
					seconds = start2Second + ((weeks) * GnssConstants.DAYS_IN_WEEK * GnssConstants.SEC_IN_DAY + (int)second);
					secondRemain = start2SecondRemain + (second - (int)(second));
					break;
		}

	}
	private TimeConvert(int y, int mon, int d, int h, int m, int s){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = String.format("%04d-%02d-%02d %02d:%02d:%02d", y, mon, d, h, m, s);
		try {
			Date date = sdf.parse(dateString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			seconds = cal.getTimeInMillis() / 1000;
			secondRemain = cal.getTimeInMillis() / 1000.0 - (int)(cal.getTimeInMillis() / 1000.0);
		} catch (ParseException e) {
			e.printStackTrace();
		}	
	}
	
	/**GPS转UTC字符串*/
	public String getUtcStrFromGps(){
		String timeString = null;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(seconds * 1000);
		timeString = String.format(" %04d %02d %02d %02d %02d %.7f", 
				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, 
				cal.get(Calendar.DATE), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), 
				cal.get(Calendar.SECOND) + (cal.get(Calendar.MILLISECOND)/1000.0 + secondRemain));
		return timeString;
	}	
	
	/**UTC字符串转GPS时间*/
	public  double getGpsFromUtcStr(String timeString){
		String[] items = timeString.split("\\.");
		long temp = Long.parseLong(items[1]);
		secondRemain = (1.0 / (Math.pow(10, items[1].length()))) * temp;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HH mm ss");
		Calendar cal = Calendar.getInstance();
		try {
			Date date = sdf.parse(items[0]);
			cal.setTime(date);
			cal.add(Calendar.MILLISECOND, (int)(secondRemain * 1000));
			seconds = cal.getTimeInMillis() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		double sec =  seconds+secondRemain;
		double sect = sec  - GnssConstants.UNIX_GPS_DAYS_DIFF * GnssConstants.SEC_IN_DAY;
		double sectt  =  sect%(GnssConstants.DAYS_IN_WEEK * GnssConstants.SEC_IN_DAY);
		return (sectt + GnssConstants.SEC_IN_HOUR*8)*1000;
	}
	
	/**转换成日期*/
	public  Calendar ToCalendar(){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(seconds * 1000 + (int)(secondRemain * 1000));
		return cal;
	}

	public long getSeconds() {
		return seconds;
	}
	
	public double getSecondRemain() {
		return secondRemain;
	}

	public void setSecondRemain(double secondRemain) {
		this.secondRemain = secondRemain;
	}
	
}
