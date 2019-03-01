package com.longge.gather.gnss.common.protocal.rtcm32.observation.gpsrtk;
import com.longge.gather.gnss.common.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description GPS RTK 观测值电文1004数据体
 * @author jianglong
 * @create 2018-04-02
 **/
@Data
public class Observation_1004_Data {
	
	/**
	 * 1~32 是指 GPS 卫星的 PRN 号。大于 32 的卫星号为 SBAS 所保留,例如 FAA 的广域增强系统（WAAS）。
	 * SBAS 的 PRN 号范围是 120~138。为
     * SBAS 卫星预留的卫星号范围是 40~58， 所以 SBAS 的 PRN 号是由卫星号加上80 得到的。
	 * */
	@SubAnnotation(type = "byte",startPos = 64, len = 6, mark="",className="")
	private byte gpsStarID;//GPS 卫星号
	
	/**
	 * 用于识别参考站跟踪到的测距码类型。
	 * 民用接收机能跟踪 C/A 码，有的可以跟踪 P 码。军用接收机能跟踪 C/A 码，也能跟踪 P 码和 Y 码。
     * 0=C/A 码； 1=P（Y）码。
	 */
	@SubAnnotation(type = "byte",startPos = 70, len = 1, mark="",className="")
	private byte gpsL1Flag;//GPS L1码标志
	
	/**
	 * DF011以米级精度提供参考站到卫星的 GPS L1 伪距观测值余数，它是 GPS L1原始伪距观测值与光毫秒（299,792.458 米）进行模运算后的结果。
	 * 用户接收机中应按以下方法重建 GPS L1 原始伪距观测值：
     * GPS L1 原始伪距观测值=（DF011） mod（299,792.458m） +从用户接收机估计的参考站距离的光毫秒整数×（299,792.458m）（或由扩展数据提供的整数）。
     * 80000h（十六进制） = 无效的 L1 伪距，仅在 L2 观测值计算时使用。
	 * */
	@SubAnnotation(type = "int",startPos = 71, len = 24, mark="",className="")
	private byte gpsL1PreDistance;//GPS L1伪距
	
	/**
	 *  DF012提供确定 L1 载波相位观测值必需的信息。注意这里定义的载波距离与伪距符号相同。载波距离比伪距精度高很多，因此定义 DF012
	 *  的目的是为了压缩电文长度。在周跳发生时，会重置并重新搜索初始模糊度，因此 L1 载波距离应尽量与 L1 伪距相近（比如，在 1/2 L1 周内)
     *  同时不破坏 L1 载波观测值中模糊度的整周特性。可按以下方法重建完整的 GPS L1 载波距离，参数单位为 m：
     * （完整的 L1 载波距离） =（从 DF011 重建的 L1 伪距） + DF012。
     *   某些电离层环境可能引起 DF012 超过允许范围，此时，应将超限数值进行 1500周的调整（视数值情况加或减），以使 DF012 符合定义范围。
     *  关于网络 RTK 应用中 PCV 改正的说明。DF012=80000h（十六进制）表明 L1 伪距无效。
	 * */
	@SubAnnotation(type = "int",startPos = 95, len = 20, mark="",className="")
	private byte gpsL1CarrierPreDistance;//GPS L1载波距离-L1 伪距
	
	/**
	 *DF013 提供参考站接收机连续锁定卫星信号的时间长度。 如果观测值周跳探测中发现周跳， DF013 将复位为 0。
	 * */
	@SubAnnotation(type = "byte",startPos = 115,len = 7, mark="",className="")
	private byte gpsL1LockTimeFlag;//GPS L1锁定时间标志
	
	/**
	 *  DF014 表示 GPS L1 原始伪距观测值对 299,792.458m 进行模运算中所得的整数部分，即光毫秒整数。 
	 * */
	@SubAnnotation(type = "short",startPos = 122, len = 8, mark="",className="")
	private short gpsL1PreDistanceMill;//GPS L1伪距光毫秒整数
	
