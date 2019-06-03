package com.longge.gather.gnss.server.protocal.rtcm32.msm.sigdata;
import com.longge.gather.gnss.server.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description MSM4 信号数据的内容
 * @author jianglong
 * @create 2018-06-14
 **/
@Data
public class MsmSigData_4 {
	
	/**
	 * DF400 (GNSS 信号精确伪距观测值)与 DF397 和 DF398 相加可以得到给定信号所对应的完整伪距观测值。
     * 卫星的每种信号的 DF400 均不相同。
     * DF400=4000h	（	-2	-20	）表示字段无效。
	 * */
	@SubAnnotation(type = "int",startPos = 0, len = 15, mark="sprepByMsmGnssEleNum",className="")
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
	@SubAnnotation(type = "int",startPos = 0, len = 22, mark="sphapByMsmGnssEleNum",className="")
	private int gnssSigPhaPse;
	
	/**
	 * DF402(GNSS 相位距离锁定时间标志)提供接收机连续锁定卫星信号的时间长度。
	 * 若发生周跳，则 DF402 应重置为 0。DF402 与时间间隔的换算关系见 6.5.15.4.3。		
	 * */
	@SubAnnotation(type = "uint",startPos = 0, len = 4, mark="sppctfByMsmGnssEleNum",className="")
	private int gnssSigPhaPseCloTimFlag;
	
	/**
	 * DF420 表示是否使用的半周模糊度。
	 * 0=没有半周模糊度；1=半周模糊度。
     * 当相位距离极性未确定时，DF420=1。若果接收机不能处理半周模糊度，
     * 则应跳过相应的相位距离观测值。若极性分辨率要求对相位距离进行半周改正，
     * 则DF402 和 DF407 应置 0（GNSS 相位距离时间）。
	 * */
	@SubAnnotation(type = "boolean",startPos = 0, len = 1, mark="hcbmByMsmGnssEleNum",className="")
	private boolean halfCycleBlurMark;
	
	/**
	 * DF403 提供卫星信号的载噪比，单位 dB-Hz。
	 * DF403=0 表示数值未计算或不可用。			
     * 无论 DF403 是否可用，都不影响相应观测值的有效性。		
	 * */
	@SubAnnotation(type = "uint",startPos = 0, len = 6, mark="scByMsmGnssEleNum",className="")
	private int gnssSigCnr;

}
