package com.longge.gather.gnss.common.protocal.rtcm32.observation.gpsrtk;
import com.longge.gather.gnss.common.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description GPS RTK 观测值电文1003数据体
 * @author jianglong
 * @create 2018-04-02
 **/
@Data
public class Observation_1003_Data {
	
	@SubAnnotation(type = "byte",startPos = 64, len = 6, mark="",className="")
	private byte gpsStarID;//GPS卫星号
	
	@SubAnnotation(type = "byte",startPos = 70, len = 1, mark="",className="")
	private byte gpsL1Flag;//GPS L1码标志
	
	@SubAnnotation(type = "int",startPos = 71, len = 24, mark="",className="")
	private byte gpsL1PreDistance;//GPS L1伪距
	
	@SubAnnotation(type = "int",startPos = 95, len = 20, mark="",className="")
	private byte gpsL1CarrierPreDistance;//GPS L1载波距离-L1 伪距
	
	@SubAnnotation(type = "byte",startPos = 115,len = 7, mark="",className="")
	private byte gpsL1LockTimeFlag;//GPS L1锁定时间标志
	
	@SubAnnotation(type = "byte",startPos = 122, len = 2, mark="",className="")
	private byte gpsL2Flag;//GPS L2码标志
	
	@SubAnnotation(type = "int",startPos = 124, len = 14, mark="",className="")
	private byte gpsL2PreDistance;//GPS L2伪距
	
	@SubAnnotation(type = "int",startPos = 138, len = 20, mark="",className="")
	private byte gpsL2CarrierPreDistance;//GPS L2载波距离-L1 伪距
	
	@SubAnnotation(type = "byte",startPos = 158,len = 7, mark="",className="")
	private byte gpsL2LockTimeFlag;//GPS L2锁定时间标志
	
}
