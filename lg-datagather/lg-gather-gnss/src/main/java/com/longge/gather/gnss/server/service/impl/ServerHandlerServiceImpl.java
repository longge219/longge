package com.longge.gather.gnss.server.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import com.longge.gather.gnss.gnss.calculate.PosCalculate;
import com.longge.gather.gnss.gnss.calculate.Time;
import com.longge.gather.gnss.gnss.constant.GnssConstants;
import com.longge.gather.gnss.scan.ScanRunnable;
import com.longge.gather.gnss.server.model.*;
import com.longge.gather.gnss.server.protocal.rtcm32.arp.Arp_1006;
import com.longge.gather.gnss.server.protocal.rtcm32.assistoperate.ephemeris.BDSEphemeris_1046;
import com.longge.gather.gnss.server.protocal.rtcm32.assistoperate.ephemeris.GpsEphemeris_1019;
import com.longge.gather.gnss.server.protocal.rtcm32.msm.constant.BdsSignal;
import com.longge.gather.gnss.server.protocal.rtcm32.msm.constant.GpsSignal;
import com.longge.gather.gnss.server.protocal.rtcm32.msm.data.Msm_4;
import com.longge.gather.gnss.server.protocal.rtcm32.msm.head.MsmHead;
import com.longge.gather.gnss.server.protocal.rtcm32.msm.satdata.MsmSatData_46;
import com.longge.gather.gnss.server.protocal.rtcm32.msm.sigdata.MsmSigData_4;
import com.longge.gather.gnss.server.protocal.wh.WhBDInoutcInfo;
import com.longge.gather.gnss.server.protocal.wh.WhInoutcInfo;
import com.longge.gather.gnss.server.service.ChannelService;
import com.longge.gather.gnss.server.service.ServerHandlerService;
import com.longge.gather.gnss.server.single.NavDataManager;
import com.longge.gather.gnss.server.single.ObsDataManager;
import com.longge.gather.gnss.utils.Bits;
import com.longge.gather.gnss.utils.CollectionUtil;
import com.longge.gather.gnss.utils.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
/**
 * @description 报文业务处理
 * @author jianglong
 * @create 2018-07-10
 **/
@Service
public class ServerHandlerServiceImpl implements ServerHandlerService {
	
	    //日志
	    private static Logger logger = LogManager.getLogger(ServerHandlerServiceImpl.class);

	    //观测数据缓存对象
	    private static ObsDataManager obsDataManager = ObsDataManager.getInstance();

	    //星历观测数据缓存对象
	    private  static NavDataManager navDataManager = NavDataManager.getInstance();

		@Resource
		private ChannelService channelServiceImpl;

