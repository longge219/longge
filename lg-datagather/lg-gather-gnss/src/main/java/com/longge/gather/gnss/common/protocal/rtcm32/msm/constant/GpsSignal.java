package com.longge.gather.gnss.common.protocal.rtcm32.msm.constant;
/**
 * @description 信号常量
 * @author jianglong
 * @create 2018-04-09
 **/
public enum GpsSignal {
	
    gps_sig_2 (2,"L1","C/A","1C"),
    gps_sig_3 (3,"L1","P","1P"),
    gps_sig_4 (4,"L1","Z","1W"),
    gps_sig_8 (8,"L2","C/A","2C"),
    gps_sig_9 (9,"L2","P","2P"),
    gps_sig_10 (10,"L2","Z","2W"),
    gps_sig_15 (15,"L2","L2C(M)","2S"),
    gps_sig_16 (16,"L2","L2C(L)","2L"),
    gps_sig_17 (17,"L2","L2C(M+L)","2X"),
    gps_sig_22 (22,"L5","I","5I"),
    gps_sig_23 (23,"L5","Q","5Q"),
    gps_sig_24 (24,"L5","I+Q","5X"),
    gps_sig_30 (30,"L1","L1C-D",""),
    gps_sig_31 (31,"L1","L1C-P",""),
    gps_sig_32 (32,"L1","L1C-(D+P)","") ;
	
	private int  sigIndex; //GPS信号id
	
    private String frequency; //频段
    
    private String signal; //信号
    
    private String rinexCode; //RINEX 观测值代码
    
    /**构造方法*/  
    private GpsSignal(int sigIndex, String frequency,String signal,String rinexCode) {  
        this.sigIndex = sigIndex;  
        this.frequency = frequency;  
        this.signal = signal;
        this.rinexCode = rinexCode;
    }  
     
    public int getSigIndex() {
		return sigIndex;
	}

	public void setSigIndex(int sigIndex) {
		this.sigIndex = sigIndex;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getSignal() {
		return signal;
	}

	public void setSignal(String signal) {
		this.signal = signal;
	}

   public String getRinexCode() {
		return rinexCode;
	}

	public void setRinexCode(String rinexCode) {
		this.rinexCode = rinexCode;
	}

/**根据名称获取值*/
   public static GpsSignal getValue(int sigIndex) {  
        for (GpsSignal frequencyConfig : GpsSignal.values()) {  
            if (frequencyConfig.sigIndex == sigIndex) {  
                return frequencyConfig;  
            }  
        }  
        return null;  
    }  
	  
}
