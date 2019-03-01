package com.longge.gather.gnss.gnss.model;
import java.util.HashMap;
import java.util.Map;
/**
 * @description 对同一时刻和一个卫星的观测
 * @author jianglong
 * @create 2018-07-11
 **/
public class ObservationSet{
	
	//卫星系统类型
	private char satType;
	
	//卫星编号
	private int satID; 
	
	//伪距
	private Map<String,Double> pseudorangeMap = new HashMap<String,Double>();
	
	//相位
	private Map<String,Double> phaseMap = new HashMap<String,Double>();
	
	//信号CNR
	private  Map<String,Integer> sigCnrMap = new HashMap<String,Integer>();
	
	/*
	 *  失锁指示 (LLI). Range: 0-7
	 *  0 or blank: OK or not known
	 *  Bit 0 set : 在以前和当前观测之间丢失的锁：可能的循环滑移
	 *  Bit 1 set : 卫星所定义的另一个波长为1/2线的波长的相反波长因子。仅适用于当前时刻.
	 *  Bit 2 set : 在反欺骗下的观察（可能会增加噪音）
	 *  Bits 0 and 1 for phase only.
	 */
	private Map<String,Integer>  lossLockInd = new HashMap<String,Integer>();

	//观测值是否可用
	private boolean inUse = true;


	public int getSatID() {
		return satID;
	}

	public void setSatID(int satID) {
		this.satID = satID;
	}

	public char getSatType() {
		return satType;
	}

	public void setSatType(char satType) {
		this.satType = satType;
	}
	
	public Map<String, Double> getPseudorangeMap() {
		return pseudorangeMap;
	}

	public Map<String, Double> getPhaseMap() {
		return phaseMap;
	}

	public Map<String, Integer> getSigCnrMap() {
		return sigCnrMap;
	}
	
	public Map<String, Integer> getLossLockInd() {
		return lossLockInd;
	}


	public void setPseudorange(String sigCode, Double pseudorangeValue){
		pseudorangeMap.put(sigCode, pseudorangeValue);
	}
	
	public double getPseudorange(String sigCode){
	  if(pseudorangeMap.containsKey(sigCode)){
		  return pseudorangeMap.get(sigCode);
	  }
	     return  Double.NaN;
	}
	
	public void setPhase(String sigCode, Double phaseValue){
		phaseMap.put(sigCode, phaseValue);
	}
	
	public double getPhase(String sigCode){
	  if(phaseMap.containsKey(sigCode)){
		  return phaseMap.get(sigCode);
	  }
	     return  Double.NaN;
	}
	
	public void setSigCnr(String sigCode, int sigCnrValue){
		sigCnrMap.put(sigCode, sigCnrValue);
	}
	
	public int getSigCnr(String sigCode){
	  if(sigCnrMap.containsKey(sigCode)){
		  return sigCnrMap.get(sigCode);
	  }
	     return  0;
	}
	
	public void setLossLockInd(String sigCode, int lossLockIndValue){
		lossLockInd.put(sigCode, lossLockIndValue);
	}
	
	public int getLossLockInd(String sigCode){
	  if(lossLockInd.containsKey(sigCode)){
		  return lossLockInd.get(sigCode);
	  }
	     return  0;
	}

	public boolean isInUse() {
	    return inUse;
	}
	
	public void setInUse(boolean inUse) {
	    this.inUse = inUse;
	}

	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ObservationSet){
			return ((ObservationSet)obj).getSatID() == satID &&((ObservationSet)obj).getSatType() ==  satType;
		}else{
			return super.equals(obj);
		}
	}
}