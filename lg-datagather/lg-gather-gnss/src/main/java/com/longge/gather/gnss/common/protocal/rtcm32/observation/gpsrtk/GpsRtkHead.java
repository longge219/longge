package com.longge.gather.gnss.common.protocal.rtcm32.observation.gpsrtk;
import com.longge.gather.gnss.server.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description GPS RTK 观测值电文头
 * @author jianglong
 * @create 2018-04-02
 **/
@Data
public class GpsRtkHead {
	
	@SubAnnotation(type = "uint",startPos = 12, len = 12, mark="",className="")
	private int rfsID;//参考站ID
	/**
	 * GPS 周内秒，即从当前 GPS 周的开始时刻起算，精确到 ms。GPS周开始于星期六晚上/星期日早上格林尼治标准时间的午夜，与 UTC 相反。
	 * */
	@SubAnnotation(type = "uint",startPos = 24, len = 30, mark="",className="")
	private int gpsTow;//GPS历元时刻（TOW)

	/**
	 * 0=同步历元观测数据传输完毕，接收机应在电文解码后即刻开始数据处理；
     * 1=后续电文中含有同一历元时刻的 GNSS 观测数据。
     * 同步的含义是指观测值的历元时刻相差小于 1μs。
	 * */
	@SubAnnotation(type = "boolean",startPos = 54, len = 1, mark="",className="")
	private boolean synGnssFlag;//同步GNSS电文标志
	
	/**
	 * 表示电文中的卫星数，不一定等于参考站上的可见卫星数。
	 * */
	@SubAnnotation(type = "uint",startPos = 55, len = 5, mark="starNum",className="")
	private int gpsStarNum;//GPS卫星数
	
	/**
	 * 0=没有使用无弥散平滑；
	 *  1=使用了无弥散平滑。
	 * */
	@SubAnnotation(type = "boolean",startPos = 60, len = 1, mark="",className="")
	private boolean unDisSmoothnessFlag;//GPS无弥散平滑标志
	
	/**
	 *  是参考站使用载波平滑伪距时，所用的平滑时间长度。在整个卫星可见时间段内，可能会一直使用无弥散平滑。
	 * */
	@SubAnnotation(type = "uint",startPos = 61, len = 3, mark="",className="")
	private int smoothnessInterval;//GPS平滑间隔
}
