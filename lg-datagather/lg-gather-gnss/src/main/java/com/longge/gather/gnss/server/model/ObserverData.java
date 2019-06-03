package com.longge.gather.gnss.server.model;
import lombok.Data;
/**
 * @description 卫星观测数据协议对象
 * @author jianglong
 * @create 2018-06-19
 **/
@Data
public class ObserverData {
	
	/**协议头*/
	private int protocolHead;
	
	/**参考站ID*/
	private int rfsID;
	
	/**GNSS历元时刻 ---每个系统不相同*/
	private int gnssTow;
	
	/**
	 * DF393 多电文标志 表示 MSM 后续电文情况：
     * 1=还有相对给定时刻与参考站 ID 的更多电文；
     * 0=本条电文时给定时刻与参考站 ID 的最后一条。
	 * */
	private boolean manyFlag;
	
	/**
	 *DF409 表示测站数据期卷号（Issue Of Data Station），为保留字段，用于将
	 *MSM 与今后的测站说明（接收机、天线说明等）联系起来。DF409=0 表示未使用本数据字段。
	 * */
	private int iods;
	
	/**保留--可能每个系统不同*/
	private int retain;
	
	/**
	 * DF411 时钟校准标志 表示时钟校准的情况。
     * 0=未使用时钟校准，此时，接收机钟差必须保持小于±1ms（约±300km）；
     * 1=使用了时钟校准，此时，接收机钟差必须保持小于±1 微秒（约±300m）；
     * 2=未知的时钟校准状态；
     * 3=保留。 
	 * */
	private int clockCorrectFlag;
	
	/**
	 * DF412 扩展时钟标志 表示时钟校准的情况。
     * 0=使用内部时钟；
     * 1=使用外部时钟，状态为“锁定”；
     * 2=使用外部时钟，状态为“未锁定”，表示外部时钟失效，传输的数据可能不可靠；
     * 3=使用时钟状态未知。 
	 * */
	private int exClockFlag;
	
	/**
	 * DF417 表示 GNSS 平滑类型。
	 * 1=使用非无弥散平滑；
	 * 0=其他平滑类型。
	 * */
	private boolean gnssSmoothnessTypeFlag;
	
	/**
	 * DF418 是指使用载波平滑伪距的时段长度。注意在卫星可见的整个时间段里可能连续使用非无弥散平滑。
	 * DF418=0 表明未使用平滑，数值与平滑间隔对应见表 11。
	 * */
	private int gnssSmoothnessInterval;
	
	/**
	 * DF394  GNSS 卫星掩码 给出所观测的 GNSS 卫星情况。每颗卫星对应一个比特位，
	 *  MSB 相当于 ID=1 的 GNSS 卫星，第二位相当于 ID=2 的 GNSS 卫星……，
	 *  LSB 相当于ID=64 的 GNSS 卫星。
	 * */
	private int gnssSatNum;
	
	//卫星系统名称
	private char satType;
	
	/**
	 * DF395 GNSS 信号掩码 给出了 GNSS 卫星播发信号的情况。每类信号对应一个比特位，
	 * MSB相当于 ID=1 卫星信号，第二位相当于 ID=2 的卫星信号……，
	 * LSB 相当于ID=32 的卫星信号。
	 * */
	private int gnssSigNum;
	
	                                                       
	
	                                                                                 /**卫星数据类容*/
	
	 /**
     * DF397  GNSS 卫星概略距离的整毫秒数 用于恢复某颗卫星的完整观测值。概略距离占 18 位，分为 DF397 与DF398 两个字段。
     * DF397 为卫星概略距离的整毫秒数。如果在 MSM1、MSM2和 MSM3 中未传输 DF397，
     * 那么解码设备需要根据参考站位置和星历数据恢复卫星概略距离。	
	   * DF397=FFh（255ms）表示字段无效。
	  * */
	 private int gnssSatGenDisMsInt;
	 
	/**
	 * DF398 是卫星概略距离的毫秒余数，可以 1/1024ms（约 300m）的精度恢复完整的 GNSS 概略距离。
	 * 见 DF397 的字段说明。
	 * */
	 private int gnssSatGenDisMsRem;
	 
	/**
	 * 扩展卫星信息--每个系统不同
	 * */
	 private int exSatInfo;
	 
	/**
	 * DF399  GNSS 卫星概略相位距离变化率  与相位距离导数的符号相同。
     * 与距离相似，完整的相位距离变化率观测值可通过概略相位距离变化率（对卫
     * 星而言取值唯一）与精确相位距离变化率（对卫星信号而言取值唯一）相加得到。				
	 * DF399=2000h（-8192m/s）表示字段数值无效。
	 * */
	 private int gnssSatGenPhaRate;
	 
	 
	                                                                                   /**信号数据的内容*/
	 
	/**
	 * DF400 (GNSS 信号精确伪距观测值)与 DF397 和 DF398 相加可以得到给定信号所对应的完整伪距观测值。
     * 卫星的每种信号的 DF400 均不相同。
     * DF400=4000h	（	-2	-20	）表示字段无效。
	 * */
	 private int gnssSigPrePse;
	 
