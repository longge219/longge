package com.longge.gather.gnss.common.protocal.rtcm32.msm.satdata;
import com.longge.gather.gnss.server.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description MSM4 和 MSM6 卫星数据的内容
 * @author jianglong
 * @create 2018-06-14
 **/
@Data
public class MsmSatData_46 {
	
 	 /**
       * DF397  GNSS 卫星概略距离的整毫秒数 用于恢复某颗卫星的完整观测值。概略距离占 18 位，分为 DF397 与DF398 两个字段。
       * DF397 为卫星概略距离的整毫秒数。如果在 MSM1、MSM2和 MSM3 中未传输 DF397，
       * 那么解码设备需要根据参考站位置和星历数据恢复卫星概略距离。	
	   * DF397=FFh（255ms）表示字段无效。
	  * */
	 @SubAnnotation(type = "uint",startPos = 0, len = 8, mark="sgdmiByMsmGnssSatNumIndex",className="")
	 private int gnssSatGenDisMsInt;
	
	/**
	 * DF398 是卫星概略距离的毫秒余数，可以 1/1024ms（约 300m）的精度恢复完整的 GNSS 概略距离。
	 * 见 DF397 的字段说明。
	 * */
	@SubAnnotation(type = "uint",startPos = 0, len = 10, mark="sgdmrByMsmGnssSatNumIndex",className="")
	private int gnssSatGenDisMsRem;

}
