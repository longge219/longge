package com.longge.gather.gnss.common.protocal.rtcm32.msm.head;
import com.longge.gather.gnss.common.protocal.rtcm32.head.ProtocolHead;
import com.longge.gather.gnss.common.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description MSM头信息
 * @author jianglong
 * @create 2018-06-14
 **/
@Data
public class MsmHead implements ProtocolHead {
	
	/**协议头*/
	@SubAnnotation(type = "uint",startPos = 0, len = 12, mark="",className ="")
	private int protocolHead;
	
	/**参考站ID*/
	@SubAnnotation(type = "uint",startPos = 12, len = 12, mark="",className ="")
	private int rfsID;
	
	/**GNSS历元时刻 ---每个系统不相同*/
	@SubAnnotation(type = "uint",startPos = 24, len = 30, mark="",className ="")
	private int gnssTow;
	
	
	/**
	 * DF393 多电文标志 表示 MSM 后续电文情况：
     * 1=还有相对给定时刻与参考站 ID 的更多电文；
     * 0=本条电文时给定时刻与参考站 ID 的最后一条。
	 * */
	@SubAnnotation(type = "boolean",startPos = 54, len = 1, mark="",className ="")
	private boolean manyFlag;
	
	/**
	 *DF409 表示测站数据期卷号（Issue Of Data Station），为保留字段，用于将
	 *MSM 与今后的测站说明（接收机、天线说明等）联系起来。DF409=0 表示未使用本数据字段。
	 * */
	@SubAnnotation(type = "uint",startPos = 55, len = 3, mark="",className ="")
	private int iods;
	
	
	/**保留--可能每个系统不同*/
	@SubAnnotation(type = "uint",startPos = 58, len = 7, mark="",className ="")
	private int retain;
	
	/**
	 * DF411 时钟校准标志 表示时钟校准的情况。
     * 0=未使用时钟校准，此时，接收机钟差必须保持小于±1ms（约±300km）；
     * 1=使用了时钟校准，此时，接收机钟差必须保持小于±1 微秒（约±300m）；
     * 2=未知的时钟校准状态；
     * 3=保留。 
	 * */
	@SubAnnotation(type = "uint",startPos = 65, len = 2, mark="",className ="")
	private int clockCorrectFlag;
	
	/**
	 * DF412 扩展时钟标志 表示时钟校准的情况。
     * 0=使用内部时钟；
     * 1=使用外部时钟，状态为“锁定”；
     * 2=使用外部时钟，状态为“未锁定”，表示外部时钟失效，传输的数据可能不可靠；
     * 3=使用时钟状态未知。 
	 * */
	@SubAnnotation(type = "uint",startPos = 67, len = 2, mark="",className ="")
	private int exClockFlag;
	
	/**
	 * DF417 表示 GNSS 平滑类型。
	 * 1=使用非无弥散平滑；
	 * 0=其他平滑类型。
	 * */
	@SubAnnotation(type = "boolean",startPos = 69, len = 1, mark="",className ="")
	private boolean gnssSmoothnessTypeFlag;
	
	/**
	 * DF418 是指使用载波平滑伪距的时段长度。注意在卫星可见的整个时间段里可能连续使用非无弥散平滑。
	 * DF418=0 表明未使用平滑，数值与平滑间隔对应见表 11。
	 * */
	@SubAnnotation(type = "uint",startPos = 70, len = 3, mark="",className ="")
	private int gnssSmoothnessInterval;
	
	/**
	 * DF394  GNSS 卫星掩码 给出所观测的 GNSS 卫星情况。每颗卫星对应一个比特位，
	 *  MSB 相当于 ID=1 的 GNSS 卫星，第二位相当于 ID=2 的 GNSS 卫星……，
	 *  LSB 相当于ID=64 的 GNSS 卫星。
	 * */
	@SubAnnotation(type = "String",startPos = 73, len = 64, mark="msmGnssSatNum",className ="")
	private String gnssSatMask;
	
	/**
	 * DF395 GNSS 信号掩码 给出了 GNSS 卫星播发信号的情况。每类信号对应一个比特位，
	 * MSB相当于 ID=1 卫星信号，第二位相当于 ID=2 的卫星信号……，
	 * LSB 相当于ID=32 的卫星信号。
	 * */
	@SubAnnotation(type = "String",startPos = 137, len = 32, mark="msmGnssSigNum",className ="")
	private String gnssSigMask;
	
	/**
	 * DF396 单元掩码 是一个二维表，用于记录每颗卫星的信号类型。
	 * DF396 大小可变，位数按照下式计算：X=Nsig×Nsat			
	 * DF396 形成的二维表中，行表示信号，列表示卫星。
	 * DF396 从最小卫星 ID 的相应列开始按列顺序存储，每列大小均为 Nsig 位，每列从最小信号 ID 的单元格开始。
	 * */
	@SubAnnotation(type = "String",startPos = 169, len = 0, mark="msmGnssEleNum",className ="")
	private String gnssEleMask;
	
}
