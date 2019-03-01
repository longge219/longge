package com.longge.gather.gnss.common.protocal.rtcm32.transparam.projection;
import com.orieange.common.protocal.rtcm32.head.ProtocolHead;
import com.orieange.common.reflect.SubAnnotation;
import lombok.Data;
/**
 * @description 投影参数电文1026
 * @author jianglong
 * @create 2018-04-02
 **/
@Data
public class Projection_1026  implements ProtocolHead{

	@Override
	public int getProtocolHead() {
		return 1026;
	}
	
	@SubAnnotation(type = "short",startPos = 12, len = 8, mark="",className="")
	private short sysId;//系统识别码
	
	@SubAnnotation(type = "byte",startPos = 20, len = 6, mark="",className="")
	private byte projectionType;//投影类型
	
	@SubAnnotation(type = "long",startPos = 26, len = 34, mark="",className="")
	private long laFo;//LaFO
	
	@SubAnnotation(type = "long",startPos = 60, len = 35, mark="",className="")
	private long loFo;//LoFO
	
	@SubAnnotation(type = "long",startPos = 95, len = 34, mark="",className="")
	private long laSP1;//LaSP1
	
	@SubAnnotation(type = "long",startPos = 129, len = 34, mark="",className="")
	private long laSP2;//LaSP2
	
	@SubAnnotation(type = "long",startPos = 163, len = 36, mark="",className="")
	private long efo;//EFO
	
	@SubAnnotation(type = "long",startPos = 199, len = 35, mark="",className="")
	private long nfo;//NFO

}
