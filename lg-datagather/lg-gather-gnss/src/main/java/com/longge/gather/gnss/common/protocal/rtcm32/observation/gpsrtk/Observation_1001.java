package com.longge.gather.gnss.common.protocal.rtcm32.observation.gpsrtk;
import com.longge.gather.gnss.common.protocal.rtcm32.head.ProtocolHead;
import com.longge.gather.gnss.common.reflect.SubAnnotation;
/**
 * @description GPS RTK 观测值电文1001
 * @author jianglong
 * @create 2018-04-02
 **/
public class Observation_1001  extends GpsRtkHead implements ProtocolHead {

	@Override
	public int getProtocolHead() {
		return 1001;
	}

	/**
	 * startPos = 0 表示依赖卫星数量解码
	 * */
	@SubAnnotation(type = "array",startPos = 0, len = 58, mark="",className="com.orieange.common.protocal.rtcm32.observation.gpsrtk.Observation_1001_Data")
	private Observation_1001_Data[] dataArray; //GPSRTK观测值电文1001数据体数组

	public Observation_1001_Data[] getDataArray() {
		return dataArray;
	}

	public void setDataArray(Observation_1001_Data[] dataArray) {
		this.dataArray = dataArray;
	}
	
}
