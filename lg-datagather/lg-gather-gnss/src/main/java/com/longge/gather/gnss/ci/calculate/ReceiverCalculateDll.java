package com.longge.gather.gnss.ci.calculate;
import com.longge.gather.gnss.ci.invok.DllHelper;
import com.longge.gather.gnss.ci.model.CalOutData;
import com.longge.gather.gnss.ci.model.OriginalData;
import com.longge.gather.gnss.ci.model.StarInfo;
import com.longge.gather.gnss.gnss.calculate.Coordinates;
import com.longge.gather.gnss.gnss.calculate.SatPosCalculate;
import com.longge.gather.gnss.gnss.calculate.TopocentricCoordinates;
import com.longge.gather.gnss.gnss.constant.GnssConstants;
import com.longge.gather.gnss.gnss.model.SatellitePosition;
import com.longge.gather.gnss.server.model.EphemerisData;
import com.longge.gather.gnss.server.model.ObservationSet;
import com.longge.gather.gnss.server.model.Observations;
import com.longge.gather.gnss.server.protocal.rtcm32.msm.constant.BdsSignal;
import com.longge.gather.gnss.server.protocal.rtcm32.msm.constant.GpsSignal;
import com.longge.gather.gnss.server.single.NavDataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
/**
 * @description 接收机高精度定位计算
 * @author jianglong
 * @create 2018-07-17
 **/
public class ReceiverCalculateDll extends Coordinates {

	//星历观测数据缓存对象
	private  static NavDataManager navDataManager = NavDataManager.getInstance();

	//钟差
	private double receiverClockError;

	private static final Logger logger = LoggerFactory.getLogger(ReceiverCalculateDll.class);

