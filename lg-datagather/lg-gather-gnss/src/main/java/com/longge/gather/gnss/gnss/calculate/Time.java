package com.longge.gather.gnss.gnss.calculate;
/**
 * Description:时间系统转换对象
 * User: jianglong
 * Date: 2018年5月22号
 */
public class Time {

	
	//GPS时间正秒数
	private long  secNum; 
	
	//GPS时间秒小数
	private double secFraction;
	
	//跳秒数
	private long leapSeconds = 0;
	
	public Time(long secNum, double secFraction) {
		super();
		this.secNum = secNum;
		this.secFraction = secFraction;
	}

	public long getSecNum() {
		return secNum;
	}

	public void setSecNum(long secNum) {
		this.secNum = secNum;
	}

	public double getSecFraction() {
		return secFraction;
	}

	public void setSecFraction(double secFraction) {
		this.secFraction = secFraction;
	}

	public long getLeapSeconds() {
		return leapSeconds;
	}

	public void setLeapSeconds(long leapSeconds) {
		this.leapSeconds = leapSeconds;
	} 

}