	/**
	 * DF401(GNSS  信号精确相位距离数据) 与 DF400 相似，是相位距离的精确值。在载波距离生成之初，为了与伪距大小一致，从原始全波载波中移除掉了部分整周数。		
     * 无周跳时各历元中此整周数为一常数，周跳发生后，必须确定一个新的整周数。
     * 此时应将 DF402（GNSS 相位距离时间锁定标志）置 0。		
	 * 注意，此处定义的相位距离与伪距有符号相同。		
     * 某些电离层状态（或者错误的初始化）可能会引起相位距离与伪距之差（相位距离-伪距）随时间发散，导致数值超过定义范围。
     * 此时，需要重新初始化上面所提到的整周数，DF402 也应置 0。		
     * DF401=200000h（-2-8m）表示数值无效。		
	 * */
	 private int gnssSigPhaPse;
	 
	/**
	 * DF402(GNSS 相位距离锁定时间标志)提供接收机连续锁定卫星信号的时间长度。
	 * 若发生周跳，则 DF402 应重置为 0。DF402 与时间间隔的换算关系见 6.5.15.4.3。		
	 * */
	 private int gnssSigPhaPseCloTimFlag;
	
	/**
	 * DF420 表示是否使用的半周模糊度。
	 * 0=没有半周模糊度；1=半周模糊度。
     * 当相位距离极性未确定时，DF420=1。若果接收机不能处理半周模糊度，
     * 则应跳过相应的相位距离观测值。若极性分辨率要求对相位距离进行半周改正，
     * 则DF402 和 DF407 应置 0（GNSS 相位距离时间）。
	 * */	
	 private boolean halfCycleBlurMark;
	 
	/**
	 * DF403 提供卫星信号的载噪比，单位 dB-Hz。
	 * DF403=0 表示数值未计算或不可用。			
     * 无论 DF403 是否可用，都不影响相应观测值的有效性。		
	 * */
	 private int gnssSigCnr;
	 
	 
	/**
	 * DF404 表示指定信号的精确相位距离变化率。
	 * 精确相位距离变化率为 DF404与 DF399 之和。
	 * 若 DF404=4000h（即-1.6384m/s）表示数值无效。		
	 * */
	 private int gnssSigPrePhaRate;
	 
    /**
      * DF405	与 DF400 定义相似，但提高了分辨率。
      * 若 DF405=80000h（即-2	-10	ms），表明数值无效。		
	  * */
	 private int gnssExSigPrePse;
	 
    /**
      * DF406 与 DF401 定义相似，但提高了分辨率。
      * 若 DF406=80000h（即-2-8ms），表明数值无效。				
	  * */
	 private int gnssExSigPhaPse;
	 
    /**
      * DF407 与 DF402 相似，但是范围更大、分辨率更高，
      * DF407 数值与时间间隔的对应关系见 6.5.15.4.3 表 107。			
	  * */
	 private int gnssExSigPhaPseCloTimFlag;
	 
	 /**
      * DF408 与 DF403 相似，但分辨率更高。
      * DF408=0 表示未计算载噪比或不可用。DF408 是否可用不影响其他观测值的有效性。		
	 * */
	 private int gnssExSigCnr;

	@Override
	public String toString() {
		return "ObserverData [protocolHead=" + protocolHead + ", rfsID=" + rfsID + ", gnssTow=" + gnssTow
				+ ", manyFlag=" + manyFlag + ", iods=" + iods + ", retain=" + retain + ", clockCorrectFlag="
				+ clockCorrectFlag + ", exClockFlag=" + exClockFlag + ", gnssSmoothnessTypeFlag="
				+ gnssSmoothnessTypeFlag + ", gnssSmoothnessInterval=" + gnssSmoothnessInterval + ", gnssSatNum="
				+ gnssSatNum + ", satType=" + satType + ", gnssSigNum=" + gnssSigNum + ", gnssSatGenDisMsInt="
				+ gnssSatGenDisMsInt + ", gnssSatGenDisMsRem=" + gnssSatGenDisMsRem + ", exSatInfo=" + exSatInfo
				+ ", gnssSatGenPhaRate=" + gnssSatGenPhaRate + ", gnssSigPrePse=" + gnssSigPrePse + ", gnssSigPhaPse="
				+ gnssSigPhaPse + ", gnssSigPhaPseCloTimFlag=" + gnssSigPhaPseCloTimFlag + ", halfCycleBlurMark="
				+ halfCycleBlurMark + ", gnssSigCnr=" + gnssSigCnr + ", gnssSigPrePhaRate=" + gnssSigPrePhaRate
				+ ", gnssExSigPrePse=" + gnssExSigPrePse + ", gnssExSigPhaPse=" + gnssExSigPhaPse
				+ ", gnssExSigPhaPseCloTimFlag=" + gnssExSigPhaPseCloTimFlag + ", gnssExSigCnr=" + gnssExSigCnr + "]";
	}
	 
}
