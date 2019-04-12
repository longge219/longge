package com.longge.gather.gnss.scan.model;
import lombok.Data;
/**
 * @Description:原始数据
 * @create Author:jianglong
 * @create 2019-04-11
 */
@Data
public class OriginalData {
	
	//定位模式   0:Null   1:L1   2:L2   4:L5   8:B1    16:B2   32:B3    64:G1    128:G2 
	private short  PosMod;
	
	//动态模式   0：静态  1：动态
    private short Dynamic;

	//电离层参数标记 1:有；0:没有
	private short IonFlag;

	//接收机位置
	private double[] XYZ;
	
	//周  0:GPS周  1:BD周   2:Glonass周
	private short[] week;

	//周内秒   0:GPS   1:BD   2:Glonass
	private double[] desecond;

	// 卫星数
	private byte satNum;
	
	//卫星信息
	private  StarInfo[] StarInfor;	
}
