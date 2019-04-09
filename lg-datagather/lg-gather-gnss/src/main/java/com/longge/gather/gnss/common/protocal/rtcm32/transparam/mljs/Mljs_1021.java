package com.longge.gather.gnss.common.protocal.rtcm32.transparam.mljs;
import com.longge.gather.gnss.common.protocal.rtcm32.head.ProtocolHead;
import com.longge.gather.gnss.server.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description 赫尔默特/莫洛金斯基电文1021
 * @author jianglong
 * @create 2018-04-02
 **/
@Data
public class Mljs_1021 implements ProtocolHead {

	@Override
	public int getProtocolHead() {
		return 1021;
	}
	
	@SubAnnotation(type = "byte",startPos = 12, len = 5, mark="",className="")
	private byte oriCharNum;//源名称字符数
	
	@SubAnnotation(type = "string",startPos = 17, len = 8, mark="",className="")
	private String oriChar;//源名称
	
	@SubAnnotation(type = "byte", startPos = 17, len = 5, mark="",className="")
	private byte tarCharNum;//目标名称字符数
	
	@SubAnnotation(type = "string",startPos = 17, len = 8, mark="",className="")
	private String tarChar;//目标名称
	
	@SubAnnotation(type = "short",startPos = 17, len = 8, mark="",className="")
	private short sysId;//系统识别码
	
	@SubAnnotation(type = "short",startPos = 17, len = 10, mark="",className="")
	private short transPacketFlag;//转换电文标识符
	
	@SubAnnotation(type = "short",startPos = 17, len = 10, mark="",className="")
	private short flatAreaId;//平面区域代码
	
	@SubAnnotation(type = "short",startPos = 17, len = 4, mark="",className="")
	private short transModelFlag;//转换模型标志
	
	@SubAnnotation(type = "short",startPos = 17, len = 2, mark="",className="")
	private short heightFlag;//高度系统标志
	
	@SubAnnotation(type = "int",startPos = 17, len = 19, mark="",className="")
	private int v1;//ϕV
	
	@SubAnnotation(type = "int",startPos = 17, len = 20, mark="",className="")
	private int v2;//λV
	
	@SubAnnotation(type = "short",startPos = 17, len = 14, mark="",className="")
	private int v3;//ΔϕV
	
	@SubAnnotation(type = "short",startPos = 17, len = 14, mark="",className="")
	private int v4;//ΔλV
	
	@SubAnnotation(type = "short",startPos = 17, len = 23, mark="",className="")
	private int dX;//dX
	
	@SubAnnotation(type = "short",startPos = 17, len = 23, mark="",className="")
	private int dY;//dY
	
	@SubAnnotation(type = "short",startPos = 17, len = 23, mark="",className="")
	private int dZ;//dZ
	
	@SubAnnotation(type = "long",startPos = 17, len = 32, mark="",className="")
	private long r1;//R1
	
	@SubAnnotation(type = "long",startPos = 17, len = 32, mark="",className="")
	private long r2;//R2
	
	@SubAnnotation(type = "long",startPos = 17, len = 32, mark="",className="")
	private long r3;//R3
	
	@SubAnnotation(type = "int",startPos = 17, len = 25, mark="",className="")
	private int dS ;//dS 
	
	@SubAnnotation(type = "int",startPos = 17, len = 24, mark="",className="")
	private int aSCorrect ;//aS 修正数
	
	@SubAnnotation(type = "int",startPos = 17, len = 25, mark="",className="")
	private int bSCorrect ;//bS 修正数
	
	@SubAnnotation(type = "int",startPos = 17, len = 24, mark="",className="")
	private int aTCorrect ;//aT 修正数
	
	@SubAnnotation(type = "int",startPos = 17, len = 25, mark="",className="")
	private int bTCorrect ;//bT 修正数
	
	@SubAnnotation(type = "byte",startPos = 17, len = 3, mark="",className="")
	private byte flatIndex ;//赫尔默特（Helmert）/莫洛金斯（Molodenski）平面精度指标
	
	@SubAnnotation(type = "byte",startPos = 17, len = 3, mark="",className="")
	private byte heightIndex ;//赫尔默特（Helmert）/莫洛金斯基(Molodenski）高程精度指标

}
