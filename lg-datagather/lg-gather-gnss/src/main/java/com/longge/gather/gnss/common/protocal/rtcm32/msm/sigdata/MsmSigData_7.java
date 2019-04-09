package com.longge.gather.gnss.common.protocal.rtcm32.msm.sigdata;
import com.longge.gather.gnss.server.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description MSM7 信号数据的内容
 * @author jianglong
 * @create 2018-06-14
 **/
@Data
public class MsmSigData_7 {
	
	/**
       * DF405	与 DF400 定义相似，但提高了分辨率。
       * 若 DF405=80000h（即-2	-10	ms），表明数值无效。		
	  * */
	@SubAnnotation(type = "int",startPos = 0, len = 20, mark="",className="")
	private int gnssExSigPrePse;
	
	/**
      * DF406 与 DF401 定义相似，但提高了分辨率。
      * 若 DF406=80000h（即-2-8ms），表明数值无效。				
	  * */
	@SubAnnotation(type = "int",startPos = 0, len = 24, mark="",className="")
	private int gnssExSigPhaPse;
	
	/**
       * DF407 与 DF402 相似，但是范围更大、分辨率更高，
       * DF407 数值与时间间隔的对应关系见 6.5.15.4.3 表 107。			
	  * */
	@SubAnnotation(type = "uint",startPos = 0, len = 10, mark="",className="")
	private int gnssExSigPhaPseCloTimFlag;
	
	/**
	 * DF420 表示是否使用的半周模糊度。
	 * 0=没有半周模糊度；1=半周模糊度。
     * 当相位距离极性未确定时，DF420=1。若果接收机不能处理半周模糊度，
     * 则应跳过相应的相位距离观测值。若极性分辨率要求对相位距离进行半周改正，
     * 则DF402 和 DF407 应置 0（GNSS 相位距离时间）。
	 * */
	@SubAnnotation(type = "boolean",startPos = 0, len = 1, mark="",className="")
	private boolean HalfCycleBlurMark;
	
	/**
       * DF408 与 DF403 相似，但分辨率更高。
       * DF408=0 表示未计算载噪比或不可用。DF408 是否可用不影响其他观测值的有效性。		
	 * */
	@SubAnnotation(type = "uint",startPos = 0, len = 10, mark="",className="")
	private int gnssExSigCnr;
	
	
	/**
	 * DF404 表示指定信号的精确相位距离变化率。
	 * 精确相位距离变化率为 DF404与 DF399 之和。
	 * 若 DF404=4000h（即-1.6384m/s）表示数值无效。		
	 * */
	@SubAnnotation(type = "int",startPos = 0, len = 15, mark="",className="")
	private int gnssSigPrePhaRate;

}