		@Resource
		private ScanRunnable scanScheduled;
	/**
	 * @description 处理MSM-4卫星观测数据电文
	 * @param: ReceiverInfo Msm_4
	 * @param: receiverId
	 * @return void
	 */
	@Override
	public void doMsm4(Msm_4 msm4, String siteNo) throws Exception{
		//返回对象集合
		List<ObserverData> obsList = new ArrayList<ObserverData>();
		//卫星号数组
		int[] gnssSatNum = StringUtil.getStrIndex(msm4.getMsmHead().getGnssSatMask());
		//信号数组
		int[] gnssSigNum = StringUtil.getStrIndex(msm4.getMsmHead().getGnssSigMask());
		//单元掩码标志位
		String gnssEleMask  = msm4.getMsmHead().getGnssEleMask();
		 /**MSM头信息*/
		 MsmHead msmHead = msm4.getMsmHead();
		 /**卫星数据的内容*/
		 Object[] msmSatDatas = msm4.getMsmSatDatas();
		 /** 信号数据的内容*/
		 Object[] msmSigDatas = msm4.getMsmSigDatas();
		 //累积单元为0的计数
		 int j = 0;
		 for(int i=0;i<gnssEleMask.length();i++){
			 if(Bits.charToBit(gnssEleMask.charAt(i))){
			   MsmSatData_46 msmSatData46 = (MsmSatData_46) msmSatDatas[i/gnssSigNum.length];
					 MsmSigData_4 msmSigData4 = (MsmSigData_4) msmSigDatas[i-j];
					 ObserverData observerData = new ObserverData();
					 //MSM观测数据头
					 observerData.setProtocolHead(msmHead.getProtocolHead());
					 observerData.setRfsID(msmHead.getRfsID());
					 observerData.setGnssTow(msmHead.getGnssTow());
					 observerData.setManyFlag(msmHead.isManyFlag());
					 observerData.setIods(msmHead.getIods());
					 observerData.setRetain(msmHead.getRetain());
					 observerData.setClockCorrectFlag(msmHead.getClockCorrectFlag());
					 //ogger.info("时钟校准标志"+msmHead.getClockCorrectFlag());
					 observerData.setExClockFlag(msmHead.getExClockFlag());
					 //logger.info(" 扩展时钟标志"+msmHead.getExClockFlag());
					 observerData.setGnssSmoothnessTypeFlag(msmHead.isGnssSmoothnessTypeFlag());
					 //logger.info("GNSS 平滑类型" + msmHead.isGnssSmoothnessTypeFlag());
					 observerData.setGnssSmoothnessInterval(msmHead.getGnssSmoothnessInterval());
					 //logger.info("载波平滑伪距的时段长度"+msmHead.getGnssSmoothnessInterval());
					 observerData.setGnssSatNum(gnssSatNum[i/gnssSigNum.length]);
					 observerData.setGnssSigNum(gnssSigNum[i%gnssSigNum.length]);
					 //MSM卫星数据体
					 observerData.setGnssSatGenDisMsInt(msmSatData46.getGnssSatGenDisMsInt());
					 observerData.setGnssSatGenDisMsRem(msmSatData46.getGnssSatGenDisMsRem());
					 //MSM信号数据体
					 observerData.setGnssSigPrePse(msmSigData4.getGnssSigPrePse());
					 observerData.setGnssSigPhaPse(msmSigData4.getGnssSigPhaPse());
					 observerData.setGnssSigPhaPseCloTimFlag(msmSigData4.getGnssSigPhaPseCloTimFlag());
					 //logger.info("相位距离锁定时间标志"+msmSigData4.getGnssSigPhaPseCloTimFlag());
					 observerData.setHalfCycleBlurMark(msmSigData4.isHalfCycleBlurMark());
					 //logger.info("半周模糊度"+msmSigData4.isHalfCycleBlurMark());
					 observerData.setGnssSigCnr(msmSigData4.getGnssSigCnr());
					 //卫星系统类型
				    int packetID = observerData.getProtocolHead();
						switch(packetID){
					      case 1074:
					    	  observerData.setSatType('G');
					    	  obsList.add(observerData);
							 break;
					      case 1124:
					    	  observerData.setSatType('C');
					    	  obsList.add(observerData);
							 break;
					       default:
					    	    logger.info("暂时不处理其他卫星系统观测数据.................");
		 			    	    break;
						}
			 }else{
				      j++;
			 }
		 }
		 //转换对象
		 Observations observations = getObservations(obsList);
		 if(observations != null){
			 //合并同一时刻的GPS和北斗观测数据m
			 SiteInfo siteInfo = new  SiteInfo(siteNo);
			 observations.setSiteInfo(siteInfo);
			 obsDataManager.addObservations(scanScheduled,observations);
		 }
	 }
	
	
	/**
	 * @description 处理GpsEphemeris_1019 GPS星历电文
	 * @param: GpsEphemeris_1019
	 * @param:  receiverId
	 * @return void
	 */
	@Override
	public void doGpsEphemeris1019(GpsEphemeris_1019 ge1019, String siteNo) throws Exception{
		EphemerisData ephemerisData = new EphemerisData('G',ge1019.getGpsSatelliteId());
		                                              /**数据转换*/
		//DF076--GPS周数
		ephemerisData.setWeek(ge1019.getGpsCirNum());
		//DF077--GPS URA
		ephemerisData.setSvAccur(ge1019.getGpsUra());
		//DF078--GPS L2 测距码标志
		ephemerisData.setL2Flag(Integer.valueOf(ge1019.getGpsL2PseudorangeFlag(),2));
		// DF079--GPS IDOT(单位π/s)
		ephemerisData.setIDot(ge1019.getGpsIdot()*Math.pow(2, -43)* GnssConstants.PI_ORBIT);
		//DF071--GPS IODE
		ephemerisData.setIode(ge1019.getGpsIode());
		//DF081--GPS toc(单位 s)
		ephemerisData.setToc(ge1019.getGpsToc()*Math.pow(2, 4));
		//DF082--GPS af2(单位s/s2)
		ephemerisData.setAf2(ge1019.getGpsAf2()*Math.pow(2, -55));
		//DF083--GPS af1(单位 s/s)
		ephemerisData.setAf1(ge1019.getGpsAf1()*Math.pow(2, -43));
		//DF084--GPS af0(单位 s)
		ephemerisData.setAf0(ge1019.getGpsAf0()*Math.pow(2, -31));
		//DF085--GPS IODC
		ephemerisData.setIodc(ge1019.getGpsIodc());
		//DF086--GPS Crs(单位 m)
		ephemerisData.setCrs(ge1019.getGpsCrs()*Math.pow(2, -5));
		//DF087--GPSΔn(单位 π/s)
		ephemerisData.setDeltaN(ge1019.getGpsN()*Math.pow(2, -43)*GnssConstants.PI_ORBIT);
		//DF088--GPS M0(单位 π)
		ephemerisData.setM0(ge1019.getGpsMo()*Math.pow(2, -31)*GnssConstants.PI_ORBIT);
		//DF089--GPS Cuc (单位 rad)
		ephemerisData.setCuc(ge1019.getGpsCuc()*Math.pow(2, -29));
		//DF090--GPS e(无单位)
		ephemerisData.setE(ge1019.getGpsE()*Math.pow(2, -33));
		//DF091--GPS Cus(单位 rad)
		ephemerisData.setCus(ge1019.getGpsCus()*Math.pow(2, -29));
		//DF092--GPS a1/2(单位 m1/2)
		ephemerisData.setRootA(ge1019.getGpsA()*Math.pow(2, -19));
		//DF093--GPS toe(单位 s)
		ephemerisData.setToe(ge1019.getGpsToe()*Math.pow(2, 4));
		//DF094--GPS Cic(单位 rad)
		ephemerisData.setCic(ge1019.getGpsCic()*Math.pow(2, -29));
		//DF095--GPS Ω0 (单位 π)
		ephemerisData.setOmega0(ge1019.getGpsOmega0()*Math.pow(2, -31)*GnssConstants.PI_ORBIT);
		//DF096--GPS Cis (单位 rad)
		ephemerisData.setCis(ge1019.getGpsCis()*Math.pow(2, -29));
		//DF097--GPS i0(单位 π)
		ephemerisData.setI0(ge1019.getGpsI0()*Math.pow(2, -31)*GnssConstants.PI_ORBIT);
		//DF098--GPS Crc(单位 m)
		ephemerisData.setCrc(ge1019.getGpsCrc()*Math.pow(2, -5));
		//DF099--GPS ω(单位 π)
		ephemerisData.setOmega(ge1019.getGpsW()*Math.pow(2, -31)*GnssConstants.PI_ORBIT);
		//DF100--GPS OMEGADOT(单位 π/s)
		ephemerisData.setOmegaDot(ge1019.getGpsOmegadot()*Math.pow(2, -43)*GnssConstants.PI_ORBIT);
		//DF101--GPS tGD(单位 s)
		ephemerisData.setTgd(ge1019.getGpsTgd()*Math.pow(2, -31));
		//DF102--GPS健康状态
		ephemerisData.setSvHealth(ge1019.getGpsHealth());
		//DF103--GPS L2 P
		if(ge1019.isGpsL2p()){
			ephemerisData.setL2Flag(1);
		}else{
			ephemerisData.setL2Flag(0);
		}
		//星历数据处理
		navDataManager.addEphemerisData(ephemerisData);
	}
	
