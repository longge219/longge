package com.longge.gather.gnss.common.protocal.rtcm32.observation.gpsrtk;

import com.longge.gather.gnss.common.protocal.rtcm32.head.ProtocolHead;
import com.longge.gather.gnss.common.reflect.SubAnnotation;

/**
 * @description GPS RTK 观测值电文1002
 * @author jianglong
 * @create 2018-04-02
 **/
public class Observation_1002  extends GpsRtkHead implements ProtocolHead {

	@Override
	public int getProtocolHead() {
		return 1002;
	}
	
	@SubAnnotation(type = "array",startPos = 64, len = 74, mark="",className="com.orieange.common.protocal.rtcm32.observation.gpsrtk.Observation_1002_Data")
	private Observation_1002_Data[] dataArray; //GPSRTK观测值电文1002数据体数组

	public Observation_1002_Data[] getDataArray() {
		return dataArray;
	}

	public void setDataArray(Observation_1002_Data[] dataArray) {
		this.dataArray = dataArray;
	}
	
}
