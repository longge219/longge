package com.longge.gather.gnss.gnss.calculate;
import com.longge.gather.gnss.common.model.ObserverData;
import com.longge.gather.gnss.gnss.constant.GnssConstants;
import org.ejml.simple.SimpleMatrix;
/**
 * Description:星历相关计算
 * User: jianglong
 * Date: 2018年6月05号
 */
public class PosCalculate {
	/**
	 * @description: 计算观测距离
	 * @param: ObserverData 观测数据
	 * @param: disType(0:伪距，1:载波相位距离)
	 * @return:  观测距离
	 */
	public   double computeObsDis(ObserverData observerData, int disType){
			double range = 0.0d;
			int temp = 0;
			int precisionType = 0; //0表示标准精度，1表示高精度
			int headerType = observerData.getProtocolHead();
		
			temp = headerType % 10;
			if (temp > 5){
			precisionType = 1;
			}
			else if ((temp > 3) && (temp < 6)){
			precisionType = 0;
			}
			else{
			precisionType = 0;
			//恢复毫秒整数
			observerData.setGnssSatGenDisMsInt(0);
			}
			switch (disType){
			case 0:
			if (precisionType == 0){
			//标准精度伪距
			range = (observerData.getGnssSatGenDisMsInt() + (observerData.getGnssSatGenDisMsRem()/1024.0)
			+ (observerData.getGnssSigPrePse() * Math.pow(2, -24))) * (GnssConstants.SPEED_OF_LIGHT /1000.0);
			}
			else{
			//高精度伪距
			range = (observerData.getGnssSatGenDisMsInt() + (observerData.getGnssSatGenDisMsRem() / 1024.0)
			+ observerData.getGnssExSigPrePse() * Math.pow(2, -29)) * (GnssConstants.SPEED_OF_LIGHT /1000.0);
			}
			break;
			
			case 1:
			if (precisionType == 0){
			//标准精度载波相位
			range = (observerData.getGnssSatGenDisMsInt() + (observerData.getGnssSatGenDisMsRem() / 1024.0)
			+ (observerData.getGnssSigPhaPse() * Math.pow(2, -29))) * (GnssConstants.SPEED_OF_LIGHT /1000.0);
			}
			else{
			//高精度载波相位
			range = (observerData.getGnssSatGenDisMsInt() + (observerData.getGnssSatGenDisMsRem() / 1024.0)
			+ observerData.getGnssExSigPhaPse() * Math.pow(2, -31)) * (GnssConstants.SPEED_OF_LIGHT /1000.0);
			}
			break;
			default:
			break;
		}
			return range;
	}
	
	/**
	 * @description: 计算真实距离
	 * @param  satPos  卫星坐标
	 * @param  bsPos 基准站坐标
	 * @return  真实距离
	 */
	public static double computeTrueDis(SimpleMatrix satPos, SimpleMatrix bsPos){
		double range = 0;
		range = Math.pow(satPos.get(0, 0) - bsPos.get(0, 0), 2);
		range += Math.pow(satPos.get(1, 0) - bsPos.get(1, 0), 2);
		range += Math.pow(satPos.get(2, 0) - bsPos.get(2, 0), 2);
		range = Math.sqrt(range);
		return range;
	}
}
