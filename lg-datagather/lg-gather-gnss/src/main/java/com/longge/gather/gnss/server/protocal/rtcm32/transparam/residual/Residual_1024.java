package com.longge.gather.gnss.server.protocal.rtcm32.transparam.residual;
import com.longge.gather.gnss.server.protocal.rtcm32.head.ProtocolHead;
import com.longge.gather.gnss.server.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description 残差电文1024
 * @author jianglong
 * @create 2018-04-02
 **/
@Data
public class Residual_1024 implements ProtocolHead {

	@Override
	public int getProtocolHead() {
		return 1024;
	}
	
	@SubAnnotation(type = "short",startPos = 12, len = 8, mark="",className="")
	private short sysId;//系统识别码
	
	@SubAnnotation(type = "byte",startPos = 20, len = 1, mark="",className="")
	private byte levelFlag;//水平移动标志
	
	@SubAnnotation(type = "byte",startPos = 21, len = 1, mark="",className="")
	private byte verticalFlag;//竖直移动标志
	
	@SubAnnotation(type = "int",startPos = 22, len = 25, mark="",className="")
	private int N0;//N0
	
	@SubAnnotation(type = "int",startPos = 47, len = 26, mark="",className="")
	private int E0;//E0
	
	@SubAnnotation(type = "short",startPos = 73, len = 12, mark="",className="")
	private int cn;//ΔN
	
	@SubAnnotation(type = "short",startPos = 85, len = 12, mark="",className="")
	private int ce;//ΔE 
	
	@SubAnnotation(type = "short",startPos = 97, len = 10, mark="",className="")
	private short cnAverage;//ΔN均值
	
	@SubAnnotation(type = "short",startPos = 107, len = 10, mark="",className="")
	private short ceAverage;//ΔE 均值
	
	@SubAnnotation(type = "short",startPos = 117, len = 15, mark="",className="")
	private short chAverage;//ΔH 均值
	
	@SubAnnotation(type = "String",startPos = 120, len = 432, mark="",className="")
	private short threeChange;//16 个格网点的三种变换
	
	@SubAnnotation(type = "short",startPos = 559, len = 9, mark="",className="")
	private short dni;//δNi
	
	@SubAnnotation(type = "short",startPos = 568, len = 9, mark="",className="")
	private short dei;//δEi
	
	@SubAnnotation(type = "short",startPos = 577, len = 9, mark="",className="")
	private short dhi;//δhi
	
	@SubAnnotation(type = "byte",startPos = 586, len = 2, mark="",className="")
	private byte flatInsertFlag ;//平面内插方法标志
	
	@SubAnnotation(type = "byte",startPos = 588, len = 2, mark="",className="")
	private byte heightInsertFlag ;//高程内插方法标志
	
	@SubAnnotation(type = "byte",startPos = 590, len = 3, mark="",className="")
	private byte gridFlatIndex ;//格网平面精度指标
	
	@SubAnnotation(type = "byte",startPos = 593, len = 3, mark="",className="")
	private byte gridHeightIndex ;//格网高程精度指标
	
	@SubAnnotation(type = "int",startPos = 602, len = 16, mark="",className="")
	private int mjdDays ;//MJD 天数

}
