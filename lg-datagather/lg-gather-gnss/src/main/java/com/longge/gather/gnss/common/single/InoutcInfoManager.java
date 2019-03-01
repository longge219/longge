package com.longge.gather.gnss.common.single;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.longge.gather.gnss.common.model.InoutcData;
/**
 * @description 武汉导航院电离层模型改正参数和UTC时间参数
 * @author jianglong
 * @create 2018年8月22号
 **/
public class InoutcInfoManager {
	
	//单例对象实例
	private final static InoutcInfoManager inoutcInfoManager  =new  InoutcInfoManager();
	
	//基准站对应电文集合
	private static  ConcurrentHashMap<String, HashMap<String, InoutcData>>  inoutcDataMap;
	
    private InoutcInfoManager(){
    	inoutcDataMap = new ConcurrentHashMap<String, HashMap<String,InoutcData>>();
     }
    
    /**单例获取缓存改正参数模型对象*/
    public static  InoutcInfoManager getInstance(){
    	return inoutcInfoManager;
    }
    
    /**添加星历数据*/
	public  void addInoutcData(String receiverId,String type,InoutcData inoutcData) {
		HashMap<String,InoutcData> inoutcMap =  inoutcDataMap.get(receiverId);
		if(inoutcMap !=null){
			inoutcMap.put(type, inoutcData);
		}else{
			HashMap<String,InoutcData> inoutcPraMap =  new HashMap<String,InoutcData>();
			inoutcPraMap.put(type, inoutcData);
			inoutcDataMap.put(receiverId, inoutcPraMap);
		}
	}
	/**获取电离层改正参数*/
	public InoutcData  getInoutcData(String receiverId,String type) {
		HashMap<String,InoutcData> inoutcMap =  inoutcDataMap.get(receiverId);
		if(inoutcMap !=null){
			InoutcData inoutcData = inoutcMap.get(type);
			return inoutcData;
		}
		return null;
	}
	
	 /**判定是否有指定类型电离层参数*/
	public boolean hasInoutcData(String receiverId,String[] types){
		HashMap<String,InoutcData> inoutcMap =  inoutcDataMap.get(receiverId);
		if(inoutcMap !=null){
			for (String type : types) {
				if(!inoutcMap.containsKey(type)){
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
