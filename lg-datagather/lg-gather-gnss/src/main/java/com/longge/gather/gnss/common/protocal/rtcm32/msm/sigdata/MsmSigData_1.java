package com.longge.gather.gnss.common.protocal.rtcm32.msm.sigdata;
import com.longge.gather.gnss.common.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description MSM1 信号数据的内容
 * @author jianglong
 * @create 2018-06-14
 **/
@Data
public class MsmSigData_1 {
	/**
	 * DF400 (GNSS 信号精确伪距观测值)与 DF397 和 DF398 相加可以得到给定信号所对应的完整伪距观测值。
     * 卫星的每种信号的 DF400 均不相同。
     * DF400=4000h	（	-2	-20	）表示字段无效。
	 * */
	@SubAnnotation(type = "int",startPos = 0, len = 15, mark="",className="")
	private int gnssSigPrePse;

}
