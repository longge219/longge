package com.longge.gather.gnss.common.protocal.rtcm32.transparam.projection;
import com.longge.gather.gnss.common.protocal.rtcm32.head.ProtocolHead;
import com.longge.gather.gnss.server.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description 投影参数电文1025
 * @author jianglong
 * @create 2018-04-02
 **/
@Data
public class Projection_1025  implements ProtocolHead {

	@Override
	public int getProtocolHead() {
		return 1025;
	}
	
	@SubAnnotation(type = "short",startPos = 12, len = 8, mark="",className="")
	private short sysId;//系统识别码
	
	@SubAnnotation(type = "byte",startPos = 20, len = 6, mark="",className="")
	private byte projectionType;//投影类型
	
	@SubAnnotation(type = "long",startPos = 26, len = 34, mark="",className="")
	private long laNo;//LaNO 
	
	@SubAnnotation(type = "long",startPos = 60, len = 35, mark="",className="")
	private long loNo;//LoNO 
	
	@SubAnnotation(type = "long",startPos = 95, len = 30, mark="",className="")
	private long snoCorrect;//SNO 修正数
	
	@SubAnnotation(type = "long",startPos = 125, len = 36, mark="",className="")
	private long fe;//FE
	
	@SubAnnotation(type = "long",startPos = 161, len = 35, mark="",className="")
	private long fn;//FN

}
