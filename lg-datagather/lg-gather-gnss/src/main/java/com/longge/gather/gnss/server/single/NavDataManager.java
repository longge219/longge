package com.longge.gather.gnss.server.single;
import com.longge.gather.gnss.server.model.EphemerisData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
/**
 * @Description:星历数据缓存
 * @create Author:jianglong
 * @create 2018-09-04
 */
public class NavDataManager {

    private static final Logger logger  = LoggerFactory.getLogger(NavDataManager.class);

    private final static NavDataManager navDataManager = new NavDataManager();

    //观测数据
    private static ConcurrentMap<String, EphemerisData>  navMap;

    private NavDataManager(){
        navMap = new ConcurrentHashMap<String, EphemerisData>();
    }

    public static NavDataManager getInstance() {
        return navDataManager;
    }

    /**添加导航数据--卫星类型和ID作为key*/
    public void  addEphemerisData(EphemerisData ephemerisData){
        navMap.put(ephemerisData.toString(),ephemerisData);
    }

    /**获取周数*/
    public short getWeek(char satType){
        for(EphemerisData ephemerisData:navMap.values()){
            if(ephemerisData.getSatType()== satType){
               if(satType == 'G'){
                   return (short)ephemerisData.getWeek();
               }else if(satType == 'C'){
                   return (short)ephemerisData.getWeek();
               }else{
                   logger.info("暂是不处理非GPS和北斗系统的数据");
                   return 0;
               }
            }
        }
        return 0;
    }
    /**根据观测数据的卫星类型和编号查找*/
    public EphemerisData findEph(char satType,int satID){
        for(EphemerisData ephemerisData:navMap.values()){
            if(ephemerisData.getSatType()==satType && ephemerisData.getSatID()== satID){
                return ephemerisData;
            }
        }
        return null;
    }

}
