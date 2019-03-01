package com.longge.gather.gnss.gnss.model;
import com.longge.gather.gnss.gnss.calculate.Time;
import lombok.Data;
/**
 * Description:GPS星历
 * User: jianglong
 * Date: 2018年7月11号
 */
@Data
public class EphGps {

	private Time refTime;
	
	private char satType; 
	
	private int satID; 
	
	private int week; 

	private int L2Code; 

	private int L2Flag; 

	private int svAccur; 
	
	private int svHealth; 

	private int iode;
	
	private int iodc;

	private double toc;
	
	private double toe;
	
	private double tom;

	private double af0;
	
	private double af1;
	
	private double af2;
	
	private double tgd;

	private double rootA; 
	
	private double e; 
	
	private double i0; 
	
	private double iDot; 
	
	private double omega; 
	
	private double omega0; 
	
	private double omegaDot; 
	
	private double M0; 
	
	private double deltaN; 
	
	private double crc, crs, cuc, cus, cic, cis; 
	
	private double fitInt;

	private float tow;

	private float tauN;
	
	private float gammaN;
	
	private double tk;

	private double X;
	private double Xv;
	private double Xa;
	private double Bn;

	private double Y;
	private double Yv;
	private double Ya;
	private int freq_num;
	private double tb;

	private double Z;
	private double Zv;
	private double Za;
	private double En;

    public static final EphGps UnhealthyEph = new EphGps();

}
