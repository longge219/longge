package com.longge.gather.gnss.common.model;
import lombok.Data;
/**
 * @description 星历数据
 * @author jianglong
 * @create 2018-06-22
 **/
@Data
public class EphemerisData {
	
   public EphemerisData(char satelliteType, int satelliteId) {
		super();
		this.satelliteType = satelliteType;
		this.satelliteId = satelliteId;
	}

	/**卫星类型*/
	private char satelliteType;
	
	                                                            
	/**卫星号*/
	private int satelliteId;
	                                                                  
	          																/**GPS星历数据*/
	/**
	 * DF076--GPS周数(0week~1023week)
	 * 表示 GPS 周数，起算于 1980 年 1 月 5 日子夜，每 1024 周一个循环。
	 * */
	private int gpsCirNum;
	
	/**
	 * DF077--GPS URA
	 * 表示 GPS 卫星的用户等效距离精度，单位 m。
	 * */
	private int gpsUra;
	
	/**
	 * DF078--GPS L2 测距码标志
	 * 表示所观测的 GPS L2 测距码类型。
	 *  0=保留； 1=P 码；2=C/A 码；3=L2C 码
	 * */
	private int gpsL2PseudorangeFlag;
	
	/**
	 * DF079--GPS IDOT
	 * 表示 GPS 卫星轨道倾角变化率，单位 π/s
	 * */
	private double gpsIdot;
	
	/**
	 * DF071--GPS IODE
	 * 来自广播星历，表示 GPS 卫星星历数据期号
	 * */
	private int gpsIode;
	
	/**
	 * DF081--GPS toc
	 * 表示 GPS 卫星钟参考时刻，单位 s
	 * */
	private double gpsToc;
	
	/**
	 * DF082--GPS af2
	 * 表示 GPS 卫星钟钟漂改正参数，单位 s/s2
	 * */
	private double gpsAf2;
	
	/**
	 * DF083--GPS af1
	 * 表示 GPS 卫星钟钟速改正参数，单位 s/s
	 * */
	private double gpsAf1;
	
	/**
	 * DF084--GPS af0
	 * 表示 GPS 卫星钟钟差改正参数，单位 s
	 * */
	private double gpsAf0;
	
	/**
	 * DF085--GPS IODC(0~1023)
	 * 表示 GPS 卫星钟参数期卷号，低 8 位与 IODE 相同
	 * */
	private int gpsIodc;
	
	/**
	 * DF086--GPS Crs
	 * 表示 GPS 卫星轨道半径正弦调和改正项的振幅，单位 m。
	 * */
	private double gpsCrs;
	
	/**
	 * DF087--GPSΔn
	 * 表示 GPS 卫星平均运动速率与计算值之差，单位 π/s
	 * */
	private double gpsDelataN;
	
	/**
	 * DF088--GPS M0
	 * 表示 GPS 卫星参考时间的平近点角，单位 π
	 * */
	private double gpsMo;
	
	/**
	 * DF089--GPS Cuc 
	 * 表示 GPS 卫星纬度幅角的余弦调和改正项的振幅，单位 rad
	 * */
	private double gpsCuc;
	
	/**
	 * DF090--GPS e
	 * 表示 GPS 卫星轨道偏心率，无单位
	 * */
	private double gpsE;
	
	/**
	 * DF091--GPS Cus
	 * 表示 GPS 卫星纬度幅角的正弦调和改正项的振幅，单位 rad
	 * */
	private double gpsCus;
	
	/**
	 * DF092--GPS a1/2
	 * 表示 GPS 卫星轨道长半轴的平方根 单位 m1/2
	 * */
	private double gpsA;
	
	/**
	 * DF093--GPS toe
	 * 表示 GPS 卫星星历参考时间，单位 s
	 * */
	private double gpsToe;
	
	/**
	 * DF094--GPS Cic
	 * 表示 GPS 卫星轨道倾角的余弦调和改正项的振幅，单位 rad
	 * */
	private double gpsCic;
	
	
	/**
	 * DF095--GPS Ω0 
	 * 表示 GPS 卫星按参考时间计算的升交点赤经，单位 π
	 * */
	private double gpsOmega0;
	
	/**
	 * DF096--GPS Cis 
	 * 表示 GPS 卫星轨道倾角的正弦调和改正项的振幅，单位 rad
	 * */
	private double gpsCis;
	
	/**
	 * DF097--GPS i0
	 * 表示 GPS 卫星参考时间的轨道倾角，单位 π。
	 * */
	private double gpsI0;
	
	/**
	 * DF098--GPS Crc
	 * 表示 GPS 卫星轨道半径的余弦调和改正项的振幅，单位 m
	 * */
	private double gpsCrc;
	
	/**
	 * DF099--GPS ω
	 * 表示 GPS 卫星近地点幅角，单位 π
	 * */
	private double gpsOmega;
	
	/**
	 * DF100--GPS OMEGADOT
	 * 表示 GPS 卫星升交点赤经变化率，单位 π/s
	 * */
	private double gpsOmegadot;
	
	/**
	 * DF101--GPS tGD
	 * 表示 GPS 卫星 L1 和 L2 信号频率的群延迟差，单位 s
	 * */
	private double gpsTgd;
	
	/**
	 * DF102--GPS健康状态
	 * MSB： 0=所有导航数据正常； 1=某些或所有导航数据不正常
	 * */
	private int gpsHealth;
	
