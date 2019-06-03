package com.longge.gather.gnss.ci.model;
import lombok.Data;
/**
 * @Description: 计算结果返回对象
 * @create Author: jianglong
 * @create 2018-10-30
 */
@Data
public class CalOutData {
	
	//差分标志   第一位为伪距差分标志位 0：伪距差分失败   1：伪距差分成功    第二位为载波差分标志位 0：载波差分失败  1：载波差分成功
	 private short DDFlag;
	 
	 //载波差分基线长度
	 private double BaselineLength;
	 
	  //基准站坐标
	 private double masterX;
	 
	 private double masterY;
	 
	 private double masterZ;
	 
	 //载波差分基线向量(XYZ)
	 private double BaseLineVectX;
	 
	 private double BaseLineVectY;
	 
	 private double BaseLineVectZ;
}