	/**
	 * DF015 提供参考站估计出的卫星信号载躁比，以dB-Hz 为单位。 0=未计算GPS L1 载躁比。
	 * 
	 * */
	@SubAnnotation(type = "short",startPos = 130, len = 8, mark="",className="")
	private short gpsL1Cnr;//GPS L1 CNR 
	
	/**
	 * DF016 表示电文所处理的 GPS L2 载波上的测距码类型:
    *  0=C/A 或 L2C 码；
    *  1=直捕获 P(Y)码；
    *  2=交叉相关的 P(Y)码；
    *  3=改正后的 P/Y 码。
    *  DF016 取值与 GPS 参考站重建 L2 伪距方法有关，以下假定 GPS 卫星不会同时传输 C/A 码和 L2C，且参考站和用户接收机一直使用同样的信号。
    * a) 参考站接收机使用 L2 载波上的任何民用测距码（C/A 或 L2C 码）获得L2 伪距， DF016=0；
    * b) 参考站接收机直接从 GPS 捕获 P(Y)码信号，则 DF016=1；
    * c) 参考站接收机给出的 L2 伪距观测值是由 L1 C/A 码加上交叉相关改正值（Y2-Y1）得到，则 DF016=2；
    * d) 参考站接收机以专有技术从 L2 P（Y）取得 L2 伪距，则 DF016=3。
	 * */
	@SubAnnotation(type = "byte",startPos = 138, len = 2, mark="",className="")
	private byte gpsL2Flag;//GPS L2码标志
	
	/**
	 *  DG017 设计用来压缩电文长度。用户接收机应按以下方法重建 L2 伪距观测值：
     *（GPS L2 伪距观测值） =（从 DF011 重建的 GPS L1 伪距观测值） +（DF017）
     * 2000h（十六进制）（-163.84m） =没有可用的 L2 码，或者其值超出了允许的范围。
	 * */
	@SubAnnotation(type = "int",startPos = 140, len = 14, mark="",className="")
	private byte gpsL2PreDistance;//GPS L2-L1 伪距差值
	
	/**
	 * DF018 提供获取 L2 载波相位观测值的必须信息。载波距离与伪距符号相同。
    *  载波距离比伪距精度高，因此设计 DF018 的目的是为了压缩电文长度。在周
    *  跳发生时，会重置并重新搜索初始模糊度，因此 L2 载波距离应尽量与 L1 伪
    *  距相近（比如，在 1/2 L2 周内），同时不破坏载波观测值中模糊度的整周特性。
    *  可按以下方法重建完整的 GPS L2 载波距离，所有参数单位为 m：
    *（完整的 L2 载波距离） =（从 DF011 重建的 L1 伪距） +（DF018）。
    *  某些电离层环境可能引起 DF018 超过允许范围，此时，应将超限数值进行 1500周的调整（视数值情况加或减），
    *  以使 DF018 符合定义范围。也请见 6.1.2 和 6.5.1 关于网络 RTK 应用中 PCV 改正的说明。
    *  80000h（十六进制） =载波观测值无效，不要进行处理。多是处于由于卫星信号微弱导致载波相位跟踪暂时丢失，但测距码跟踪仍然有效的情况下。
	 * */
	@SubAnnotation(type = "int",startPos = 154, len = 20, mark="",className="")
	private byte gpsL2CarrierPreDistance;//GPS L2载波距离-L1 伪距
	
	/**
	 * DF019 供参考站接收机连续锁定卫星信号的时间长度。 如果观测值周跳探测中发现周跳，则DF013 将复位为 0。见表 9。
	 * */
	@SubAnnotation(type = "byte",startPos = 174,len = 7, mark="",className="")
	private byte gpsL2LockTimeFlag;//GPS L2锁定时间标志
	
	/**
	 * DF020 提供参考站估计出的卫星信号载躁比, dB-Hz 为单位。0=未计算 GPS L2 载躁比。
	 * */
	@SubAnnotation(type = "short",startPos = 181, len = 8, mark="",className="")
	private short gpsL2Cnr;//GPS L2 CNR 
	
}
