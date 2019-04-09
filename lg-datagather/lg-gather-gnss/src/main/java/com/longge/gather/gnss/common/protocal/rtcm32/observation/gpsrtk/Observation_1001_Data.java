package com.longge.gather.gnss.common.protocal.rtcm32.observation.gpsrtk;
import com.longge.gather.gnss.server.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description GPS RTK 观测值电文1001数据体
 * @author jianglong
 * @create 2018-04-02
 **/
@Data
public class Observation_1001_Data {
	
	/**
	 * 1~32 是指 GPS 卫星的 PRN 号。大于 32 的卫星号为 SBAS 所保留,例如 FAA 的广域增强系统（WAAS）。
	 * SBAS 的 PRN 号范围是 120~138。为
     * SBAS 卫星预留的卫星号范围是 40~58， 所以 SBAS 的 PRN 号是由卫星号加上80 得到的。
	 * */
	@SubAnnotation(type = "uint",startPos = 64, len = 6, mark="byStarNum",className="")
	private int gpsStarID;//GPS 卫星号
	
	/**
	 * 用于识别参考站跟踪到的测距码类型。
	 * 民用接收机能跟踪 C/A 码，有的可以跟踪 P 码。军用接收机能跟踪 C/A 码，也能跟踪 P 码和 Y 码。
     * 0=C/A 码； 1=P（Y）码。
	 */
	@SubAnnotation(type = "boolean",startPos = 70, len = 1, mark="byStarNum",className="")
	private boolean gpsL1Flag;//GPS L1码标志
	
	/**
	 * DF011以米级精度提供参考站到卫星的 GPS L1 伪距观测值余数，它是 GPS L1原始伪距观测值与光毫秒（299,792.458 米）进行模运算后的结果。
	 * 用户接收机中应按以下方法重建 GPS L1 原始伪距观测值：
     * GPS L1 原始伪距观测值=（DF011） mod（299,792.458m） +从用户接收机估计的参考站距离的光毫秒整数×（299,792.458m）（或由扩展数据提供的整数）。
     * 80000h（十六进制） = 无效的 L1 伪距，仅在 L2 观测值计算时使用。
	 * */
	@SubAnnotation(type = "uint",startPos = 71, len = 24, mark="byStarNum",className="")
	private int gpsL1PreDistance;//GPS L1 伪距
	
	/**
	 *  DF012提供确定 L1 载波相位观测值必需的信息。注意这里定义的载波距离与伪距符号相同。载波距离比伪距精度高很多，因此定义 DF012
	 *  的目的是为了压缩电文长度。在周跳发生时，会重置并重新搜索初始模糊度，因此 L1 载波距离应尽量与 L1 伪距相近（比如，在 1/2 L1 周内)
     *  同时不破坏 L1 载波观测值中模糊度的整周特性。可按以下方法重建完整的 GPS L1 载波距离，参数单位为 m：
     * （完整的 L1 载波距离） =（从 DF011 重建的 L1 伪距） + DF012。
     *   某些电离层环境可能引起 DF012 超过允许范围，此时，应将超限数值进行 1500周的调整（视数值情况加或减），以使 DF012 符合定义范围。
     *  关于网络 RTK 应用中 PCV 改正的说明。DF012=80000h（十六进制）表明 L1 伪距无效。
	 * */
	@SubAnnotation(type = "int",startPos = 95, len = 20, mark="byStarNum",className="")
	private int gpsL1CarrierPreDistance;//GPS L1 载波距离-L1 伪距
	
	/**
	 *DF013 提供参考站接收机连续锁定卫星信号的时间长度。 如果观测值周跳探测中发现周跳， DF013 将复位为 0。
	 * */
	@SubAnnotation(type = "uint",startPos = 115,len = 7, mark="byStarNum",className="")
	private int gpsL1LockTimeFlag;//GPS L1 锁定时间标志
	
}
