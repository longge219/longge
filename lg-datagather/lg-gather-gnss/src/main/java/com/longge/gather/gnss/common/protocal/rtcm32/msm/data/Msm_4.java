package com.longge.gather.gnss.common.protocal.rtcm32.msm.data;
import com.longge.gather.gnss.common.protocal.rtcm32.msm.head.MsmHead;
import com.longge.gather.gnss.common.protocal.rtcm32.msm.satdata.MsmSatData_46;
import com.longge.gather.gnss.common.protocal.rtcm32.msm.sigdata.MsmSigData_4;
import com.longge.gather.gnss.server.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description MSM_4电文
 * @author jianglong
 * @create 2018-06-14
 **/
@Data
public class Msm_4 {
	
	@SubAnnotation(type = "object",startPos = 0, len = 0, mark="",className ="com.longge.gather.gnss.common.protocal.rtcm32.msm.head.MsmHead")
	private MsmHead msmHead;
	
	/**卫星数据的内容*/
    @SubAnnotation(type = "array",startPos = 0, len = 0, mark="byMsmGnssSatNum",className ="com.longge.gather.gnss.common.protocal.rtcm32.msm.satdata.MsmSatData_46")
     private Object[] msmSatDatas;
    
     /** 信号数据的内容*/
     @SubAnnotation(type = "array",startPos = 0, len = 0, mark="byMsmGnssSigNum",className ="com.longge.gather.gnss.common.protocal.rtcm32.msm.sigdata.MsmSigData_4")
     private  Object[] msmSigDatas;


}
