package com.longge.gather.gnss.common.protocal.wh;
import com.longge.gather.gnss.common.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description 武汉导航院电离层模型改正参数和UTC时间参数
 * @author jianglong
 * @create 2018-08-17
 **/
@Data
public class WhInoutcInfo{
	
	@SubAnnotation(type = "object",startPos = 6, len = 22, mark="",className="com.orieange.common.protocal.wh.WhHead")
	private WhHead whHead;
	
	@SubAnnotation(type = "double",startPos = 28, len = 8, mark="",className="")
	private double alp0;//Alpha parameter constant term
	
	@SubAnnotation(type = "double",startPos = 36, len = 8, mark="",className="")
	private double alp1;//Alpha parameter 1st order term
	
	@SubAnnotation(type = "double",startPos = 44, len = 8, mark="",className="")
	private double alp2;//Alpha parameter 2st order term
	
	@SubAnnotation(type = "double",startPos = 52, len = 8, mark="",className="")
	private double alp3;//Alpha parameter 3st order term
	
	@SubAnnotation(type = "double",startPos = 60, len = 8, mark="",className="")
	private double b0;//Beta parameter constant term
	
	@SubAnnotation(type = "double",startPos = 68, len = 8, mark="",className="")
	private double b1;//Beta parameter 1st order term
	
	@SubAnnotation(type = "double",startPos = 76, len = 8, mark="",className="")
	private double b2;//Beta parameter 2st order term
	
	@SubAnnotation(type = "double",startPos = 84, len = 8, mark="",className="")
	private double b3;//Beta parameter 3st order term
	
	@SubAnnotation(type = "uint",startPos = 92, len = 4, mark="",className="")
	private long utcWn;//UTC reference week number
	
	@SubAnnotation(type = "uint",startPos = 96, len = 4, mark="",className="")
	private long tot;//Reference time of UTC parameters
	
	@SubAnnotation(type = "double",startPos = 100, len = 8, mark="",className="")
	private double a0;//UTC constant term of polynomial 
	
	@SubAnnotation(type = "double",startPos = 108, len = 8, mark="",className="")
	private double a1;//UTC 1st order term of polynomial 
	
	@SubAnnotation(type = "uint",startPos = 116, len = 4, mark="",className="")
	private long wnlsf;//Future week number
	
	@SubAnnotation(type = "uint",startPos = 120, len = 4, mark="",className="")
	private long dn;//Day number (the range is 1 to 7 where Sunday = 1 and Saturday = 7) 
	
	@SubAnnotation(type = "int",startPos = 124, len = 4, mark="",className="")
	private int deltatls ;//Delta time due to leap seconds
	
	@SubAnnotation(type = "int",startPos = 128, len = 4, mark="",className="")
	private int deltatlsf  ;//Future delta time due to leap seconds
	
	@SubAnnotation(type = "uint",startPos = 132, len = 4, mark="",className="")
	private long deltatUtc ;//Time difference 
	
	@SubAnnotation(type = "int",startPos = 136, len = 4, mark="",className="")
	private int crc  ;//32-bit CRC

}
