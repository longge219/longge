package com.longge.gather.gnss.common.protocal.rtcm32.assistoperate.systemparam;
import com.longge.gather.gnss.common.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description 辅助信息--系统参数电文1003数据体
 * @author jianglong
 * @create 2018-04-04
 **/
@Data
public class SystemParam_1013_Data { 

	@SubAnnotation(type = "short",startPos = 70, len = 12, mark="",className="")
	private short packetID;//电文ID
	
	@SubAnnotation(type = "byte",startPos = 82, len = 1, mark="",className="")
	private byte synFlag;//同步标志
	
	@SubAnnotation(type = "int",startPos = 81, len = 16, mark="",className="")
	private int transInterval;//传输间隔


	
}