	/**构造方法*/
	public ReceiverCalculateDll(){
		super();
		this.setXYZ(0.0, 0.0, 0.0);
		this.receiverClockError = 0.0;
	}
	/**
	 * 双差接收机定位
	 * roverObs     R接收机观测数据
	 * masterObs  M接收机观测数据
	 * masterPos   M接收机定位
	 * */
	public CalOutData codeDoubleDifferences(Observations roverObs, Observations masterObs, Coordinates masterPos, Coordinates roverPos) {
		//判断是否有观测数据 
		if(masterObs.getObsSet() == null || roverObs.getObsSet() == null){
			 return null;
		 }
		/**封装参数--流动站参数*/
		OriginalData roverData = new OriginalData();
		//定位模式   0:Null   1:L1   2:L2   4:L5   8:B1    16:B2   32:B3    64:G1    128:G2   
		roverData.setPosMod((byte)0);
		//动态模式   0：静态  1：动态
		roverData.setDynamic((byte)0);
		//周数
		short[] roleWeek ={(short)(navDataManager.getWeek('G')+1024),navDataManager.getWeek('C'),0};
		roverData.setWeek(roleWeek);
		//电离层参数标记 1:有；0:没有
		roverData.setIonFlag((short)0);
		//周内秒   0:GPS   1:BD   2:Glonass
		double[] desecond = {roverObs.getRefTime().getGpsTime(),roverObs.getRefTime().getGpsTime()-14,0};
		roverData.setDesecond(desecond);
		//接收机位置
		double[] xyzRover = {roverPos.getX(),roverPos.getY(),roverPos.getZ()};
		roverData.setXYZ(xyzRover);
		//卫星信息
		 ArrayList<ObservationSet> obsList = roverObs.getObsSet();
		 List<StarInfo> starInfos = new ArrayList<StarInfo>();
        //星历数据
		 Set<EphemerisData> ephSet = new HashSet<EphemerisData>();
		 for (ObservationSet observationSet : obsList) {
			   //获取星历信息
			 EphemerisData roverEph = navDataManager.findEph(observationSet.getSatType(), observationSet.getSatID());
			 if(roverEph != null){
				 ephSet.add(roverEph);
			 }
			
			   /**GPS系统*/
			   if(observationSet.getSatType() == 'G'){
				    /**GPS-L1频点*/
				   if(!(Double.isNaN(observationSet.getPseudorange(GpsSignal.gps_sig_2.getRinexCode()))) && !(Double.isNaN(observationSet.getPhase(GpsSignal.gps_sig_2.getRinexCode())))){
					   StarInfo starInfo = new StarInfo();
					   //卫星号
					   starInfo.setID((byte)observationSet.getSatID());
					   //频点号--1:L1  2:L2  3:L5  4：B1  5:B2  6:B3  7:G1  8:G2 
					   starInfo.setFreq((byte)1);
					   //伪距(单位:米)
					   starInfo.setPR(observationSet.getPseudorange(GpsSignal.gps_sig_2.getRinexCode()));
					  //载波相位(单位:周)
					   starInfo.setPhase(observationSet.getPhase(GpsSignal.gps_sig_2.getRinexCode()));
					  //多普列(HZ)
					  starInfo.setDoppler(observationSet.getDoppler(GpsSignal.gps_sig_2.getRinexCode()));
					  //信噪比
					  starInfo.setSNR(observationSet.getSigCnr(GpsSignal.gps_sig_2.getRinexCode()));
					 //卫星失锁标志0-7
					  starInfo.setLli((byte)observationSet.getLossLockInd(GpsSignal.gps_sig_2.getRinexCode()));
					  SatellitePosition satellitePosition = SatPosCalculate.computePosition(roverObs,GpsSignal.gps_sig_2.getRinexCode(),roverEph, this.getReceiverClockError());
					  if(satellitePosition !=null){
						  //计算仰角
						  TopocentricCoordinates topocentricCoordinates = new TopocentricCoordinates();
						  Coordinates  roverC = Coordinates.globalXYZInstance(xyzRover[0], xyzRover[1], xyzRover[2]);
						  Coordinates satC = Coordinates.globalXYZInstance(satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ());
						  topocentricCoordinates.computeTopocentricT(roverC, satC);
						  double[] el = {topocentricCoordinates.getElevation(),topocentricCoordinates.getAzimuth()};
						  starInfo.setEl(el);
						  //卫星坐标(米)
						  double[] satXyz = {satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ()};
						  starInfo.setXYZ(satXyz);
						  //卫星XYZ速度(米/秒)
						  double[]  vel = {0.0,0.0,0.0};
						  starInfo.setVel(vel);
						  starInfos.add(starInfo);
					  }
				   }
				    /**GPS-L2频点*/
				   if(!(Double.isNaN(observationSet.getPseudorange(GpsSignal.gps_sig_9.getRinexCode()))) && !(Double.isNaN(observationSet.getPhase(GpsSignal.gps_sig_9.getRinexCode())))){
					   StarInfo starInfo = new StarInfo();
					   //卫星号
					   starInfo.setID((byte)observationSet.getSatID());
					   //频点号--1:L1  2:L2  3:L5  4：B1  5:B2  6:B3  7:G1  8:G2 
					   starInfo.setFreq((byte)2);
					   //伪距(单位:米)
					   starInfo.setPR(observationSet.getPseudorange(GpsSignal.gps_sig_9.getRinexCode()));
					  //载波相位(单位:周)
					   starInfo.setPhase(observationSet.getPhase(GpsSignal.gps_sig_9.getRinexCode()));
					  //多普列(HZ)
					  starInfo.setDoppler(observationSet.getDoppler(GpsSignal.gps_sig_9.getRinexCode()));
					  //信噪比
					  starInfo.setSNR(observationSet.getSigCnr(GpsSignal.gps_sig_9.getRinexCode()));
						 //卫星失锁标志0-7
					  starInfo.setLli((byte)observationSet.getLossLockInd(GpsSignal.gps_sig_9.getRinexCode()));
					  SatellitePosition satellitePosition = SatPosCalculate.computePosition(roverObs,GpsSignal.gps_sig_9.getRinexCode(),roverEph, this.getReceiverClockError());
					  if(satellitePosition !=null){
						  //计算仰角
						  TopocentricCoordinates topocentricCoordinates = new TopocentricCoordinates();
						  Coordinates  roverC = Coordinates.globalXYZInstance(xyzRover[0], xyzRover[1], xyzRover[2]);
						  Coordinates satC = Coordinates.globalXYZInstance(satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ());
						  topocentricCoordinates.computeTopocentricT(roverC, satC);
						  double[] el = {topocentricCoordinates.getElevation(),topocentricCoordinates.getAzimuth()};
						  starInfo.setEl(el);
						  //卫星坐标(米)
						  double[] satXyz = {satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ()};
						  starInfo.setXYZ(satXyz);
						  //卫星XYZ速度(米/秒)
						  double[]  vel = {0.0,0.0,0.0};
						  starInfo.setVel(vel);
						  starInfos.add(starInfo);
					  }
				   }
				    /**GPS-L5频点*/
				   if(!(Double.isNaN(observationSet.getPseudorange(GpsSignal.gps_sig_22.getRinexCode()))) && !(Double.isNaN(observationSet.getPhase(GpsSignal.gps_sig_22.getRinexCode())))){
					   StarInfo starInfo = new StarInfo();
					   //卫星号
					   starInfo.setID((byte)observationSet.getSatID());
					   //频点号--1:L1  2:L2  3:L5  4：B1  5:B2  6:B3  7:G1  8:G2 
					   starInfo.setFreq((byte)3);
					   //伪距(单位:米)
					   starInfo.setPR(observationSet.getPseudorange(GpsSignal.gps_sig_22.getRinexCode()));
					  //载波相位(单位:周)
					   starInfo.setPhase(observationSet.getPhase(GpsSignal.gps_sig_22.getRinexCode()));
					  //多普列(HZ)
					  starInfo.setDoppler(observationSet.getDoppler(GpsSignal.gps_sig_22.getRinexCode()));
					  //信噪比
					  starInfo.setSNR(observationSet.getSigCnr(GpsSignal.gps_sig_22.getRinexCode()));
						 //卫星失锁标志0-7
					  starInfo.setLli((byte)observationSet.getLossLockInd(GpsSignal.gps_sig_22.getRinexCode()));
					  SatellitePosition satellitePosition = SatPosCalculate.computePosition(roverObs,GpsSignal.gps_sig_22.getRinexCode(),roverEph, this.getReceiverClockError());
					  if(satellitePosition !=null){
						  //计算仰角
						  TopocentricCoordinates topocentricCoordinates = new TopocentricCoordinates();
						  Coordinates  roverC = Coordinates.globalXYZInstance(xyzRover[0], xyzRover[1], xyzRover[2]);
						  Coordinates satC = Coordinates.globalXYZInstance(satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ());
						  topocentricCoordinates.computeTopocentricT(roverC, satC);
						  //0:卫星俯仰角(度)  1:方位角(度)
						  double[] el = new double[2];
						  el[0] = topocentricCoordinates.getElevation();
						  el[1] = topocentricCoordinates.getAzimuth();
						  starInfo.setEl(el);
						  //卫星坐标(米)
						  double[] satXyz = {satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ()};
						  starInfo.setXYZ(satXyz);
						  //卫星XYZ速度(米/秒)
						  double[]  vel = {0.0,0.0,0.0};
						  starInfo.setVel(vel);
						  starInfos.add(starInfo);
					  }
				   }
			   }
			   /**BDS系统*/
			   if(observationSet.getSatType() == 'C'){
				    /**BDS-B1频点*/
				   if(!(Double.isNaN(observationSet.getPseudorange(BdsSignal.bds_sig_2.getRinexCode()))) && !(Double.isNaN(observationSet.getPhase(BdsSignal.bds_sig_2.getRinexCode())))){
					   StarInfo starInfo = new StarInfo();
					   //卫星号
					   starInfo.setID((byte)(observationSet.getSatID()+96));
					   //频点号--1:L1  2:L2  3:L5  4：B1  5:B2  6:B3  7:G1  8:G2 
					   starInfo.setFreq((byte)4);
					   //伪距(单位:米)
					   starInfo.setPR(observationSet.getPseudorange(BdsSignal.bds_sig_2.getRinexCode()));
					  //载波相位(单位:周)
					   starInfo.setPhase(observationSet.getPhase(BdsSignal.bds_sig_2.getRinexCode()));
					  //多普列(HZ)
					  starInfo.setDoppler(observationSet.getDoppler(BdsSignal.bds_sig_2.getRinexCode()));
					  //信噪比
					  starInfo.setSNR(observationSet.getSigCnr(BdsSignal.bds_sig_2.getRinexCode()));
						 //卫星失锁标志0-7
					  starInfo.setLli((byte)observationSet.getLossLockInd(BdsSignal.bds_sig_2.getRinexCode()));
					  SatellitePosition satellitePosition = SatPosCalculate.computePosition(roverObs,BdsSignal.bds_sig_2.getRinexCode(),roverEph, this.getReceiverClockError());
					  if(satellitePosition !=null){
						  //计算仰角
						  TopocentricCoordinates topocentricCoordinates = new TopocentricCoordinates();
						  Coordinates  roverC = Coordinates.globalXYZInstance(xyzRover[0], xyzRover[1], xyzRover[2]);
						  Coordinates satC = Coordinates.globalXYZInstance(satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ());
						  topocentricCoordinates.computeTopocentricT(roverC, satC);
						  //0:卫星俯仰角(度)  1:方位角(度)
						  double[] el = {topocentricCoordinates.getElevation(),topocentricCoordinates.getAzimuth()};
						  starInfo.setEl(el);
						  //卫星坐标(米)
						  double[] satXyz = {satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ()};
						  starInfo.setXYZ(satXyz);
						  //卫星XYZ速度(米/秒)
						  double[]  vel = {0.0,0.0,0.0};
						  starInfo.setVel(vel);
						  starInfos.add(starInfo);
					  }
				   }
				    /**BDS-B2频点*/
				   if(!(Double.isNaN(observationSet.getPseudorange(BdsSignal.bds_sig_15.getRinexCode()))) && !(Double.isNaN(observationSet.getPhase(BdsSignal.bds_sig_15.getRinexCode())))){
					   StarInfo starInfo = new StarInfo();
					   //卫星号
					   starInfo.setID((byte)observationSet.getSatID());
					   //频点号--1:L1  2:L2  3:L5  4：B1  5:B2  6:B3  7:G1  8:G2 
					   starInfo.setFreq((byte)5);
					   //伪距(单位:米)
					   starInfo.setPR(observationSet.getPseudorange(BdsSignal.bds_sig_15.getRinexCode()));
					  //载波相位(单位:周)
					   starInfo.setPhase(observationSet.getPhase(BdsSignal.bds_sig_15.getRinexCode()));
					  //多普列(HZ)
					  starInfo.setDoppler(observationSet.getDoppler(BdsSignal.bds_sig_15.getRinexCode()));
					  //信噪比
					  starInfo.setSNR(observationSet.getSigCnr(BdsSignal.bds_sig_15.getRinexCode()));
						 //卫星失锁标志0-7
					  starInfo.setLli((byte)observationSet.getLossLockInd(BdsSignal.bds_sig_15.getRinexCode()));
					  SatellitePosition satellitePosition = SatPosCalculate.computePosition(roverObs,BdsSignal.bds_sig_15.getRinexCode(),roverEph, this.getReceiverClockError());
					  if(satellitePosition !=null){
						  //计算仰角
						  TopocentricCoordinates topocentricCoordinates = new TopocentricCoordinates();
						  Coordinates  roverC = Coordinates.globalXYZInstance(xyzRover[0], xyzRover[1], xyzRover[2]);
						  Coordinates satC = Coordinates.globalXYZInstance(satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ());
						  topocentricCoordinates.computeTopocentricT(roverC, satC);
						  //0:卫星俯仰角(度)  1:方位角(度)
						  double[] el = {topocentricCoordinates.getElevation(),topocentricCoordinates.getAzimuth()};
						  starInfo.setEl(el);
						  //卫星坐标(米)
						  double[] satXyz = {satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ()};
						  starInfo.setXYZ(satXyz);
						  //卫星XYZ速度(米/秒)
						  double[]  vel = {0.0,0.0,0.0};
						  starInfo.setVel(vel);
						  starInfos.add(starInfo);
					  }
				   }
				    /**BDS-B3频点*/
				   if(!(Double.isNaN(observationSet.getPseudorange(BdsSignal.bds_sig_9.getRinexCode()))) && !(Double.isNaN(observationSet.getPhase(BdsSignal.bds_sig_9.getRinexCode())))){
					   StarInfo starInfo = new StarInfo();
					   //卫星号
					   starInfo.setID((byte)observationSet.getSatID());
					   //频点号--1:L1  2:L2  3:L5  4：B1  5:B2  6:B3  7:G1  8:G2 
					   starInfo.setFreq((byte)6);
					   //伪距(单位:米)
					   starInfo.setPR(observationSet.getPseudorange(BdsSignal.bds_sig_9.getRinexCode()));
					  //载波相位(单位:周)
					   starInfo.setPhase(observationSet.getPhase(BdsSignal.bds_sig_9.getRinexCode()));
					  //多普列(HZ)
					  starInfo.setDoppler(observationSet.getDoppler(BdsSignal.bds_sig_9.getRinexCode()));
					  //信噪比
					  starInfo.setSNR(observationSet.getSigCnr(BdsSignal.bds_sig_9.getRinexCode()));
						 //卫星失锁标志0-7
					  starInfo.setLli((byte)observationSet.getLossLockInd(BdsSignal.bds_sig_9.getRinexCode()));
					  SatellitePosition satellitePosition = SatPosCalculate.computePosition(roverObs,BdsSignal.bds_sig_9.getRinexCode(),roverEph, this.getReceiverClockError());
					  if(satellitePosition !=null){
						  //计算仰角
						  TopocentricCoordinates topocentricCoordinates = new TopocentricCoordinates();
						  Coordinates  roverC = Coordinates.globalXYZInstance(xyzRover[0], xyzRover[1], xyzRover[2]);
						  Coordinates satC = Coordinates.globalXYZInstance(satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ());
						  topocentricCoordinates.computeTopocentricT(roverC, satC);
					  //0:卫星俯仰角(度)  1:方位角(度)
						  double[] el = {topocentricCoordinates.getElevation(),topocentricCoordinates.getAzimuth()};
						  starInfo.setEl(el);
						  //卫星坐标(米)
						  double[] satXyz = {satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ()};
						  starInfo.setXYZ(satXyz);
						  //卫星XYZ速度(米/秒)
						  double[]  vel = {0.0,0.0,0.0};
						  starInfo.setVel(vel);
						  starInfos.add(starInfo);
					  }
				   }
			   } 
		 }
		// 卫星数
		 roverData.setSatNum((byte)starInfos.size());
		  //卫星信息
		 StarInfo[] starInfoArray = new  StarInfo[starInfos.size()];
		 for(int i=0;i<starInfos.size();i++){
			 starInfoArray[i] = starInfos.get(i);
		 }
		 roverData.setStarInfor(starInfoArray);
		 
		/**封装参数--基准站参数*/
		OriginalData baseData = new OriginalData();
		//定位模式   0:Null   1:L1   2:L2   4:L5   8:B1    16:B2   32:B3    64:G1    128:G2   
		baseData.setPosMod((short)0);
		//动态模式   0：静态  1：动态
		baseData.setDynamic((short)0);
		//周数
		short[] baseWeek ={(short)(navDataManager.getWeek('G')+1024),navDataManager.getWeek('C'),0};
		baseData.setWeek(baseWeek);
		//电离层参数标记 1:有；0:没有
		baseData.setIonFlag((short)0);
		//周内秒   0:GPS   1:BD   2:Glonass
		double[] desecondBase = {masterObs.getRefTime().getGpsTime(),masterObs.getRefTime().getGpsTime()-14,0};
		baseData.setDesecond(desecondBase);
		//接收机位置
		double[] xyzBase = {masterPos.getX(),masterPos.getY(),masterPos.getZ()};
		baseData.setXYZ(xyzBase);
		//卫星信息
		 ArrayList<ObservationSet> obsListBase = masterObs.getObsSet();
		 List<StarInfo> starInfosBase = new  ArrayList<StarInfo>();
		 for (ObservationSet observationSet : obsListBase) {
			 //获取星历信息
			 EphemerisData masterEph = navDataManager.findEph(observationSet.getSatType(), observationSet.getSatID());
			 if(masterEph != null){
				 ephSet.add(masterEph);
			 }
			   /**GPS系统*/
			   if(observationSet.getSatType() == 'G'){
				    /**GPS-L1频点*/
				   if(!(Double.isNaN(observationSet.getPseudorange(GpsSignal.gps_sig_2.getRinexCode()))) && !(Double.isNaN(observationSet.getPhase(GpsSignal.gps_sig_2.getRinexCode())))){
					   StarInfo starInfo = new StarInfo();
					   //卫星号
					   starInfo.setID((byte)observationSet.getSatID());
					   //频点号--1:L1  2:L2  3:L5  4：B1  5:B2  6:B3  7:G1  8:G2 
					   starInfo.setFreq((byte)1);
					   //伪距(单位:米)
					   starInfo.setPR(observationSet.getPseudorange(GpsSignal.gps_sig_2.getRinexCode()));
					  //载波相位(单位:周)
					   starInfo.setPhase(observationSet.getPhase(GpsSignal.gps_sig_2.getRinexCode()));
					  //多普列(HZ)
					  starInfo.setDoppler(observationSet.getDoppler(GpsSignal.gps_sig_2.getRinexCode()));
					  //信噪比
					  starInfo.setSNR(observationSet.getSigCnr(GpsSignal.gps_sig_2.getRinexCode()));
						 //卫星失锁标志0-7
					  starInfo.setLli((byte)observationSet.getLossLockInd(GpsSignal.gps_sig_2.getRinexCode()));
					  SatellitePosition satellitePosition = SatPosCalculate.computePosition(roverObs,GpsSignal.gps_sig_2.getRinexCode(), masterEph, this.getReceiverClockError());
					  if(satellitePosition !=null){
						  //计算仰角
						  TopocentricCoordinates topocentricCoordinates = new TopocentricCoordinates();
						  Coordinates  baseC = Coordinates.globalXYZInstance(xyzBase[0], xyzBase[1], xyzBase[2]);
						  Coordinates satC = Coordinates.globalXYZInstance(satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ());
						  topocentricCoordinates.computeTopocentricT(baseC, satC);
						  //0:卫星俯仰角(度)  1:方位角(度)
						  double[] el = {topocentricCoordinates.getElevation(),topocentricCoordinates.getAzimuth()};
						  starInfo.setEl(el);
						  //卫星坐标(米)
						  double[] satXyz = {satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ()};
						  starInfo.setXYZ(satXyz);
						  //卫星XYZ速度(米/秒)
						  double[]  vel = {0.0,0.0,0.0};
						  starInfo.setVel(vel);
						  starInfosBase.add(starInfo);
					  }
				   }
				    /**GPS-L2频点*/
				   if(!(Double.isNaN(observationSet.getPseudorange(GpsSignal.gps_sig_9.getRinexCode()))) && !(Double.isNaN(observationSet.getPhase(GpsSignal.gps_sig_9.getRinexCode())))){
					   StarInfo starInfo = new StarInfo();
					   //卫星号
					   starInfo.setID((byte)observationSet.getSatID());
					   //频点号--1:L1  2:L2  3:L5  4：B1  5:B2  6:B3  7:G1  8:G2 
					   starInfo.setFreq((byte)2);
					   //伪距(单位:米)
					   starInfo.setPR(observationSet.getPseudorange(GpsSignal.gps_sig_9.getRinexCode()));
					  //载波相位(单位:周)
					   starInfo.setPhase(observationSet.getPhase(GpsSignal.gps_sig_9.getRinexCode()));
					  //多普列(HZ)
					  starInfo.setDoppler(observationSet.getDoppler(GpsSignal.gps_sig_9.getRinexCode()));
					  //信噪比
					  starInfo.setSNR(observationSet.getSigCnr(GpsSignal.gps_sig_9.getRinexCode()));
						 //卫星失锁标志0-7
					  starInfo.setLli((byte)observationSet.getLossLockInd(GpsSignal.gps_sig_9.getRinexCode()));
					  SatellitePosition satellitePosition = SatPosCalculate.computePosition(roverObs,GpsSignal.gps_sig_9.getRinexCode(), masterEph, this.getReceiverClockError());
					  if(satellitePosition !=null){
						  //计算仰角
						  TopocentricCoordinates topocentricCoordinates = new TopocentricCoordinates();
						  Coordinates  baseC = Coordinates.globalXYZInstance(xyzBase[0], xyzBase[1], xyzBase[2]);
						  Coordinates satC = Coordinates.globalXYZInstance(satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ());
						  topocentricCoordinates.computeTopocentricT(baseC, satC);
						  //0:卫星俯仰角(度)  1:方位角(度)
						  double[] el = {topocentricCoordinates.getElevation(),topocentricCoordinates.getAzimuth()};
						  starInfo.setEl(el);
						  //卫星坐标(米)
						  double[] satXyz = {satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ()};
						  starInfo.setXYZ(satXyz);
						  //卫星XYZ速度(米/秒)
						  double[]  vel = {0.0,0.0,0.0};
						  starInfo.setVel(vel);
						  starInfosBase.add(starInfo);
					  }
				   }
				    /**GPS-L5频点*/
				   if(!(Double.isNaN(observationSet.getPseudorange(GpsSignal.gps_sig_22.getRinexCode()))) && !(Double.isNaN(observationSet.getPhase(GpsSignal.gps_sig_22.getRinexCode())))){
					   StarInfo starInfo = new StarInfo();
					   //卫星号
					   starInfo.setID((byte)observationSet.getSatID());
					   //频点号--1:L1  2:L2  3:L5  4：B1  5:B2  6:B3  7:G1  8:G2 
					   starInfo.setFreq((byte)3);
					   //伪距(单位:米)
					   starInfo.setPR(observationSet.getPseudorange(GpsSignal.gps_sig_22.getRinexCode()));
					  //载波相位(单位:周)
					   starInfo.setPhase(observationSet.getPhase(GpsSignal.gps_sig_22.getRinexCode()));
					  //多普列(HZ)
					  starInfo.setDoppler(observationSet.getDoppler(GpsSignal.gps_sig_22.getRinexCode()));
					  //信噪比
					  starInfo.setSNR(observationSet.getSigCnr(GpsSignal.gps_sig_22.getRinexCode()));
						 //卫星失锁标志0-7
					  starInfo.setLli((byte)observationSet.getLossLockInd(GpsSignal.gps_sig_22.getRinexCode()));
					  SatellitePosition satellitePosition = SatPosCalculate.computePosition(roverObs,GpsSignal.gps_sig_22.getRinexCode(), masterEph, this.getReceiverClockError());
					  if(satellitePosition !=null){
						  //计算仰角
						  TopocentricCoordinates topocentricCoordinates = new TopocentricCoordinates();
						  Coordinates  baseC = Coordinates.globalXYZInstance(xyzBase[0], xyzBase[1], xyzBase[2]);
						  Coordinates satC = Coordinates.globalXYZInstance(satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ());
						  topocentricCoordinates.computeTopocentricT(baseC, satC);
						  //0:卫星俯仰角(度)  1:方位角(度)
						  double[] el = {topocentricCoordinates.getElevation(), topocentricCoordinates.getAzimuth()};
						  starInfo.setEl(el);
						  //卫星坐标(米)
						  double[] satXyz = {satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ()};
						  starInfo.setXYZ(satXyz);
						  //卫星XYZ速度(米/秒)
						  double[]  vel = {0.0,0.0,0.0};
						  starInfo.setVel(vel);
						  starInfosBase.add(starInfo);
					  }
				   }
			   }
			   /**BDS系统*/
			   if(observationSet.getSatType() == 'C'){
				    /**BDS-B1频点*/
				   if(!(Double.isNaN(observationSet.getPseudorange(BdsSignal.bds_sig_2.getRinexCode()))) && !(Double.isNaN(observationSet.getPhase(BdsSignal.bds_sig_2.getRinexCode())))){
					   StarInfo starInfo = new StarInfo();
					   //卫星号
					   starInfo.setID((byte)(observationSet.getSatID()+96));
					   //频点号--1:L1  2:L2  3:L5  4：B1  5:B2  6:B3  7:G1  8:G2 
					   starInfo.setFreq((byte)4);
					   //伪距(单位:米)
					   starInfo.setPR(observationSet.getPseudorange(BdsSignal.bds_sig_2.getRinexCode()));
					  //载波相位(单位:周)
					   starInfo.setPhase(observationSet.getPhase(BdsSignal.bds_sig_2.getRinexCode()));
					  //多普列(HZ)
					  starInfo.setDoppler(observationSet.getDoppler(BdsSignal.bds_sig_2.getRinexCode()));
					  //信噪比
					  starInfo.setSNR(observationSet.getSigCnr(BdsSignal.bds_sig_2.getRinexCode()));
						 //卫星失锁标志0-7
					  starInfo.setLli((byte)observationSet.getLossLockInd(BdsSignal.bds_sig_2.getRinexCode()));
					  SatellitePosition satellitePosition = SatPosCalculate.computePosition(roverObs,GpsSignal.gps_sig_2.getRinexCode(), masterEph, this.getReceiverClockError());
					  if(satellitePosition !=null){
						  //计算仰角
						  TopocentricCoordinates topocentricCoordinates = new TopocentricCoordinates();
						  Coordinates  baseC = Coordinates.globalXYZInstance(xyzBase[0], xyzBase[1], xyzBase[2]);
						  Coordinates satC = Coordinates.globalXYZInstance(satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ());
						  topocentricCoordinates.computeTopocentricT(baseC, satC);
						  //0:卫星俯仰角(度)  1:方位角(度)
						  double[] el = {topocentricCoordinates.getElevation(),topocentricCoordinates.getAzimuth()};
						  starInfo.setEl(el);
						  //卫星坐标(米)
						  double[] satXyz = {satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ()};
						  starInfo.setXYZ(satXyz);
						  //卫星XYZ速度(米/秒)
						  double[]  vel = {0.0,0.0,0.0};
						  starInfo.setVel(vel);
						  starInfosBase.add(starInfo);
					  }
				   }
				    /**BDS-B2频点*/
				   if(!(Double.isNaN(observationSet.getPseudorange(BdsSignal.bds_sig_15.getRinexCode()))) && !(Double.isNaN(observationSet.getPhase(BdsSignal.bds_sig_15.getRinexCode())))){
					   StarInfo starInfo = new StarInfo();
					   //卫星号
					   starInfo.setID((byte)observationSet.getSatID());
					   //频点号--1:L1  2:L2  3:L5  4：B1  5:B2  6:B3  7:G1  8:G2 
					   starInfo.setFreq((byte)5);
					   //伪距(单位:米)
					   starInfo.setPR(observationSet.getPseudorange(BdsSignal.bds_sig_15.getRinexCode()));
					  //载波相位(单位:周)
					   starInfo.setPhase(observationSet.getPhase(BdsSignal.bds_sig_15.getRinexCode()));
					  //多普列(HZ)
					  starInfo.setDoppler(observationSet.getDoppler(BdsSignal.bds_sig_15.getRinexCode()));
					  //信噪比
					  starInfo.setSNR(observationSet.getSigCnr(BdsSignal.bds_sig_15.getRinexCode()));
						 //卫星失锁标志0-7
					  starInfo.setLli((byte)observationSet.getLossLockInd(BdsSignal.bds_sig_15.getRinexCode()));
					  SatellitePosition satellitePosition = SatPosCalculate.computePosition(roverObs,GpsSignal.gps_sig_15.getRinexCode(), masterEph, this.getReceiverClockError());
					  if(satellitePosition !=null){
						  //计算仰角
						  TopocentricCoordinates topocentricCoordinates = new TopocentricCoordinates();
						  Coordinates  baseC = Coordinates.globalXYZInstance(xyzBase[0], xyzBase[1], xyzBase[2]);
						  Coordinates satC = Coordinates.globalXYZInstance(satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ());
						  topocentricCoordinates.computeTopocentricT(baseC, satC);
							  //0:卫星俯仰角(度)  1:方位角(度)
						  double[] el = {topocentricCoordinates.getElevation(),topocentricCoordinates.getAzimuth()};
						  starInfo.setEl(el);
						  //卫星坐标(米)
						  double[] satXyz = {satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ()};
						  starInfo.setXYZ(satXyz);
						  //卫星XYZ速度(米/秒)
						  double[]  vel = {0.0,0.0,0.0};
						  starInfo.setVel(vel);
						  starInfosBase.add(starInfo);
					  }
				   }
				    /**BDS-B3频点*/
				   if(!(Double.isNaN(observationSet.getPseudorange(GpsSignal.gps_sig_9.getRinexCode()))) && !(Double.isNaN(observationSet.getPhase(GpsSignal.gps_sig_9.getRinexCode())))){
					   StarInfo starInfo = new StarInfo();
					   //卫星号
					   starInfo.setID((byte)observationSet.getSatID());
					   //频点号--1:L1  2:L2  3:L5  4：B1  5:B2  6:B3  7:G1  8:G2 
					   starInfo.setFreq((byte)6);
					   //伪距(单位:米)
					   starInfo.setPR(observationSet.getPseudorange(GpsSignal.gps_sig_9.getRinexCode()));
					  //载波相位(单位:周)
					   starInfo.setPhase(observationSet.getPhase(GpsSignal.gps_sig_9.getRinexCode()));
					  //多普列(HZ)
					  starInfo.setDoppler(observationSet.getDoppler(GpsSignal.gps_sig_9.getRinexCode()));
					  //信噪比
					  starInfo.setSNR(observationSet.getSigCnr(GpsSignal.gps_sig_9.getRinexCode()));
						 //卫星失锁标志0-7
					  starInfo.setLli((byte)observationSet.getLossLockInd(GpsSignal.gps_sig_9.getRinexCode()));
					  SatellitePosition satellitePosition = SatPosCalculate.computePosition(roverObs,GpsSignal.gps_sig_9.getRinexCode(), masterEph, this.getReceiverClockError());
					  if(satellitePosition !=null){
						  //计算仰角
						  TopocentricCoordinates topocentricCoordinates = new TopocentricCoordinates();
						  Coordinates  baseC = Coordinates.globalXYZInstance(xyzBase[0], xyzBase[1], xyzBase[2]);
						  Coordinates satC = Coordinates.globalXYZInstance(satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ());
						  topocentricCoordinates.computeTopocentricT(baseC, satC);
							  //0:卫星俯仰角(度)  1:方位角(度)
						  double[] el = {topocentricCoordinates.getElevation(),topocentricCoordinates.getAzimuth()};
						  starInfo.setEl(el);
						  //卫星坐标(米)
						  double[] satXyz = {satellitePosition.getX(),satellitePosition.getY(),satellitePosition.getZ()};
						  starInfo.setXYZ(satXyz);
						  //卫星XYZ速度(米/秒)
						  double[]  vel = {0.0,0.0,0.0};
						  starInfo.setVel(vel);
						  starInfosBase.add(starInfo);
					  }
				   }
			   } 
		 }
		// 卫星数
		 baseData.setSatNum((byte)starInfosBase.size());
		 //卫星信息
		 StarInfo[] starInfoArrayBse = new  StarInfo[starInfosBase.size()];
		 for(int i=0;i<starInfosBase.size();i++){
			 starInfoArrayBse[i] = starInfosBase.get(i);
		 }
		 baseData.setStarInfor(starInfoArrayBse);
		 /**调用DLL计算*/
		 //观测站数据对象
		 DllHelper.ORIGINALDATA.ByReference roverOriginalData = new DllHelper.ORIGINALDATA.ByReference();
		 roverOriginalData.PosMod = roverData.getPosMod();
		 roverOriginalData.Dynamic = roverData.getDynamic();
		 roverOriginalData.week = roverData.getWeek();
		 roverOriginalData.IonFlag = roverData.getIonFlag();
		 roverOriginalData.desecond =  roverData.getDesecond();
		 roverOriginalData.satNum =  roverData.getSatNum();
		 roverOriginalData.XYZ = roverData.getXYZ();
		 for(int i=0;i<roverData.getStarInfor().length;i++){
			 DllHelper.STARINFO.ByValue roverStarInfo = new  DllHelper.STARINFO.ByValue();
			 roverStarInfo.ID = roverData.getStarInfor()[i].getID();
			 roverStarInfo.Freq = roverData.getStarInfor()[i].getFreq();
			 roverStarInfo.PR = roverData.getStarInfor()[i].getPR();
			 roverStarInfo.Phase = roverData.getStarInfor()[i].getPhase();
			 roverStarInfo.Doppler = roverData.getStarInfor()[i].getDoppler();
			 roverStarInfo.SNR = roverData.getStarInfor()[i].getSNR();
			 //roverStarInfo.LLI = roverData.getStarInfor()[i].getLli();
			 roverStarInfo.LLI = 0;
			 roverStarInfo.El = roverData.getStarInfor()[i].getEl();
			 roverStarInfo.XYZ = roverData.getStarInfor()[i].getXYZ();
			 roverStarInfo.Iono = 0.0;
			 roverStarInfo.write();
			 roverOriginalData.StarInfor[i] = roverStarInfo;
		 }
		 //基准站数据对象
		 DllHelper.ORIGINALDATA.ByReference baseOriginalData = new DllHelper.ORIGINALDATA.ByReference();
		 baseOriginalData.PosMod = baseData.getPosMod();
		 baseOriginalData.Dynamic = baseData.getDynamic();
		 baseOriginalData.week = baseData.getWeek();
		 baseOriginalData.IonFlag = baseData.getIonFlag();
		 baseOriginalData.desecond =  baseData.getDesecond();
		 baseOriginalData.satNum =  baseData.getSatNum();
		 baseOriginalData.XYZ = baseData.getXYZ();
		 for(int i=0;i<baseData.getStarInfor().length;i++){
			 DllHelper.STARINFO.ByValue baseStarInfo = new  DllHelper.STARINFO.ByValue();
			 baseStarInfo.ID = baseData.getStarInfor()[i].getID();
			 baseStarInfo.Freq = baseData.getStarInfor()[i].getFreq();
			 baseStarInfo.PR = baseData.getStarInfor()[i].getPR();
			 baseStarInfo.Phase = baseData.getStarInfor()[i].getPhase();
			 baseStarInfo.Doppler = baseData.getStarInfor()[i].getDoppler();
			 baseStarInfo.SNR = baseData.getStarInfor()[i].getSNR();
			 //baseStarInfo.LLI =  baseData.getStarInfor()[i].getLli();
			 baseStarInfo.LLI =  0;
			 baseStarInfo.El = baseData.getStarInfor()[i].getEl();
			 baseStarInfo.XYZ = baseData.getStarInfor()[i].getXYZ();
			 baseStarInfo.Iono=0.0;
			 baseStarInfo.write();
			 baseOriginalData.StarInfor[i] =baseStarInfo;
		 }
		 //星历数据对象
		 DllHelper.NAVDATA2.ByReference navData = new DllHelper.NAVDATA2.ByReference();
		 navData.nNum = ephSet.size();
		 Iterator<EphemerisData> ephInterator =  ephSet.iterator();
		 int i =0;
		 while(ephInterator.hasNext()){
			 EphemerisData ephemerisData = ephInterator.next();
			 DllHelper.EPHDATA.ByValue ephData = new DllHelper.EPHDATA.ByValue();
			 if(ephemerisData.getSatType() == 'C'){
				 ephData.nSat = ephemerisData.getSatID()+96;
			 }else{
				 ephData.nSat = ephemerisData.getSatID();
			 }
			 ephData.nIode = ephemerisData.getIode();
			 ephData.nIodc = ephemerisData.getIodc();
			 ephData.nSva = ephemerisData.getSvAccur();
			 ephData.nSvh = ephemerisData.getSvHealth();
			 ephData.nWeek = ephemerisData.getWeek() + 1024;
			 ephData.nCode = ephemerisData.getL2Code();
			 ephData.nFlag = ephemerisData.getL2Flag();
			 ephData.dblToe = ephemerisData.getToe() > GnssConstants.SEC_IN_WEEK ? ephemerisData.getToe()-GnssConstants.SEC_IN_WEEK: ephemerisData.getToe();
			 ephData.dblToc = ephemerisData.getToc();
	         ephData.dblTtr = 0.0;
	         ephData.dblA = ephemerisData.getRootA()*ephemerisData.getRootA();
	         ephData.dblE = ephemerisData.getE();
	         ephData.dblI0 = ephemerisData.getI0();
	         ephData.dblOMG0 = ephemerisData.getOmega0();
	         ephData.dblOmg  =  ephemerisData.getOmega();
	         ephData.dblM0 = ephemerisData.getM0();
	         ephData.dblDeln = ephemerisData.getDeltaN();
	         ephData.dblOMGd = ephemerisData.getOmegaDot();
	         ephData.dblIdot = ephemerisData.getIDot();
	         ephData.dblCrc = ephemerisData.getCrc();
	         ephData.dblCrs = ephemerisData.getCrs();
	         ephData.dblCuc = ephemerisData.getCuc();
	         ephData.dblCus = ephemerisData.getCus();
	         ephData.dblCic = ephemerisData.getCic();
	         ephData.dblCis = ephemerisData.getCis();
	         ephData.dblToes = ephemerisData.getToe();
	         ephData.dblFit = ephemerisData.getFitInt();
	         ephData.dblAf0 = ephemerisData.getAf0();
	         ephData.dblAf1 = ephemerisData.getAf1();
	         ephData.dblAf2 = ephemerisData.getAf2();
	         ephData.arrTgd = new double[]{ephemerisData.getTgd(), ephemerisData.getTgd2(), 0.0, 0.0};
	         ephData.dblAdot = 0.0;
	         ephData.dblNdot = 0.0;
			 navData.ehpData[i] = ephData;
			 i++;
		 }
		 //输出结果结构
		 DllHelper.CALOUTPUT.ByReference OutPut = new DllHelper.CALOUTPUT.ByReference();
		 //DLL方法调用
		 int  res = DllHelper.getInstance().CalculateRTK2(baseOriginalData,roverOriginalData, navData,OutPut, (short)0);
		 //没有异常
		 if(res == 1){
			 //数据质量判定
			 if(OutPut.Amb_Ratio>= 3){
				 CalOutData calOutData = new CalOutData();
				 calOutData.setBaseLineVectX(OutPut.BaseLineVect[0]);
				 calOutData.setBaseLineVectY(OutPut.BaseLineVect[1]);
				 calOutData.setBaseLineVectZ(OutPut.BaseLineVect[2]);
				 logger.info("时间:"+ roverObs.getRefTime().toString()+ "中间结果X: " + OutPut.BaseLineVect[0] +"Y: " +OutPut.BaseLineVect[1] + "Z: " +OutPut.BaseLineVect[2]);
				 return calOutData;
			}
		 }
          return null;
	}
	


	public double getReceiverClockError() {
		return receiverClockError;
	}

	public void setReceiverClockError(double receiverClockError) {
		this.receiverClockError = receiverClockError;
	}

}
