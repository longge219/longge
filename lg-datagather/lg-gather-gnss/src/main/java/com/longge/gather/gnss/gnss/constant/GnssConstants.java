package com.longge.gather.gnss.gnss.constant;
/**
 * Description:GNSS常量
 * User: jianglong
 * Date: 2018年7月11号
 */
public class GnssConstants {
	// 光速 单位m/s
	public static final double SPEED_OF_LIGHT = 299792458.0;
	//地心引力GM
	public static final double EARTH_GRAVITATIONAL_CONSTANT = 3.986005e14;
	//自转角速度W
	public static final double EARTH_ANGULAR_VELOCITY = 7.2921151467e-5; 
	//相对论常数
	public static final double RELATIVISTIC_ERROR_CONSTANT = -4.442807633e-10; 
	//gps信号传输时间
	public static final double GPS_APPROX_TRAVEL_TIME = 0.072;
	// 长轴
	public static final double WGS84_SEMI_MAJOR_AXIS = 6378137;
	//扁率
	public static final double WGS84_FLATTENING = 1 / 298.257222101;
	//离心率
	public static final double WGS84_ECCENTRICITY = Math.sqrt(1 - Math.pow((1 - WGS84_FLATTENING), 2));
	//时间常量
	public static final long DAYS_IN_WEEK = 7L;
	public static final long SEC_IN_DAY = 86400L;
	public static final long SEC_IN_HOUR = 3600L;
	public static final long MILLISEC_IN_SEC = 1000L;
	public static final long SEC_IN_HALF_WEEK = 302400L;
	// UNIX time和GPS time差异 
	public static final long UNIX_GPS_DAYS_DIFF = 3657L;
	//标准气压
	public static final double STANDARD_PRESSURE = 1013.25;
	//标准温度
	public static final double STANDARD_TEMPERATURE = 291.15;

	//用信噪比来衡量观测值的参数
	public static final float SNR_a = 30;
	public static final float SNR_A = 30;
	public static final float SNR_0 = 10;
	public static final float SNR_1 = 50;
	
	//GPS 频率
	public static final double FL1 = 1575.420e6; 
	public static final double FL2 = 1227.600e6;
	public static final double FL5 = 1176.450e6;
     
	//GPS波长
	public static final double GPS_L1_WAVELENGTH = SPEED_OF_LIGHT / FL1;
	public static final double GPS_L2_WAVELENGTH = SPEED_OF_LIGHT / FL2;
	public static final double GPS_L5_WAVELENGTH = SPEED_OF_LIGHT / FL5;
	
	// GLONASS 频率
	public static final double FR1_base = 1602.000e6; 
	public static final double FR2_base = 1246.000e6;
	public static final double FR1_delta = 0.5625;
	public static final double FR2_delta = 0.4375;
	
	// Galileo 频率
	public static final double FE1  = FL1; 
	public static final double FE5a = FL5;
	public static final double FE5b = 1207.140e6;
	public static final double FE5  = 1191.795e6;
	public static final double FE6  = 1278.750e6;
	
	//BeiDou频率
	public static final double FC1  = 1589.740e6; 
	public static final double FC2  = 1561.098e6;
	public static final double FC5b = FE5b;
	public static final double FC6  = 1268.520e6;
	
	//BDS波长
	public static final double BDS_B1_WAVELENGTH = SPEED_OF_LIGHT / FC2;
	public static final double BDS_B2_WAVELENGTH = SPEED_OF_LIGHT / FC5b;
	public static final double BDS_B3_WAVELENGTH = SPEED_OF_LIGHT / FC6;
	
	//QZSS频率
	public static final double FJ1  = FL1; 
	public static final double FJ2  = FL2;
	public static final double FJ5  = FL5;
	public static final double FJ6  = FE6;

	//椭球半轴
	public static final long ELL_A_GPS = 6378137;
	public static final long ELL_A_GLO = 6378136;
	public static final long ELL_A_GAL = 6378137;
	public static final long ELL_A_BDS = 6378136;
	public static final long ELL_A_QZS = 6378137; 
    
	//椭球扁率
	public static final double ELL_F_GPS = 1/298.257222101;
	public static final double ELL_F_GLO = 1/298.257222101;
	public static final double ELL_F_GAL = 1/298.257222101; 
	public static final double ELL_F_BDS = 1/298.257222101;
	public static final double ELL_F_QZS = 1/298.257222101; 
    
	//离心率
	public static final double ELL_E_GPS = Math.sqrt(1-(1- Math.pow(ELL_F_GPS, 2)));
	public static final double ELL_E_GLO = Math.sqrt(1-(1- Math.pow(ELL_F_GLO, 2))); 
	public static final double ELL_E_GAL = Math.sqrt(1-(1- Math.pow(ELL_F_GAL, 2)));
	public static final double ELL_E_BDS = Math.sqrt(1-(1- Math.pow(ELL_F_BDS, 2)));
	public static final double ELL_E_QZS = Math.sqrt(1-(1- Math.pow(ELL_F_QZS, 2)));
    
	//引力
	public static final double GM_GPS = 3.986005e14; 
	public static final double GM_GLO = 3.9860044e14; 
	public static final double GM_GAL = 3.986004418e14;
	public static final double GM_BDS = 3.986004418e14; 
	public static final double GM_QZS = 3.986005e14;
    
	//地球自转角速度
	public static final double OMEGAE_DOT_GPS = 7.2921151467e-5;
	public static final double OMEGAE_DOT_GLO = 7.292115e-5;
	public static final double OMEGAE_DOT_GAL = 7.2921151467e-5; 
	public static final double OMEGAE_DOT_BDS = 7.292115e-5;
	public static final double OMEGAE_DOT_QZS = 7.2921151467e-5;
    
	//GLONASS的第二次纬向谐波
	public static final double J2_GLO = 1.0826257e-3;
    //用于轨道计算的pi值
	public static final double PI_ORBIT = 3.1415926535898;
	//用于轨道计算的2pi值
	public static final double CIRCLE_RAD = 2 * PI_ORBIT;
	//开普勒误差数
	public static double RTOL_KEPLER = 1E-14;
}
