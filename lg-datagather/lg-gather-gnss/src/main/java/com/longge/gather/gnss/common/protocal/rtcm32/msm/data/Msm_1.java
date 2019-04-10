package com.longge.gather.gnss.common.protocal.rtcm32.msm.data;
import com.longge.gather.gnss.common.protocal.rtcm32.msm.head.MsmHead;
import com.longge.gather.gnss.common.protocal.rtcm32.msm.satdata.MsmSatData_123;
import com.longge.gather.gnss.common.protocal.rtcm32.msm.sigdata.MsmSigData_1;
import com.longge.gather.gnss.server.reflect.SubAnnotation;

/**
 * @description MSM_1电文
 * @author jianglong
 * @create 2018-06-14
 **/
public class Msm_1{
	
   @SubAnnotation(type = "object",startPos = 0, len = 0, mark="",className ="com.longge.gather.gnss.common.protocal.rtcm32.msm.head.MsmHead")
   private MsmHead msmHead;
	
  @SubAnnotation(type = "array",startPos = 0, len = 10, mark="",className="com.longge.gather.gnss.common.protocal.rtcm32.msm.satdata.MsmSatData_123")
  private MsmSatData_123[] msmSatDatas;
  
  @SubAnnotation(type = "array",startPos = 0, len = 10, mark="",className="com.longge.gather.gnss.common.protocal.rtcm32.msm.sigdata.MsmSigData_1")
  private MsmSigData_1[] msmSigDatas;
	

}
