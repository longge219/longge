package com.longge.gather.gnss.server.reflect;
import com.longge.gather.gnss.common.protocal.rtcm32.arp.Arp_1006;
import com.longge.gather.gnss.common.protocal.rtcm32.assistoperate.ephemeris.BDSEphemeris_1046;
import com.longge.gather.gnss.common.protocal.rtcm32.assistoperate.ephemeris.GpsEphemeris_1019;
import com.longge.gather.gnss.common.protocal.rtcm32.msm.data.*;
import com.longge.gather.gnss.common.protocal.wh.WhBDInoutcInfo;
import com.longge.gather.gnss.common.protocal.wh.WhInoutcInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:封装报文与报文id对应集合
 * @create Author:jianglong
 * @create Date:2018-06-14
 * @version V1.0
 */
public abstract class ClassMap{
	protected static final Map<Integer, ClassHandler> map=new HashMap<Integer, ClassHandler>();
	protected void init(){
		                                                                   /**原始观测电文*/
		 /**RTCM---SMS---GPS*/
		map.put(1071, new ClassHandler(Msm_1.class));
		map.put(1072, new ClassHandler(Msm_2.class));
		map.put(1073, new ClassHandler(Msm_3.class));
		map.put(1074, new ClassHandler(Msm_4.class));
		map.put(1075, new ClassHandler(Msm_5.class));
		map.put(1076, new ClassHandler(Msm_6.class));
		map.put(1077, new ClassHandler(Msm_7.class));
		 /**RTCM---SMS---BDS*/
		map.put(1121, new ClassHandler(Msm_1.class));
		map.put(1122, new ClassHandler(Msm_2.class));
		map.put(1123, new ClassHandler(Msm_3.class));
		map.put(1124, new ClassHandler(Msm_4.class));
		map.put(1125, new ClassHandler(Msm_5.class));
		map.put(1126, new ClassHandler(Msm_6.class));
		map.put(1127, new ClassHandler(Msm_7.class));
		
                                                                 /**星历电文*/
		/**GPS星历电文*/
		map.put(1019, new ClassHandler(GpsEphemeris_1019.class));
		/**BDS星历电文*/
		map.put(4011, new ClassHandler(BDSEphemeris_1046.class));
		map.put(1042, new ClassHandler(BDSEphemeris_1046.class));
		
		
		                                                        /**固定天线参考点说明*/
		map.put(1006, new ClassHandler(Arp_1006.class));
		
		
		   
		                                                       /**武汉导航院自定义协议*/
		//电离层UTC时间参数
		map.put(8, new ClassHandler(WhInoutcInfo.class));
		map.put(2010, new ClassHandler(WhBDInoutcInfo.class));
	}

}
