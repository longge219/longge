package com.longge.gather.gnss.common.single;
import java.util.concurrent.ConcurrentHashMap;

import com.longge.gather.gnss.common.model.EphemerisData;
import com.longge.gather.gnss.gnss.model.Satellite;
/**
 * @description 缓存星历信息数据
 * @author jianglong
 * @create 2018年6月21号
 **/
public class EphemerisManager {
	
	//单例对象实例
	private final static EphemerisManager ephemerisManager = new EphemerisManager();
	
	//基准站对应电文集合
	private static ConcurrentHashMap<Satellite, EphemerisData> ephemerisMessage ;
	
    private EphemerisManager(){
    	ephemerisMessage = new ConcurrentHashMap<Satellite, EphemerisData>();
     }
    
    /**单例获取缓存星历对象*/
    public static  EphemerisManager getInstance(){
    	return ephemerisManager;
    }
    
    /**获取星历观测对象*/
    public EphemerisData getEphemeris(Satellite satellite){
    	for (Satellite satelliteT : ephemerisMessage.keySet()) {
			if(satellite.toString().equals(satelliteT.toString())){
				return ephemerisMessage.get(satellite);
			}
		}
    	return null;
    }
    
    /**添加星历观测对象*/
    public void  addEphemeris(EphemerisData  ephemerisData){
    	Satellite satellite =  new Satellite(ephemerisData.getSatelliteType(),ephemerisData.getSatelliteId());
		ephemerisMessage.put(satellite, ephemerisData);
    }
    
    /**获取GPS周数*/
    public Integer getGpsCirNum(){
    	if(!ephemerisMessage.isEmpty()){
    		for (EphemerisData ephemerisData : ephemerisMessage.values()) {
    			if(ephemerisData.getSatelliteType()=='G'){
    				return ephemerisData.getGpsCirNum();
    			}
			 }
    	 }
    	return null;
     }
    
}
