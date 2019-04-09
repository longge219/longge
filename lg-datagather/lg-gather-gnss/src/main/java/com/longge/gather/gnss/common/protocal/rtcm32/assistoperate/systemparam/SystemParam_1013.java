package com.longge.gather.gnss.common.protocal.rtcm32.assistoperate.systemparam;
import com.longge.gather.gnss.common.protocal.rtcm32.head.ProtocolHead;
import com.longge.gather.gnss.server.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description 辅助信息--系统参数电文1003
 * @author jianglong
 * @create 2018-04-04
 **/
@Data
public class SystemParam_1013 implements ProtocolHead {

	@Override
	public int getProtocolHead() {
		return 1013 ;
	}
	
	@SubAnnotation(type = "short",startPos = 12, len = 12, mark="",className="")
	private short rfsID;//参考站ID
	
	@SubAnnotation(type = "int",startPos = 24, len = 16, mark="",className="")
	private int mjdDays;//MJD 天数
	
	@SubAnnotation(type = "int",startPos = 40, len = 17, mark="",className="")
	private int utcTime;//UTC 日秒
	
	@SubAnnotation(type = "byte",startPos = 57, len = 5, mark="",className="")
	private byte packetNum;//后续电文数
	
	@SubAnnotation(type = "short",startPos = 62, len = 8, mark="",className="")
	private short gpsUtc;//GPS-UTC 跳秒数
	
	@SubAnnotation(type = "array",startPos = 70, len = 29, mark="",className="")
	private SystemParam_1013_Data[] dataArray;//数据体
	
	
}
