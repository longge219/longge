package com.longge.gather.gnss.common.protocal.rtcm32.msm.data;

import com.longge.gather.gnss.common.protocal.rtcm32.msm.head.MsmHead;
import com.longge.gather.gnss.common.protocal.rtcm32.msm.satdata.MsmSatData_46;
import com.longge.gather.gnss.common.protocal.rtcm32.msm.sigdata.MsmSigData_6;
import com.longge.gather.gnss.common.reflect.SubAnnotation;

/**
 * @description MSM_6电文
 * @author jianglong
 * @create 2018-06-14
 **/
public class Msm_6{
	
   @SubAnnotation(type = "object",startPos = 0, len = 0, mark="",className ="com.orieange.common.protocal.rtcm32.msm.head.MsmHead")
   private MsmHead msmHead;
	
  @SubAnnotation(type = "array",startPos = 0, len = 10, mark="",className="com.orieange.common.protocal.rtcm32.msm.satdata.MsmSatData_46")
  private MsmSatData_46[] msmSatDatas;
  
  @SubAnnotation(type = "array",startPos = 0, len = 10, mark="",className="com.orieange.common.protocal.rtcm32.msm.sigdata.MsmSigData_6")
  private MsmSigData_6[] msmSigDatas;
	

}
