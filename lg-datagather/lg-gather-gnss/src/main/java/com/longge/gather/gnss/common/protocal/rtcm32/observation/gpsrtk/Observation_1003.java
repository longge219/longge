package com.longge.gather.gnss.common.protocal.rtcm32.observation.gpsrtk;

import com.longge.gather.gnss.common.protocal.rtcm32.head.ProtocolHead;
import com.longge.gather.gnss.server.reflect.SubAnnotation;

/**
 * @description GPS RTK 观测值电文1003
 * @author jianglong
 * @create 2018-04-02
 **/
public class Observation_1003  extends GpsRtkHead implements ProtocolHead {

	@Override
	public int getProtocolHead() {
		return 1003;
	}
	
	@SubAnnotation(type = "array",startPos = 64, len = 101, mark="",className="com.orieange.common.protocal.rtcm32.observation.gpsrtk.Observation_1003_Data")
	private Observation_1003_Data[] dataArray; //GPSRTK观测值电文1002数据体数组

	public Observation_1003_Data[] getDataArray() {
		return dataArray;
	}

	public void setDataArray(Observation_1003_Data[] dataArray) {
		this.dataArray = dataArray;
	}
	
}
