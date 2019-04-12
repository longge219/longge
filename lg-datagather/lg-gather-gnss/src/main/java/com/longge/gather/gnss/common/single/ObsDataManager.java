package com.longge.gather.gnss.common.single;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import com.longge.gather.gnss.gnss.model.ObservationSet;
import com.longge.gather.gnss.gnss.model.Observations;
import com.longge.gather.gnss.scan.ScanRunnable;
/**
 * @Description:缓存观测数据集合
 * @create Author:jianglong
 * @create 2018-09-04
 */
public class ObsDataManager {

	private final static ObsDataManager obsDataManager = new ObsDataManager();

	private static ConcurrentMap<String, Observations> obsMap;

	private static ScanRunnable scanScheduled;

	 private ObsDataManager(){
		 obsMap = new ConcurrentHashMap<String, Observations>();
	 }
	
	public static ObsDataManager getInstance(ScanRunnable scanScheduled) {
		scanScheduled = scanScheduled;
		 return obsDataManager;
	}
	
	public void addObservations(Observations observations){
		String key = observations.getRefTime().getSecNum()+"-"+observations.getSiteInfo().getSiteNo();
		if(obsMap.containsKey(key)){
			Observations observationsOld = obsMap.get(key);
			ArrayList<ObservationSet> obsSet = observations.getObsSet();
			//logger.info("合并同一时刻数据");
			for (ObservationSet observationSet : obsSet) {
				observationsOld.getObsSet().add(observationSet);
			}
			scanScheduled.addQueue(observationsOld);
			obsMap.remove(key);
		}else{
			//logger.info("第一次添加数据");
			obsMap.put(key, observations);
		}
	}

	public  ConcurrentMap<String, Observations> getObsMap() {
		return obsMap;
	}	
}
