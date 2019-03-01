package com.longge.gather.gnss.common.protocal.rtcm32.assistoperate.ephemeris;
import com.longge.gather.gnss.common.protocal.rtcm32.head.ProtocolHead;
import com.longge.gather.gnss.common.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description BDS卫星星历数据电文1046
 * @author jianglong
 * @create 2018-06-14
 **/
@Data
public class BDSEphemeris_1046 implements ProtocolHead {

	@Override
	public int getProtocolHead() {
		return 1046;
	}
	/**
	 * DF460 表示 BDS 卫星号，DF460=0 表示 64 号卫星
	 * */
	@SubAnnotation(type = "uint",startPos = 12, len = 6, mark="",className="")
	private int BDS_sateliteID;
	
	/**
	 * DF484 表示 BDT 周数，起始于 2006 年 1 月 1 日 UTC 0 点
	 * */
	@SubAnnotation(type = "uint",startPos = 18, len = 13, mark="",className="")
	private int BDS_Cirnum;
	
	/***
	 * DF485 表示 BDS 卫星的用户距离精度（URA）指数，无单位，见
     * BDS-SIS-ICD-2.0 5.2.4.5。BDS URA 可以按照下式计算：
     * 当 0≤DF485<6 时，BDS URA =2DF485/2+1；
     * 当 6≤DF485<15 时，BDS URA =2DF485-2；
     * 当 DF485 = 15 时，数值无效。
	 * */
	@SubAnnotation(type = "int",startPos = 31, len = 4, mark="",className="")
	private int BDS_Urai;
	
	/**
	 * DF487 表示 BDS 卫星轨道倾角变化率，单位 π/s
	 * */
	@SubAnnotation(type = "int",startPos = 35, len = 14, mark="",className="")
	private int BDS_IDOT;
	
	/**
	 * DF488 表示 BDS 卫星星历数据龄期，见 BDS-SIS-ICD-2.0 5.2.4.11。
	 * */
	@SubAnnotation(type = "uint",startPos = 49, len = 5, mark="",className="")
	private int BDS_ADODE;
	
	/**
	 * DF489 表示 BDS 卫星钟数据参考时刻，单位 s。
	 * */
	@SubAnnotation(type = "uint",startPos = 54, len = 17, mark="",className="")
	private int BDS_toc;
	
	/**
	 * DF490 表示 BDS 卫星钟钟漂改正参数，单位 s/s2。见 BDS-SIS-ICD-2.0 5.2.4.10。
	 * */
	@SubAnnotation(type = "int",startPos = 71, len = 11, mark="",className="")
	private int BDS_a2;
	
	/**
	 * DF491 表示 BDS 卫星钟钟速改正参数，单位 s/s。见 BDS-SIS-ICD-2.0 5.2.4.10
	 * */
	@SubAnnotation(type = "int",startPos = 82, len = 22, mark="",className="")
	private int BDS_a1;
	
	/**
	 * DF492 表示 BDS 卫星钟钟差改正参数，单位 s。见 BDS-SIS-ICD-2.0 5.2.4.10
	 * */
	@SubAnnotation(type = "int",startPos = 104, len = 24, mark="",className="")
	private int BDS_a0;
	
	/**
	 * DF493 表示 BDS 卫星钟时钟数据龄期，无单位。见 BDS-SIS-ICD-2.0 5.2.4.9。
	 * */
	@SubAnnotation(type = "uint",startPos = 128, len = 5, mark="",className="")
	private int BDS_AODC;
	
	/**
	 * DF494 表示 BDS 卫星轨道半径正弦调和改正项的振幅，单位 m。
	 * */
	@SubAnnotation(type = "int",startPos = 133, len = 18, mark="",className="")
	private int BDS_Crs;
	
	/**
	 * DF495 表示 BDS 卫星平均运动速率与计算值之差，单位 π/s。
	 * */
	@SubAnnotation(type = "int",startPos = 151, len = 16, mark="",className="")
	private int BDS_Deltan;
	
	/**
	 * DF496 表示 BDS 卫星参考时间的平近点角，单位 π。
	 * */
	@SubAnnotation(type = "int",startPos = 167, len = 32, mark="",className="")
	private long BDS_M0;
	