	/**
	 * @description 处理BDSEphemeris_1046 BDS星历电文
	 * @param: BDSEphemeris
	 * @return void
	 */
	@Override
	public void doBDSEphemeris1046(BDSEphemeris_1046 bdsE1046, String siteNo) throws Exception{
		EphemerisData ephemerisData = new EphemerisData('C',bdsE1046.getBDS_sateliteID());
		                                              /**数据转换*/
		   //BDT 周数，起始于 2006 年 1 月 1 日 UTC 0 点
		    ephemerisData.setWeek(bdsE1046.getBDS_Cirnum());
		   //BDS 卫星的用户距离精度（URA）指数，无单位
		    if( 0<=bdsE1046.getBDS_Urai() && bdsE1046.getBDS_Urai()<6){
		    	ephemerisData.setSvAccur((int)Math.pow( 2, bdsE1046.getBDS_Urai()/2 +1));
		    }else if( 6<=bdsE1046.getBDS_Urai() && bdsE1046.getBDS_Urai()<15){
		    	ephemerisData.setSvAccur((int)Math.pow(2,bdsE1046.getBDS_Urai() - 2));
		    }else{
		    	logger.info("BDS URA无效，该条星历电文无效");
		    	return;
		    }
		   // BDS 卫星轨道倾角变化率，单位 π/s
		    ephemerisData.setIDot(bdsE1046.getBDS_IDOT()*Math.pow(2, -43)*GnssConstants.PI_ORBIT);
		   // BDS 卫星星历数据龄期
		    ephemerisData.setIode(bdsE1046.getBDS_ADODE());
		   //BDS 卫星钟数据参考时刻，单位 s。
		    ephemerisData.setToc(bdsE1046.getBDS_toc()*Math.pow(2, 3));
		   //BDS 卫星钟钟漂改正参数，单位 s/s2
		    ephemerisData.setAf2(bdsE1046.getBDS_a2()*Math.pow(2, -66));
		   //BDS 卫星钟钟速改正参数，单位 s/s
		    ephemerisData.setAf1(bdsE1046.getBDS_a1()*Math.pow(2, -50));
		   //BDS 卫星钟钟差改正参数，单位 s。
		    ephemerisData.setAf0(bdsE1046.getBDS_a0()*Math.pow(2, -33));
		   // BDS 卫星钟时钟数据龄期,无单位。
		    ephemerisData.setIodc(bdsE1046.getBDS_AODC());
		   //BDS 卫星轨道半径正弦调和改正项的振幅，单位 m。
		    ephemerisData.setCrs(bdsE1046.getBDS_Crs()*Math.pow(2, -6));
		   //BDS 卫星平均运动速率与计算值之差，单位 π/s。
		    ephemerisData.setDeltaN(bdsE1046.getBDS_Deltan()*Math.pow(2, -43)*GnssConstants.PI_ORBIT);
		   //BDS 卫星参考时间的平近点角，单位 π。
		    ephemerisData.setM0(bdsE1046.getBDS_M0()*Math.pow(2, -31)*GnssConstants.PI_ORBIT);
		   //BDS 卫星纬度幅角的余弦调和改正项的振幅，单位 rad。
		    ephemerisData.setCuc(bdsE1046.getBDS_Cuc()*Math.pow(2, -31));
		   //BDS 卫星轨道偏心率，无单位。
		    ephemerisData.setE(bdsE1046.getBDS_e()*Math.pow(2, -33));
		   //BDS 卫星纬度幅角的正弦调和改正项的振幅，单位 rad。
		    ephemerisData.setCus(bdsE1046.getBDS_Cus()*Math.pow(2, -31));
		   //BDS 卫星轨道长半轴的平方根 单位 m1/2。
		    ephemerisData.setRootA(bdsE1046.getBDS_sqrta()*Math.pow(2, -19));
		   //BDS 卫星星历数据参考时刻，单位 s。
		    ephemerisData.setToe(bdsE1046.getBDS_toe()*Math.pow(2, 3));
		   //BDS 卫星轨道倾角的余弦调和改正项的振幅，单位 rad。
		    ephemerisData.setCic(bdsE1046.getBDS_Cic()*Math.pow(2, -31));
		   //BDS 卫星按参考时间计算的升交点赤经，单位 π
		    ephemerisData.setOmega0(bdsE1046.getBDS_Omega0()*Math.pow(2, -31)*GnssConstants.PI_ORBIT);
		   //BDS 卫星轨道倾角的正弦调和改正项的振幅，单位 rad。
		    ephemerisData.setCis(bdsE1046.getBDS_Cis()*Math.pow(2, -31));
		   //BDS 卫星参考时间的轨道倾角，单位 π。
		    ephemerisData.setI0(bdsE1046.getBDS_i0()*Math.pow(2, -31)*GnssConstants.PI_ORBIT);
		   //BDS 卫星轨道半径的余弦调和改正项的振幅，单位 m。
		    ephemerisData.setCrc(bdsE1046.getBDS_Crc()*Math.pow(2, -6));
		   //BDS 卫星近地点幅角，单位 π。
		    ephemerisData.setOmega(bdsE1046.getBDS_minorOmega()*Math.pow(2, -31)*GnssConstants.PI_ORBIT);
		   //BDS 卫星升交点赤经变化率，单位 π/s。
		    ephemerisData.setOmegaDot(bdsE1046.getBDS_OmegaDOT()*Math.pow(2, -43)*GnssConstants.PI_ORBIT);
		   //BDS 卫星 B1I 星上设备时延差，单位 ns。
		    ephemerisData.setTgd(bdsE1046.getBDS_TGD1()*Math.pow(10, -10));
		   //BDS 卫星 B2I 星上设备时延差，单位 ns
		    ephemerisData.setTgd2(bdsE1046.getBDS_TGD2()*Math.pow(10, -10));
		   //BDS 卫星健康信息
		    ephemerisData.setSvHealth(Integer.valueOf(String.valueOf(bdsE1046.getBDS_SatHealth().charAt(8))));
		    //卫星自主健康状态
		    ephemerisData.setSvHealth(Integer.valueOf(bdsE1046.getBDS_SatSelfHealth()));
		    //处理星历数据
		    navDataManager.addEphemerisData(ephemerisData);
	}
	
		
    /**
	 * @description 处理固定天线参考点
	 * @param: ProtocolHead
	 * @return void
	 */
	@Override
	public void doArp1006(String channelId, Arp_1006 arp1006) throws Exception{

			if(channelServiceImpl.hasChannel(channelId)){
						channelServiceImpl.deleleChannel(channelId);
						channelServiceImpl.cacheWorkingChannel(channelId, String.valueOf(arp1006.getRfsID()));
			}else{
				channelServiceImpl.cacheWorkingChannel(channelId, String.valueOf(arp1006.getRfsID()));
				logger.info(arp1006.getRfsID()+"授权通过...........");
			}
		     //存储移动站设备的天线坐标
		     SiteInfo siteInfo = new SiteInfo(String.valueOf(arp1006.getRfsID()));
			 siteInfo.setLat(arp1006.getArpEcefX()*Math.pow(10, -4));
			 siteInfo.setLng(arp1006.getArpEcefY()*Math.pow(10, -4));
			 siteInfo.setAlt(arp1006.getArpEcefZ()*Math.pow(10, -4));
	}
	
