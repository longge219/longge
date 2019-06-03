package com.longge.gather.gnss.server.service;

import com.longge.gather.gnss.server.protocal.rtcm32.arp.Arp_1006;
import com.longge.gather.gnss.server.protocal.rtcm32.assistoperate.ephemeris.BDSEphemeris_1046;
import com.longge.gather.gnss.server.protocal.rtcm32.assistoperate.ephemeris.GpsEphemeris_1019;
import com.longge.gather.gnss.server.protocal.rtcm32.msm.data.Msm_4;
import com.longge.gather.gnss.server.protocal.wh.WhBDInoutcInfo;
import com.longge.gather.gnss.server.protocal.wh.WhInoutcInfo;

/**
 * @description 服务端报文业务处理接口
 * @author jianglong
 * @create 2018-03-20
 **/
public interface ServerHandlerService {
	
	/**
	 * @description 处理MSM-4卫星观测数据电文
	 * @param: Msm_4
	 * @param receiverId
	 * @return void
	 */
	public void doMsm4(Msm_4 msm4, String receiverId) throws Exception;
	
	/**
	 * @description 处理GpsEphemeris_1019 GPS星历电文
	 * @param: GpsEphemeris_1019
	 * @param  receiverId
	 * @return void
	 */
	public void doGpsEphemeris1019(GpsEphemeris_1019 ge1019, String receiverId) throws Exception;
	
	/**
	 * @description 处理BDSEphemeris_1046 BDS星历电文
	 * @param: BDSEphemeris
	 * @return void
	 */
	public void doBDSEphemeris1046(BDSEphemeris_1046 bdsE1046, String receiverId) throws Exception;
	
	
    /**
	 * @description 处理固定天线参考点
	 * @param: ProtocolHead
	 * @return void
	 */
	public void doArp1006(String channelId, Arp_1006 arp1006) throws Exception;
	
    /**
	 * @description 处理武汉导航院GPS电离层UTC模型参数协议
	 * @param: WhInoutcData
	 * @return void
	 */
	public void doWhGPSInoutcInfo(WhInoutcInfo whInoutcInfo, String receiverId) throws Exception;
	
    /**
	 * @description 处理武汉导航院BD电离层UTC模型参数协议
	 * @param: WhInoutcData
	 * @return void
	 */
	public void doWhBDInoutcInfo(WhBDInoutcInfo whBDInoutcInfo, String receiverId) throws Exception;

}
