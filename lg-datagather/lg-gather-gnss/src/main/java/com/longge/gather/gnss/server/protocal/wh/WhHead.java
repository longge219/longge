package com.longge.gather.gnss.server.protocal.wh;
import com.longge.gather.gnss.server.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description 武汉导航院协议头部
 * @author jianglong
 * @create 2018-08-17
 **/
@Data
public class WhHead {
	
	@SubAnnotation(type = "byte",startPos = 6, len = 1, mark="",className="")
	public byte messageType;//消息类型(00 = Binary 01 = ASCII  10 = Abbreviated ASCII)
	
	@SubAnnotation(type = "byte",startPos = 7, len = 1, mark="",className="")
	public byte portAddress;//端口地址
	
	@SubAnnotation(type = "ushort",startPos = 8, len = 2, mark="",className="")
	public int messageLength;//消息长度
	
	@SubAnnotation(type = "ushort",startPos = 10, len = 2, mark="",className="")
	public int  reserved;//预留
	
	@SubAnnotation(type = "byte",startPos = 12, len = 1, mark="",className="")
	public byte idleTime;//时间频率
	
	@SubAnnotation(type = "byte",startPos = 13, len = 1, mark="",className="")
	public byte timeStatus;//gps时间质量
	
	@SubAnnotation(type = "ushort",startPos = 14, len = 2, mark="",className="")
	public int gpsWeekNum;//gps周数
	
	@SubAnnotation(type = "uint",startPos = 16, len = 4, mark="",className="")
	public long ms;//从GPS第一周开始数量
	
	@SubAnnotation(type = "uint",startPos = 20, len = 4, mark="",className="")
	public long  reserved2;//预留
	
	@SubAnnotation(type = "ushort",startPos = 24, len = 2, mark="",className="")
	public int  bd2LeapSecond;//北斗2跳秒数
	
	@SubAnnotation(type = "ushort",startPos = 26, len = 2, mark="",className="")
	public int  reserved3;//预留
}