   /**
	 * @description 处理武汉导航院电离层UTC模型参数协议
	 * @param: WhInoutcData
	 * @return void
	 */
	@Override
	public void doWhGPSInoutcInfo(WhInoutcInfo whInoutcInfo, String receiverId) throws Exception{
		InoutcData inoutcData = new InoutcData();
		inoutcData.setTimeStatus(whInoutcInfo.getWhHead().getTimeStatus());
		inoutcData.setGpsWeekNum(whInoutcInfo.getWhHead().getGpsWeekNum());
		inoutcData.setMs(whInoutcInfo.getWhHead().getMs());
		inoutcData.setBd2LeapSecond(whInoutcInfo.getWhHead().getBd2LeapSecond());
		inoutcData.setAlp0(whInoutcInfo.getAlp0());
		inoutcData.setAlp1(whInoutcInfo.getAlp1());
		inoutcData.setAlp2(whInoutcInfo.getAlp2());
		inoutcData.setAlp3(whInoutcInfo.getAlp3());
		inoutcData.setB0(whInoutcInfo.getB0());
		inoutcData.setB1(whInoutcInfo.getB1());
		inoutcData.setB2(whInoutcInfo.getB2());
		inoutcData.setB3(whInoutcInfo.getB3());
		inoutcData.setUtcWn(whInoutcInfo.getUtcWn());
		inoutcData.setTot(whInoutcInfo.getTot());
		inoutcData.setA0(whInoutcInfo.getA0());
		inoutcData.setA1(whInoutcInfo.getA1());
		inoutcData.setWnlsf(whInoutcInfo.getWnlsf());
		inoutcData.setDn(whInoutcInfo.getDn());
		inoutcData.setDeltatls(whInoutcInfo.getDeltatls());
		inoutcData.setDeltatlsf(whInoutcInfo.getDeltatlsf());
		inoutcData.setDeltatUtc(whInoutcInfo.getDeltatUtc());
		//处理电离层数据
	}
	
