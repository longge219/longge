package com.longge.gather.gnss.common.protocal.rtcm32.msm.satdata;
import com.longge.gather.gnss.server.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description MSM5 和 MSM7 卫星数据的内容
 * @author jianglong
 * @create 2018-06-14
 **/
@Data
public class MsmSatData_57 {
	
 	 /**
       * DF397  GNSS 卫星概略距离的整毫秒数 用于恢复某颗卫星的完整观测值。概略距离占 18 位，分为 DF397 与DF398 两个字段。
       * DF397 为卫星概略距离的整毫秒数。如果在 MSM1、MSM2和 MSM3 中未传输 DF397，
       * 那么解码设备需要根据参考站位置和星历数据恢复卫星概略距离。	
	   * DF397=FFh（255ms）表示字段无效。
	  * */
	@SubAnnotation(type = "uint",startPos = 0, len = 8, mark="",className="")
	private int gnssSatGenDisMsInt;
	
	/**
	 * 扩展卫星信息--每个系统不同
	 * */
	@SubAnnotation(type = "uint",startPos = 0, len = 4, mark="",className="")
	private int exSatInfo;
	
	/**
	 * DF398 是卫星概略距离的毫秒余数，可以 1/1024ms（约 300m）的精度恢复完整的 GNSS 概略距离。
	 * 见 DF397 的字段说明。
	 * */
	@SubAnnotation(type = "uint",startPos = 0, len = 10, mark="",className="")
	private int gnssSatGenDisMsRem;
	
	
	/**
	 * DF399  GNSS 卫星概略相位距离变化率  与相位距离导数的符号相同。
     * 与距离相似，完整的相位距离变化率观测值可通过概略相位距离变化率（对卫
     * 星而言取值唯一）与精确相位距离变化率（对卫星信号而言取值唯一）相加得到。				
	 * DF399=2000h（-8192m/s）表示字段数值无效。
	 * */
	@SubAnnotation(type = "int",startPos = 0, len = 14, mark="",className="")
	private int gnssSatGenPhaRate;

}
