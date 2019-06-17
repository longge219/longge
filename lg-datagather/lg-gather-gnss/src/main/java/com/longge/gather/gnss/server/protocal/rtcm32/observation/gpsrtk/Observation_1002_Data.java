package com.longge.gather.gnss.server.protocal.rtcm32.observation.gpsrtk;
import com.longge.gather.gnss.server.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description GPS RTK 观测值电文1002数据体
 * @author jianglong
 * @create 2018-04-02
 **/
@Data
public class Observation_1002_Data {
	
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
	
	@SubAnnotation(type = "short",startPos = 122, len = 8, mark="",className="")
	private short gpsL1PreDistanceMill;//GPS L1伪距光毫秒整数
	
	@SubAnnotation(type = "short",startPos = 130, len = 8, mark="",className="")
	private short gpsL1Cnr;//GPS L1 CNR 
	
}