    /**
	 * @description: 处理武汉导航院BD电离层UTC模型参数协议
	 * @param: WhInoutcData
	 * @return void
	 */
	public void doWhBDInoutcInfo(WhBDInoutcInfo whBDInoutcInfo, String receiverId) throws Exception{
		InoutcData inoutcData = new InoutcData();
		inoutcData.setTimeStatus(whBDInoutcInfo.getWhHead().getTimeStatus());
		inoutcData.setGpsWeekNum(whBDInoutcInfo.getWhHead().getGpsWeekNum());
		inoutcData.setMs(whBDInoutcInfo.getWhHead().getMs());
		inoutcData.setBd2LeapSecond(whBDInoutcInfo.getWhHead().getBd2LeapSecond());
		inoutcData.setAlp0(whBDInoutcInfo.getAlp0());
		inoutcData.setAlp1(whBDInoutcInfo.getAlp1());
		inoutcData.setAlp2(whBDInoutcInfo.getAlp2());
		inoutcData.setAlp3(whBDInoutcInfo.getAlp3());
		inoutcData.setB0(whBDInoutcInfo.getB0());
		inoutcData.setB1(whBDInoutcInfo.getB1());
		inoutcData.setB2(whBDInoutcInfo.getB2());
		inoutcData.setB3(whBDInoutcInfo.getB3());
		inoutcData.setUtcWn(whBDInoutcInfo.getUtcWn());
		inoutcData.setTot(whBDInoutcInfo.getTot());
		inoutcData.setA0(whBDInoutcInfo.getA0());
		inoutcData.setA1(whBDInoutcInfo.getA1());
		inoutcData.setWnlsf(whBDInoutcInfo.getWnlsf());
		inoutcData.setDn(whBDInoutcInfo.getDn());
		inoutcData.setDeltatls(whBDInoutcInfo.getDeltatls());
		inoutcData.setDeltatlsf(whBDInoutcInfo.getDeltatlsf());
		inoutcData.setDeltatUtc(whBDInoutcInfo.getDeltatUtc());
		//处理电离层数据
	}
	
