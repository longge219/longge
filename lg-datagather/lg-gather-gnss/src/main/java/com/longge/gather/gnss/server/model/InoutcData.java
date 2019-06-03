package com.longge.gather.gnss.server.model;
import lombok.Data;
/**
 * @description 武汉导航院电离层模型改正参数和UTC时间参数
 * @author jianglong
 * @create 2018-08-22
 **/
@Data
public class InoutcData{
	
	private byte timeStatus;//gps时间质量
	
	private int gpsWeekNum;//gps周数
	
	private long ms;//从GPS第一周开始数量

	private int  bd2LeapSecond;//北斗2跳秒数

	private double alp0;//Alpha parameter constant term
	
	private double alp1;//Alpha parameter 1st order term
	
	private double alp2;//Alpha parameter 2st order term
	
	private double alp3;//Alpha parameter 3st order term
	
	private double b0;//Beta parameter constant term
	
	private double b1;//Beta parameter 1st order term
	
	private double b2;//Beta parameter 2st order term
	
	private double b3;//Beta parameter 3st order term
	
	private long utcWn;//UTC reference week number
	
	private long tot;//Reference time of UTC parameters
	
	private double a0;//UTC constant term of polynomial 
	
	private double a1;//UTC 1st order term of polynomial 
	
	private long wnlsf;//Future week number
	
	private long dn;//Day number (the range is 1 to 7 where Sunday = 1 and Saturday = 7) 
	
	private int deltatls ;//Delta time due to leap seconds
	
	private int deltatlsf  ;//Future delta time due to leap seconds
	
	private long deltatUtc ;//Time difference 
	
}
