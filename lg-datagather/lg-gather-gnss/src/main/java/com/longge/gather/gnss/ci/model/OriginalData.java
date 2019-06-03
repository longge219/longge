package com.longge.gather.gnss.ci.model;
import lombok.Data;
/**
 * @Description:DLL调用输入参数--原始数据
 * @create Author:jianglong
 * @create 2018-10-30
 */
@Data
public class OriginalData {
	
	//定位模式   0:Null   1:L1   2:L2   4:L5   8:B1    16:B2   32:B3    64:G1    128:G2 
	private short  PosMod;
	
	//动态模式   0：静态  1：动态
    private short Dynamic;
	
	//GPS周
	private short[] week;
	
	//电离层参数标记 1:有；0:没有
	private short IonFlag;				
	
	//周内秒   0:GPS   1:BD   2:Glonass
	private double[] desecond;

	// 卫星数
	private byte satNum;
	
	//接收机位置
	private double[] XYZ;
	
	private double[]  BLH;
	
	//卫星信息
	private  StarInfo[] StarInfor;	
}