   /**
	 * @description 观测卫星数据处理
	 * @param: List<ObserverData>
	 * @return: Observations
	 */
	private static Observations getObservations(List<ObserverData> obsDataList){
		 Observations observations = null;
		  Map<Integer, ObservationSet> tempObject = null;
		 if(CollectionUtil.isNotNullAndNotEmpty(obsDataList)){
		    /**填充卫星观测数据*/
		    tempObject =  new HashMap<Integer, ObservationSet>();
		    if(obsDataList.get(0).getSatType() == 'G'){
		    	observations =   new Observations(new Time(obsDataList.get(0).getGnssTow()/GnssConstants.MILLISEC_IN_SEC,0),0);
		    }else if(obsDataList.get(0).getSatType() == 'C'){
		    	observations =   new Observations(new Time(obsDataList.get(0).getGnssTow()/GnssConstants.MILLISEC_IN_SEC+14,0),0);
		    }else{
		       logger.info("暂时不处理其他系统");  
		    }
			for (ObserverData observerData : obsDataList) {
				ObservationSet observationSet = null ;
				//合并卫星ID一样卫星观测数据
				if(tempObject.containsKey(observerData.getSatType()+observerData.getGnssSatNum())){
					observationSet = tempObject.get(observerData.getSatType()+observerData.getGnssSatNum());
				}else{
					 observationSet = new ObservationSet();
				}
				//卫星信息
				 observationSet.setSatType(observerData.getSatType());
				 observationSet.setSatID(observerData.getGnssSatNum());
				 //信号频段
				 if(observerData.getSatType() == 'G'){
					 GpsSignal gpsSignal = GpsSignal.getValue(observerData.getGnssSigNum());
					 if(gpsSignal != null){
						 PosCalculate posCalculate = new PosCalculate();
						 observationSet.getPseudorangeMap().put(gpsSignal.getRinexCode(),  posCalculate.computeObsDis(observerData,0));
						 observationSet.getPhaseMap().put(gpsSignal.getRinexCode(),  posCalculate.computeObsDis(observerData,1));
						 observationSet.getSigCnrMap().put(gpsSignal.getRinexCode(), observerData.getGnssSigCnr());
						 observationSet.getLossLockInd().put(gpsSignal.getRinexCode(), observerData.getGnssSigPhaPseCloTimFlag());
						 tempObject.put(observerData.getSatType()+observerData.getGnssSatNum(), observationSet);
					 }
				 }else if(observerData.getSatType() =='C'){
					 BdsSignal bdsSignal = BdsSignal.getValue(observerData.getGnssSigNum());
					 if(bdsSignal != null){
						 PosCalculate posCalculate = new PosCalculate();
						 observationSet.getPseudorangeMap().put(bdsSignal.getRinexCode(),  posCalculate.computeObsDis(observerData,0));
						 observationSet.getPhaseMap().put(bdsSignal.getRinexCode(),  posCalculate.computeObsDis(observerData,1));
						 observationSet.getSigCnrMap().put(bdsSignal.getRinexCode(), observerData.getGnssSigCnr());
						 observationSet.getLossLockInd().put(bdsSignal.getRinexCode(), observerData.getGnssSigPhaPseCloTimFlag());
						 tempObject.put(observerData.getSatType()+observerData.getGnssSatNum(), observationSet);
					 }
				 }

			}
		    ArrayList<ObservationSet> obsSetList =  new ArrayList<ObservationSet>();
		    for(ObservationSet obsData : tempObject.values()){
		    	obsSetList.add(obsData);
		    }
		    observations.setEventFlag(0);
			observations.setObsSet(obsSetList);
		}else{
			logger.info("未收到观测数据");
		}
			return observations;
	}
   

}