	/**
	 * DF497 表示 BDS 卫星纬度幅角的余弦调和改正项的振幅，单位 rad。
	 * */
	@SubAnnotation(type = "int",startPos = 199, len = 18, mark="",className="")
	private int BDS_Cuc;
	
	/**
	 * DF498 表示 BDS 卫星轨道偏心率，无单位。
	 * */
	@SubAnnotation(type = "ulong",startPos = 217, len = 32, mark="",className="")
	private long BDS_e;
	
	/**
	 * DF499 表示 BDS 卫星纬度幅角的正弦调和改正项的振幅，单位 rad。
	 * */
	@SubAnnotation(type = "int",startPos = 249, len = 18, mark="",className="")
	private int BDS_Cus;
	
	/**
	 * DF500 表示 BDS 卫星轨道长半轴的平方根 单位 m1/2。
	 * */
	@SubAnnotation(type = "ulong",startPos = 267, len = 32, mark="",className="")
	private long BDS_sqrta;
	
	/**
	 * DF501 表示 BDS 卫星星历数据参考时刻，单位 s。
	 * */
	@SubAnnotation(type = "uint",startPos = 299, len = 17, mark="",className="")
	private int BDS_toe;
	
	/**
	 * DF502 表示 BDS 卫星轨道倾角的余弦调和改正项的振幅，单位 rad。
	 * */
	@SubAnnotation(type = "int",startPos = 316, len = 18, mark="",className="")
	private int BDS_Cic;
	
	/**
	 * DF503 表示 BDS 卫星按参考时间计算的升交点赤经，单位 π
	 * */
	@SubAnnotation(type = "int",startPos = 334, len = 32, mark="",className="")
	private int BDS_Omega0;
	
	/**
	 * DF504 表示 BDS 卫星轨道倾角的正弦调和改正项的振幅，单位 rad。
	 * */
	@SubAnnotation(type = "int",startPos = 366, len = 18, mark="",className="")
	private int BDS_Cis;
	
	/**
	 * DF505 表示 BDS 卫星参考时间的轨道倾角，单位 π。
	 * */
	@SubAnnotation(type = "int",startPos = 384, len = 32, mark="",className="")
	private int BDS_i0;
	
	/**
	 * DF506 表示 BDS 卫星轨道半径的余弦调和改正项的振幅，单位 m。
	 * */
	@SubAnnotation(type = "int",startPos = 416, len = 18, mark="",className="")
	private int BDS_Crc;
	
	/**
	 * DF507 表示 BDS 卫星近地点幅角，单位 π。
	 * */
	@SubAnnotation(type = "int",startPos = 434, len = 32, mark="",className="")
	private int BDS_minorOmega;
	
	/**
	 * DF508 表示 BDS 卫星升交点赤经变化率，单位 π/s。
	 * */
	@SubAnnotation(type = "int",startPos = 466, len = 24, mark="",className="")
	private int BDS_OmegaDOT;
	
	/**
	 * DF509 表示 BDS 卫星 B1I 星上设备时延差，单位 ns。见 BDS-SIS-ICD-2.0 5.2.4.8。
	 * */
	@SubAnnotation(type = "int",startPos = 490, len = 10, mark="",className="")
	private int BDS_TGD1;
	
	/**
	 * DF510 表示 BDS 卫星 B2I 星上设备时延差，单位 ns。见 BDS-SIS-ICD-2.0 5.2.4.8。
	 * */
	@SubAnnotation(type = "int",startPos = 500, len = 10, mark="",className="")
	private int BDS_TGD2;
	
	/**
	 * DF511 表示 BDS 卫星健康信息，见 BDS-SIS-ICD-2.0 5.2.4.16。第 9 位 MSB：0=所有导航数据正常；1=某些或所有导航数据不正常。
	 * */
	@SubAnnotation(type = "String",startPos = 510, len = 9, mark="",className="")
	private String BDS_SatHealth;

	/**
	 * BDS 卫星自主健康状态
	 * */
	@SubAnnotation(type = "String",startPos = 519, len = 1, mark="",className="")
	private String BDS_SatSelfHealth;
	
	/**
	 * 保留
	 * */
	@SubAnnotation(type = "String",startPos = 520, len = 16, mark="",className="")
	private String reserve;
}
