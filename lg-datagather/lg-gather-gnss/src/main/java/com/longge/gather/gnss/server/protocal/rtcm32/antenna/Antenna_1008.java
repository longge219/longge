package com.longge.gather.gnss.server.protocal.rtcm32.antenna;
import com.longge.gather.gnss.server.protocal.rtcm32.head.ProtocolHead;
import com.longge.gather.gnss.server.reflect.SubAnnotation;
/**
 * @description 天线说明电文1008
 * @author jianglong
 * @create 2018-04-02
 **/
public class Antenna_1008 implements ProtocolHead {

	@Override
	public int getProtocolHead() {
		return 1007;
	}

	@SubAnnotation(type = "short",startPos = 12, len = 12, mark="",className="")
	private short rfsID;//参考站ID
	
	@SubAnnotation(type = "short",startPos = 24, len = 8, mark="",className="")
	private short antennaNum;//天线标识符字符数
	
	@SubAnnotation(type = "string",startPos = 32, len = 8, mark="",className="")
	private String antennaChar;//天线标识符
	
	@SubAnnotation(type = "short",startPos = 40, len = 8, mark="",className="")
	private short antennaSerailNum;//天线序列号字符数
	
	@SubAnnotation(type = "short",startPos = 40, len = 8, mark="",className="")
	private String antennaSerail;//天线序列号
}