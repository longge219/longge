package com.longge.gather.gnss.server.single;
import com.longge.gather.gnss.server.model.EphemerisData;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
/**
 * @Description:星历数据缓存
 * @create Author:jianglong
 * @create 2018-09-04
 */
public class NavDataManager {

    private final static NavDataManager navDataManager = new NavDataManager();

    //观测数据
    private static ConcurrentMap<String, EphemerisData> obsMap;

    private NavDataManager(){
        obsMap = new ConcurrentHashMap<String, EphemerisData>();
    }

    public static NavDataManager getInstance() {
        return navDataManager;
    }

    public void  addEphemerisData(EphemerisData ephemerisData){
        obsMap.put(ephemerisData.toString(),ephemerisData);
    }


}
