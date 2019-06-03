package com.longge.gather.gnss.server.protocal.rtcm32.assistoperate.ephemeris;
import com.longge.gather.gnss.server.protocal.rtcm32.head.ProtocolHead;
import com.longge.gather.gnss.server.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description GPS卫星星历数据电文1019
 * @author jianglong
 * @create 2018-06-14
 **/
@Data
public class GpsEphemeris_1019  implements ProtocolHead {

	@Override
	public int getProtocolHead() {
		return 1019;
	}
	
	/**
	 * GPS卫星号
	 * 1~32 是指 GPS 卫星的 PRN 号
	 * */
	@SubAnnotation(type = "uint",startPos = 12, len = 6, mark="",className="")
	private int gpsSatelliteId;
	
	/**
	 * GPS周数
	 * 表示 GPS 周数，起算于 1980 年 1 月 5 日子夜，每 1024 周一个循环
	 * */
	@SubAnnotation(type = "uint",startPos = 18, len = 10, mark="",className="")
	private int gpsCirNum;
	
	/**
	 * GPS URA
	 * 表示 GPS 卫星的用户等效距离精度，单位 m。
	 * */
	@SubAnnotation(type = "uint",startPos = 28, len = 4, mark="",className="")
	private int gpsUra;
	
	/**
	 * GPS L2 测距码标志
	 * 表示所观测的 GPS L2 测距码类型。
	 *  00=保留； 
	 *  01=P 码； 
	 *  10=C/A 码；
	 *  11=L2C 码
	 * */
	@SubAnnotation(type = "String",startPos = 32, len = 2, mark="",className="")
	private String gpsL2PseudorangeFlag;
	
	/**
	 * GPS IDOT
	 * 表示 GPS 卫星轨道倾角变化率，单位 π/s
	 * */
	@SubAnnotation(type = "int",startPos = 34, len = 14, mark="",className="")
	private int gpsIdot;
	
	/**
	 * GPS IODE
	 * 来自广播星历，表示 GPS 卫星星历数据期号
	 * */
	@SubAnnotation(type = "uint",startPos = 48, len = 8, mark="",className="")
	private int gpsIode;
	
	/**
	 * GPS toc
	 * 表示 GPS 卫星钟参考时刻，单位 s
	 * */
	@SubAnnotation(type = "uint",startPos = 56, len = 16, mark="",className="")
	private int gpsToc;
	
	/**
	 * GPS af2
	 * 表示 GPS 卫星钟钟漂改正参数，单位 s/s2
	 * */
	@SubAnnotation(type = "int",startPos = 72, len = 8, mark="",className="")
	private int gpsAf2;
	
	/**
	 * GPS af1
	 * 表示 GPS 卫星钟钟速改正参数，单位 s/s
	 * */
	@SubAnnotation(type = "int",startPos = 80, len = 16, mark="",className="")
	private int gpsAf1;
	
	/**
	 * GPS af0
	 * 表示 GPS 卫星钟钟差改正参数，单位 s
	 * */
	@SubAnnotation(type = "int",startPos = 96, len = 22, mark="",className="")
	private int gpsAf0;
	
	/**
	 * GPS IODC
	 * 表示 GPS 卫星钟参数期卷号，低 8 位与 IODE 相同
	 * */
	@SubAnnotation(type = "uint",startPos = 118, len = 10, mark="",className="")
	private int gpsIodc;
	
	/**
	 * GPS Crs
	 * 表示 GPS 卫星轨道半径正弦调和改正项的振幅，单位 m。
	 * */
	@SubAnnotation(type = "int",startPos = 128, len = 16, mark="",className="")
	private int gpsCrs;
	
	/**
	 * GPSΔn
	 * 表示 GPS 卫星平均运动速率与计算值之差，单位 π/s
	 * */
	@SubAnnotation(type = "int",startPos = 144, len = 16, mark="",className="")
	private int gpsN;
	
	/**
	 * GPS M0
	 * 表示 GPS 卫星参考时间的平近点角，单位 π
	 * */
	@SubAnnotation(type = "int",startPos = 160, len = 32, mark="",className="")
	private int gpsMo;
	
