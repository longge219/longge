package com.longge.gather.gnss.common.protocal.rtcm32.arp;
import com.longge.gather.gnss.common.protocal.rtcm32.head.ProtocolHead;
import com.longge.gather.gnss.server.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description 固定天线参考点电文1005
 * @author jianglong
 * @create 2018-06-22
 **/
@Data
public class Arp_1005 implements ProtocolHead {

	@Override
	public int getProtocolHead() {
		return 1005;
	}
	/**
	 * 参考站ID
	 * */
	@SubAnnotation(type = "uint",startPos = 12, len = 12, mark="",className="")
	private int rfsID;
	
	/**
	 * DF021 ITRF实现年代
	 * 表示坐标框架定义并实现的年份。DF021 为保留字段，所有比特位置 0。
     *  但未来的新版本可能会改变其定义，所以应根据解码赋值而不应直接赋 0 值。
	 * */
	@SubAnnotation(type = "uint",startPos = 24, len = 6, mark="",className="")
	private int itrfTime;
	
	/**
	 * DF022 GPS 标志
	 * 0=没有 GPS 服务支持；1=有 GPS 服务支持。
	 * */
	@SubAnnotation(type = "boolean",startPos = 30, len = 1, mark="",className="")
	private boolean gpsFlag;
	
	/**
	 * DF023 GLONASS 标志
	 * 0=没有 GLONASS 服务支持；1=有 GLONASS 服务支持。
	 * */
	@SubAnnotation(type = "boolean",startPos = 31, len = 1, mark="",className="")
	private boolean glonassFlag;
	
	/**
	 * DF024 Galileo 标志
	 * 0=没有 Galileo 服务支持；1=有 Galileo 服务支持。
	 * */
	@SubAnnotation(type = "boolean",startPos = 32, len = 1, mark="",className="")
	private boolean galileoFlag;
	
	/**
	 * DF141 参考站类型标志
	 * 0=物理参考站；1=非物理或计算所得的参考站。
     * 注意：非物理或计算所得的参考站是根据参考站网络提供的信息计算得到的。
     * 目前有很多建立非物理参考站的方法，某些名称已经被注册为商标，而且彼此
     * 间无法兼容。类似的方法名称有“虚拟参考站”，“伪参考站”或“定制参考站”等。
	 * */
	@SubAnnotation(type = "boolean",startPos = 33, len = 1, mark="",className="")
	private boolean observationTypeFlag;
	
	/**
	 * DF025 ARP ECEF-X(单位0.0001m)
	 * ARP 在地心地固坐标系中的 X 坐标，坐标系历元为 DF021d 规定的参考历元。
	 * */
	@SubAnnotation(type = "long",startPos = 34, len = 38, mark="",className="")
	private long arpEcefX;
	
	/**
	 * DF142 单接收机振荡器标志
	 * 0=电文类型 1001~1004 与电文类型 1009~1012 中的原始数据可能是在不同时刻观测的。除非完全满足 DF142=1 的条件，否则 DF142 应置 0；
	 * 1=电文类型 1001~1004 与电文类型 1009~1012 中的原始数据为同时观测，见6.1.5。
	 * */
	@SubAnnotation(type = "boolean",startPos = 72, len = 1, mark="",className="")
	private boolean oscillatorFlag;
	
	/**
	 * DF458 BDS标志
	 * DF458 表示电文中是否包含 BDS 信息。
	 * DF458=0 表示电文中无 BDS 信息；DF458=1 表示电文中有 BDS 信息
	 * */
	@SubAnnotation(type = "boolean",startPos = 73, len = 1, mark="",className="")
	private boolean bdsFlag;
	
	/**
	 * DF026 ARP ECEF-Y(单位0.0001m)
	 * ARP 在地心地固坐标系中的 Y 坐标，坐标系历元为 DF021 规定的参考历元。
	 * 
	 * */
	@SubAnnotation(type = "long",startPos = 74, len = 38, mark="",className="")
	private long arpEcefY;
	
	/**
	 * DF364  1/4 周标志
	 * DF364 用于说明统一频率上所跟踪的不同载波相位信号相位是否相同。即同一频率两个信号的相位距离差是否为 1/4 周（见 6.1.9）。
	 * DF364 仅反映出与 1/4周改正状态（与电文类型 1001、1002、1003、1004、1009、1010、1011、1012有关）：
     * 00=改正状态未知；
     * 01=已对电文类型 1001、1002、1003、1004、1009、1010、1011、1012 的载波距离进行了修正，不同信号之间的观测值间不在存在 1/4 周偏差；
     * 10=未对相位观测值进行改正。同一频率上不同信号的载波双差观测值可能含有 1/4 周偏差。需使用合适方法进行处理；
     * 11=保留。
	 * */
	@SubAnnotation(type = "String",startPos = 112, len = 2, mark="",className="")
	private String quarterFlag;
	
	/**
	 * DF027 ARP ECEF-Z(单位0.0001m)
	 * ARP 在地心地固坐标系中的 Z 坐标，坐标系历元为 DF021 规定的参考历元
	 * */
	@SubAnnotation(type = "long",startPos = 114, len = 38, mark="",className="")
	private long arpEcefZ;
}
