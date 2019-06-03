package com.longge.gather.gnss.server.protocal.rtcm32.msm.constant;
/**
 * @description 信号常量
 * @author jianglong
 * @create 2018-04-09
 **/
public enum BdsSignal {
    bds_sig_2 (2,"B1","I","2I"),
    bds_sig_3 (3,"B1","Q","2Q"),
    bds_sig_4 (4,"B1","I+Q","2X"),
    bds_sig_8 (8,"B3","I","6I"),
    bds_sig_9 (9,"B3","Q","6Q"),
    bds_sig_10 (10,"B3","I+Q","6X"),
    bds_sig_14 (14,"B2","I","7I"),
    bds_sig_15 (15,"B2","Q","7Q"),
    bds_sig_16 (16,"B2","I+Q","7X");
	
	private int  sigIndex; //信号id
	
    private String frequency; //频段
    
    private String signal; //信号
    
    private String rinexCode; //RINEX 观测值代码
    
    /**构造方法*/  
    private BdsSignal(int sigIndex, String frequency,String signal,String rinexCode) {  
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
   public static BdsSignal getValue(int sigIndex) {  
        for (BdsSignal frequencyConfig : BdsSignal.values()) {  
            if (frequencyConfig.sigIndex == sigIndex) {  
                return frequencyConfig;  
            }  
        }  
        return null;  
    }  
	  
}
