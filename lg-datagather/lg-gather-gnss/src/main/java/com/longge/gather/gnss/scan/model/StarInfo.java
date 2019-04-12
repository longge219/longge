package com.longge.gather.gnss.scan.model;
import lombok.Data;
/**
 * @Description:观测卫星信息
 * @create Author:jianglong
 * @create 2018-10-30
 */
@Data
public class StarInfo {

	//卫星号
	private byte ID;
	
	//频点号--1:L1  2:L2  3:L5  4：B1  5:B2  6:B3  7:G1  8:G2 
	private byte Freq;
	
	//伪距(单位:米)
	private double PR;
	
	//载波相位(单位:周)
	private double Phase;
	
	//多普列(HZ)
	private double Doppler;
	
	//信噪比
	private double SNR;
	
	//卫星失锁标志0-7
	private byte lli;
	
	//0:卫星俯仰角(度)  1:方位角(度)
	private double[] El;
	
	//卫星坐标(米)
	private double[] XYZ;
	
	//卫星XYZ速度(米/秒)
	private  double[]  Vel;
	
	//电离层延迟
	double  Iono;
}
