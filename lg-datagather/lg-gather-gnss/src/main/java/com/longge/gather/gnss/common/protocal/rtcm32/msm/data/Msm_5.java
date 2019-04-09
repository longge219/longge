package com.longge.gather.gnss.common.protocal.rtcm32.msm.data;
import com.longge.gather.gnss.common.protocal.rtcm32.msm.head.MsmHead;
import com.longge.gather.gnss.common.protocal.rtcm32.msm.satdata.MsmSatData_57;
import com.longge.gather.gnss.common.protocal.rtcm32.msm.sigdata.MsmSigData_5;
import com.longge.gather.gnss.server.reflect.SubAnnotation;

/**
 * @description MSM_5电文
 * @author jianglong
 * @create 2018-06-14
 **/
public class Msm_5{
	
   @SubAnnotation(type = "object",startPos = 0, len = 0, mark="",className ="com.orieange.common.protocal.rtcm32.msm.head.MsmHead")
   private MsmHead msmHead;
	
  @SubAnnotation(type = "array",startPos = 0, len = 10, mark="",className="com.orieange.common.protocal.rtcm32.msm.satdata.MsmSatData_57")
  private MsmSatData_57[] msmSatDatas;
  
  @SubAnnotation(type = "array",startPos = 0, len = 10, mark="",className="com.orieange.common.protocal.rtcm32.msm.sigdata.MsmSigData_5")
  private MsmSigData_5[] msmSigDatas;
	

}