	/**
	 * GPS Cuc 
	 * 表示 GPS 卫星纬度幅角的余弦调和改正项的振幅，单位 rad
	 * */
	@SubAnnotation(type = "int",startPos = 192, len = 16, mark="",className="")
	private int gpsCuc;
	
	/**
	 * GPS e
	 * 表示 GPS 卫星轨道偏心率，无单位
	 * */
	@SubAnnotation(type = "ulong",startPos = 208, len = 32, mark="",className="")
	private long gpsE;
	
	/**
	 * GPS Cus
	 * 表示 GPS 卫星纬度幅角的正弦调和改正项的振幅，单位 rad
	 * */
	@SubAnnotation(type = "int",startPos = 240, len = 16, mark="",className="")
	private int gpsCus;
	
	/**
	 * GPS a1/2
	 * 表示 GPS 卫星轨道长半轴的平方根 单位 m1/2
	 * */
	@SubAnnotation(type = "ulong",startPos = 256, len = 32, mark="",className="")
	private long gpsA;
	
	/**
	 * GPS toe
	 * 表示 GPS 卫星星历参考时间，单位 s
	 * */
	@SubAnnotation(type = "uint",startPos = 288, len = 16, mark="",className="")
	private int gpsToe;
	
	/**
	 * GPS Cic
	 * 表示 GPS 卫星轨道倾角的余弦调和改正项的振幅，单位 rad
	 * */
	@SubAnnotation(type = "int",startPos = 304, len = 16, mark="",className="")
	private int gpsCic;
	
	
	/**
	 * GPS Ω0 
	 * 表示 GPS 卫星按参考时间计算的升交点赤经，单位 π
	 * */
	@SubAnnotation(type = "int",startPos = 320, len = 32, mark="",className="")
	private int gpsOmega0;
	
	/**
	 * GPS Cis 
	 * 表示 GPS 卫星轨道倾角的正弦调和改正项的振幅，单位 rad
	 * */
	@SubAnnotation(type = "int",startPos = 352, len = 16, mark="",className="")
	private int gpsCis;
	
	/**
	 * GPS i0
	 * 表示 GPS 卫星参考时间的轨道倾角，单位 π。
	 * */
	@SubAnnotation(type = "int",startPos = 368, len = 32, mark="",className="")
	private int gpsI0;
	
	/**
	 * GPS Crc
	 * 表示 GPS 卫星轨道半径的余弦调和改正项的振幅，单位 m
	 * */
	@SubAnnotation(type = "int",startPos = 400, len = 16, mark="",className="")
	private int gpsCrc;
	
	/**
	 * GPS ω
	 * 表示 GPS 卫星近地点幅角，单位 π
	 * */
	@SubAnnotation(type = "int",startPos = 416, len = 32, mark="",className="")
	private int gpsW;
	
	/**
	 * GPS OMEGADOT
	 * 表示 GPS 卫星升交点赤经变化率，单位 π/s
	 * */
	@SubAnnotation(type = "int",startPos = 448, len = 24, mark="",className="")
	private int gpsOmegadot;
	
	/**
	 * GPS tGD
	 * 表示 GPS 卫星 L1 和 L2 信号频率的群延迟差，单位 s
	 * */
	@SubAnnotation(type = "int",startPos = 472, len = 8, mark="",className="")
	private int gpsTgd;
	
	/**
	 * GPS健康状态
	 * MSB： 0=所有导航数据正常； 1=某些或所有导航数据不正常
	 * */
	@SubAnnotation(type = "uint",startPos = 480, len = 6, mark="",className="")
	private int gpsHealth;
	
	/**
	 * GPS L2 P
	 * 0=L2 P 码导航电文可用； 1=L2 P 码导航电文不可用
	 * */
	@SubAnnotation(type = "boolean",startPos = 486, len = 1, mark="",className="")
	private boolean gpsL2p;
	
	/**
	 * GPS 拟合间隔
	 * 0=曲线拟合间隔为 4 小时； 1=曲线拟合间隔大于 4 小时
	 * */
	@SubAnnotation(type = "boolean",startPos = 487, len = 1, mark="",className="")
	private boolean gpsMatchInterval;
}