	/**
	 * DF103--GPS L2 P
	 * 0=L2 P 码导航电文可用； 1=L2 P 码导航电文不可用
	 * */
	private int gpsL2p;
	
	/**
	 * DF137--GPS 拟合间隔
	 * 0=曲线拟合间隔为 4 小时； 1=曲线拟合间隔大于 4 小时
	 * */
	private int gpsMatchInterval;
	
																				/**BDS星历数据*/
	/**
	 * DF484 表示 BDT 周数，起始于 2006 年 1 月 1 日 UTC 0 点
	 * */
	private int bdsCirnum;
	
	/***
	 * DF485 表示 BDS 卫星的用户距离精度（URA）指数，无单位，见
     * BDS-SIS-ICD-2.0 5.2.4.5。BDS URA 可以按照下式计算：
     * 当 0≤DF485<6 时，BDS URA =2DF485/2+1；
     * 当 6≤DF485<15 时，BDS URA =2DF485-2；
     * 当 DF485 = 15 时，数值无效。
	 * */
	private int bdsUra;
	
	/**
	 * DF487 表示 BDS 卫星轨道倾角变化率，单位 π/s
	 * */
	private double bdsIdot;
	
	/**
	 * DF488 表示 BDS 卫星星历数据龄期，见 BDS-SIS-ICD-2.0 5.2.4.11。
	 * */
	private int bdsAdode;
	
	/**
	 * DF489 表示 BDS 卫星钟数据参考时刻，单位 s。
	 * */
	private double bdsToc;
	
	/**
	 * DF490 表示 BDS 卫星钟钟漂改正参数，单位 s/s2。见 BDS-SIS-ICD-2.0 5.2.4.10。
	 * */
	private double bdsA2;
	
	/**
	 * DF491 表示 BDS 卫星钟钟速改正参数，单位 s/s。见 BDS-SIS-ICD-2.0 5.2.4.10
	 * */
	private double bdsA1;
	
	/**
	 * DF492 表示 BDS 卫星钟钟差改正参数，单位 s。见 BDS-SIS-ICD-2.0 5.2.4.10
	 * */
	private double bdsA0;
	
	/**
	 * DF493 表示 BDS 卫星钟时钟数据龄期，无单位。见 BDS-SIS-ICD-2.0 5.2.4.9。
	 * */
	private int bdsAodc;
	
	/**
	 * DF494 表示 BDS 卫星轨道半径正弦调和改正项的振幅，单位 m。
	 * */
	private double bdsCrs;
	
	/**
	 * DF495 表示 BDS 卫星平均运动速率与计算值之差，单位 π/s。
	 * */
	private double bdsDeltan;
	
	/**
	 * DF496 表示 BDS 卫星参考时间的平近点角，单位 π。
	 * */
	private double bdsM0;
	
	/**
	 * DF497 表示 BDS 卫星纬度幅角的余弦调和改正项的振幅，单位 rad。
	 * */
	private double bdsCuc;
	
	/**
	 * DF498 表示 BDS 卫星轨道偏心率，无单位。
	 * */
	private double bdsE;
	
	/**
	 * DF499 表示 BDS 卫星纬度幅角的正弦调和改正项的振幅，单位 rad。
	 * */
	private double bdsCus;
	
	/**
	 * DF500 表示 BDS 卫星轨道长半轴的平方根 单位 m1/2。
	 * */
	private double bdsSqrta;
	
	/**
	 * DF501 表示 BDS 卫星星历数据参考时刻，单位 s。
	 * */
	private double bdsToe;
	
	/**
	 * DF502 表示 BDS 卫星轨道倾角的余弦调和改正项的振幅，单位 rad。
	 * */
	private double bdsCic;
	
	/**
	 * DF503 表示 BDS 卫星按参考时间计算的升交点赤经，单位 π
	 * */
	private double bdsOmega0;
	
	/**
	 * DF504 表示 BDS 卫星轨道倾角的正弦调和改正项的振幅，单位 rad。
	 * */
	private double bdsCis;
	
	/**
	 * DF505 表示 BDS 卫星参考时间的轨道倾角，单位 π。
	 * */
	private double bdsI0;
	
	/**
	 * DF506 表示 BDS 卫星轨道半径的余弦调和改正项的振幅，单位 m。
	 * */
	private double bdsCrc;
	
	/**
	 * DF507 表示 BDS 卫星近地点幅角，单位 π。
	 * */
	private double bdsMinorOmega;
	
	/**
	 * DF508 表示 BDS 卫星升交点赤经变化率，单位 π/s。
	 * */
	private double bdsOmegaDot;
	
	/**
	 * DF509 表示 BDS 卫星 B1I 星上设备时延差，单位 ns。见 BDS-SIS-ICD-2.0 5.2.4.8。
	 * */
	private double bdsTgd1;
	
	/**
	 * DF510 表示 BDS 卫星 B2I 星上设备时延差，单位 ns。见 BDS-SIS-ICD-2.0 5.2.4.8。
	 * */
	private double bdsTgd2;
	
	/**
	 * DF511 表示 BDS 卫星健康信息，见 BDS-SIS-ICD-2.0 5.2.4.16。第 9 位 MSB：0=所有导航数据正常；1=某些或所有导航数据不正常。
	 * */
	private int bdsSatHealth;

	/**
	 * BDS 卫星自主健康状态
	 * */
	private int bdsSatSelfHealth;

}
