package com.longge.gather.gnss.common.protocal.rtcm32.msm.satdata;
import com.longge.gather.gnss.common.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description MSM1--MSM7电文头
 * @author jianglong
 * @create 2018-06-14
 **/
@Data
public class MsmSatData_123 {
	
	/**
	 * DF398 是卫星概略距离的毫秒余数，可以 1/1024ms（约 300m）的精度恢复完整的 GNSS 概略距离。
	 * 见 DF397 的字段说明。
	 * */
	@SubAnnotation(type = "uint",startPos = 0, len = 10, mark="",className="")
	private int gnssSatGenDisMsRem;

}
