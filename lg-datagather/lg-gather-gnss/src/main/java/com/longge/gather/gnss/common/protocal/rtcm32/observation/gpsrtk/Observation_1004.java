package com.longge.gather.gnss.common.protocal.rtcm32.observation.gpsrtk;
import com.longge.gather.gnss.common.protocal.rtcm32.head.ProtocolHead;
import com.longge.gather.gnss.server.reflect.SubAnnotation;

/**
 * @description GPS RTK 观测值电文1004
 * @author jianglong
 * @create 2018-04-02
 **/
public class Observation_1004  extends GpsRtkHead implements ProtocolHead {

	@Override
	public int getProtocolHead() {
		return 1004;
	}
	
	@SubAnnotation(type = "array",startPos = 64, len = 125, mark="",className="com.orieange.common.protocal.rtcm32.observation.gpsrtk.Observation_1004_Data")
	private Observation_1004_Data[] dataArray; //GPSRTK观测值电文1004数据体数组

	public Observation_1004_Data[] getDataArray() {
		return dataArray;
	}

	public void setDataArray(Observation_1004_Data[] dataArray) {
		this.dataArray = dataArray;
	}
	
}
