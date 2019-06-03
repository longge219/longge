package com.longge.gather.gnss.server.model;
import com.longge.gather.gnss.gnss.calculate.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;
/**
 * @description 观测集合对象
 * @author jianglong
 * @create 2018-07-11
 **/
public class Observations {

	SimpleDateFormat sdfHeader = getGMTdf();
	
	DecimalFormat dfX4 = new DecimalFormat("0.0000");

	/**观测时间*/
	private Time refTime; 
	
	/**
	 * 事件标志
	 * 0: OK
	 * 1: power failure between previous and current epoch
	 * >1: Special event
	 *  2: start moving antenna
     *  3: new site occupation (end of kinem. data) (at least MARKER NAME record follows)
     *  4: header information follows
     *  5: external event (epoch is significant)
     *  6: cycle slip records follow to optionally report detected and repaired cycle slips (same format as OBSERVATIONS records; slip instead of observation; LLI and signal strength blank)
	 */
	private int eventFlag; 

	/**观测数据集合*/
	private ArrayList<ObservationSet> obsSet;
	
	/**观测基准站信息*/
	private SiteInfo siteInfo;
	
	/**构造函数*/
	public Observations(Time time, int flag){
		this.refTime = time;
		this.eventFlag = flag;
	}
	
	/**获取GMT时区时刻*/
	public static SimpleDateFormat getGMTdf(){
		SimpleDateFormat sdfHeader = new SimpleDateFormat("dd-MMM-yy HH:mm:ss");
		sdfHeader.setTimeZone( TimeZone.getTimeZone("GMT"));
		return sdfHeader;
	}

	
	/**获取可用观测卫星数*/
	public int getNumSat(){
		if(obsSet == null) return 0;
		int nsat = 0;
		for(int i=0;i<obsSet.size();i++)
			if(obsSet.get(i)!=null) nsat++;
		return obsSet==null?-1:nsat;
	}
	/**根据序号ID获取卫星观测数据*/
	public ObservationSet getSatByIdx(int idx){
		return obsSet.get(idx);
	}
	/**根据卫星ID获取卫星观测数据*/
	public ObservationSet getSatByID(Integer satID){
		if(obsSet == null || satID==null) return null;
		for(int i=0;i<obsSet.size();i++)
			if(obsSet.get(i)!=null && obsSet.get(i).getSatID()==satID.intValue()) {
				return obsSet.get(i);
			}
		    return null;
	}
	
	/**根据卫星号卫星系统类型获取卫星观测数据*/
	public ObservationSet getSatByIDType(Integer satID, char satType){
		if(obsSet == null || satID==null){
			return null;
		} 
		for(int i=0;i<obsSet.size();i++)
			if(obsSet.get(i)!=null && obsSet.get(i).getSatID()==satID.intValue() && obsSet.get(i).getSatType()==satType) {
				return obsSet.get(i);
			}
		return null;
	}
	
	/**根据序号获取该卫星卫星号*/
	public Integer getSatID(int idx){
		return getSatByIdx(idx).getSatID();
	}
	
	/**根据序号获取该卫星卫星类型*/
	public char getGnssType(int idx){
		return getSatByIdx(idx).getSatType();
	}
	
	/**判定是否包含卫星号*/
	public boolean containsSatID(Integer satId){
		return getSatByID(satId) != null;
	}
	
	/**判定是否包含指定卫星类型卫星号*/
	public boolean containsSatIDType(Integer id, Character satType){
		return getSatByIDType(id, satType) != null;
	}

	/**添加观测卫星数据*/
	public void setObservationSet(int i, ObservationSet os){
		if(obsSet==null) obsSet = new ArrayList<ObservationSet>(i+1);
		if(i==obsSet.size()){
			obsSet.add(os);
		}else{
			int c=obsSet.size();
			while(c++<=i) obsSet.add(null);
			obsSet.set(i,os);
		}
	}
	
	public Time getRefTime() {
		return refTime;
	}

	public void setRefTime(Time refTime) {
		this.refTime = refTime;
	}

	public int getEventFlag() {
		return eventFlag;
	}
	
	public void setEventFlag(int eventFlag) {
		this.eventFlag = eventFlag;
	}
	
	public ArrayList<ObservationSet> getObsSet() {
		return obsSet;
	}
	
	public void  setObsSet(ArrayList<ObservationSet> obsList) {
		 this.obsSet = obsList;
	}

	public SiteInfo getSiteInfo() {
		return siteInfo;
	}

	public void setSiteInfo(SiteInfo siteInfo) {
		this.siteInfo = siteInfo;
	}
}
