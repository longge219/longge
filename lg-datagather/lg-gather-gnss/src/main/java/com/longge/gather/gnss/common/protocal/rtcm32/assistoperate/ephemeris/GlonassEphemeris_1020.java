package com.longge.gather.gnss.common.protocal.rtcm32.assistoperate.ephemeris;
import com.longge.gather.gnss.common.protocal.rtcm32.head.ProtocolHead;
import com.longge.gather.gnss.common.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description GLONASS卫星星历数据电文1046
 * @author jianglong
 * @create 2018-06-14
 **/
@Data
public class GlonassEphemeris_1020 implements ProtocolHead {

	@Override
	public int getProtocolHead() {
		
		return 1020;
	}
	@SubAnnotation(type = "byte",startPos = 12, len = 6, mark="",className="")
	private byte glonasStarId;
	
	@SubAnnotation(type = "byte",startPos = 18, len = 5, mark="",className="")
	private byte glonassSatChannel;
	
	@SubAnnotation(type = "byte",startPos = 23, len = 1, mark="",className="")
	private byte glonassEphHealth;
	
	@SubAnnotation(type = "byte",startPos = 24, len = 1, mark="",className="")
	private byte glonassEphFlag;
	
	@SubAnnotation(type = "byte",startPos = 25, len = 2, mark="",className="")
	private byte glonassP1;
	
	@SubAnnotation(type = "short",startPos = 27, len = 12, mark="",className="")
	private int glonassTk;
	
	@SubAnnotation(type = "byte",startPos = 39, len = 1, mark="",className="")
	private byte glonassBnMSB;
	
	@SubAnnotation(type = "byte",startPos = 40, len = 1, mark="",className="")
	private byte glonassB2;
	
	@SubAnnotation(type = "byte",startPos = 41, len = 7, mark="",className="")
	private byte glonassTb;
	
	@SubAnnotation(type = "int",startPos = 48, len = 24, mark="",className="")
	private int glonassDXn;
	
	@SubAnnotation(type = "int",startPos = 72, len = 27, mark="",className="")
	private int glonassXn;
	
	@SubAnnotation(type = "int",startPos = 99, len = 5, mark="",className="")
	private int glonassD2Xn;
	
	@SubAnnotation(type = "int",startPos = 104, len = 24, mark="",className="")
	private int glonassDYn;
	
	@SubAnnotation(type = "int",startPos = 128, len = 27, mark="",className="")
	private int glonassYn;
	
	@SubAnnotation(type = "int",startPos = 155, len =5, mark="",className="")
	private int glonassD2Yn;
	
	@SubAnnotation(type = "int",startPos = 160, len = 24, mark="",className="")
	private int glonassDZn;
	
	@SubAnnotation(type = "int",startPos = 184, len = 27, mark="",className="")
	private int glonassZn;
	
	@SubAnnotation(type = "int",startPos = 211, len =5, mark="",className="")
	private int glonassD2Zn;
	
	@SubAnnotation(type = "byte",startPos = 216, len =1, mark="",className="")
	private byte glonassP3;
	
	@SubAnnotation(type = "int",startPos = 217, len =11, mark="",className="")
	private int glonassGamaN;
	
	@SubAnnotation(type = "byte",startPos = 228, len =2, mark="",className="")
	private byte glonassMP;
	
	@SubAnnotation(type = "byte",startPos = 230, len =1, mark="",className="")
	private byte glonassMIn;
	
	@SubAnnotation(type = "int",startPos = 231, len =22, mark="",className="")
	private int glonassTauN;
	
	@SubAnnotation(type = "byte",startPos = 253, len =5, mark="",className="")
	private byte glonassMDiffTau;
	
	@SubAnnotation(type = "byte",startPos = 258, len =5, mark="",className="")
	private byte glonassEn;
	
	@SubAnnotation(type = "byte",startPos = 263, len =1, mark="",className="")
	private byte glonassMP4;
	
	@SubAnnotation(type = "byte",startPos = 264, len =4, mark="",className="")
	private byte glonassFt;
	
	@SubAnnotation(type = "int",startPos = 268, len =11, mark="",className="")
	private int glonassNt;
	
	@SubAnnotation(type = "byte",startPos = 279, len =2, mark="",className="")
	private byte glonassMM;
	
	@SubAnnotation(type = "byte",startPos = 281, len =1, mark="",className="")
	private byte glonassAdditionFlag;//glonass附加可用性标志
	
	@SubAnnotation(type = "short",startPos = 282, len =11, mark="",className="")
	private short glonassNA;
	
	@SubAnnotation(type = "int",startPos = 293, len =32, mark="",className="")
	private int glonassTauC;
	
	@SubAnnotation(type = "byte",startPos = 325, len =5, mark="",className="")
	private byte glonassMN4;
	
	@SubAnnotation(type = "int",startPos = 330, len =32, mark="",className="")
	private int glonassM_TauGPS;
	
	@SubAnnotation(type = "byte",startPos = 362, len =7, mark="",className="")
	private byte glonassM_ln;
	
	@SubAnnotation(type = "byte",startPos = 369, len =7, mark="",className="")
	private byte reserve;
}
