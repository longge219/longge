package com.longge.gather.gnss.common.protocal.rtcm32.transparam.residual;
import com.longge.gather.gnss.common.protocal.rtcm32.head.ProtocolHead;
import com.longge.gather.gnss.common.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description 残差电文1023
 * @author jianglong
 * @create 2018-04-02
 **/
@Data
public class Residual_1023 implements ProtocolHead {

	@Override
	public int getProtocolHead() {
		return 1023;
	}
	
	@SubAnnotation(type = "short",startPos = 12, len = 8, mark="",className="")
	private short sysId;//系统识别码
	
	@SubAnnotation(type = "byte",startPos = 20, len = 1, mark="",className="")
	private byte levelFlag;//水平移动标志
	
	@SubAnnotation(type = "byte",startPos = 21, len = 1, mark="",className="")
	private byte verticalFlag;//竖直移动标志
	
	@SubAnnotation(type = "int",startPos = 22, len = 21, mark="",className="")
	private int a0;//ϕ0
	
	@SubAnnotation(type = "int",startPos = 43, len = 22, mark="",className="")
	private int b0;//λ0
	
	@SubAnnotation(type = "short",startPos = 65, len = 12, mark="",className="")
	private int ca;//Δϕ
	
	@SubAnnotation(type = "short",startPos = 77, len = 12, mark="",className="")
	private int cb;//Δλ
	
	@SubAnnotation(type = "byte",startPos = 89, len = 8, mark="",className="")
	private byte caAverage;//Δϕ均值
	
	@SubAnnotation(type = "byte",startPos = 97, len = 8, mark="",className="")
	private byte cbAverage;//Δλ均值
	
	@SubAnnotation(type = "short",startPos = 105, len = 15, mark="",className="")
	private short chAverage;//ΔH 均值
	
	@SubAnnotation(type = "String",startPos = 120, len = 432, mark="",className="")
	private short threeChange;//16 个格网点的三种变换
	
	@SubAnnotation(type = "short",startPos = 559, len = 9, mark="",className="")
	private short dai;//δφi
	
	@SubAnnotation(type = "short",startPos = 568, len = 9, mark="",className="")
	private short dbi;//δλi
	
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
