package com.longge.gather.mqtt.code.reflect;
import com.longge.gather.mqtt.code.protocol.Payload_3;
import com.longge.gather.mqtt.code.protocol.Payload_4;

import java.util.HashMap;
import java.util.Map;
/**
 * @Description:封装报文与报文id对应集合
 * @create Author:jianglong
 * @create Date:2019-09-11
 * @version V1.0
 */
public abstract class ClassMap{
	protected static final Map<Integer,ClassHandler> map=new HashMap<Integer, ClassHandler>();
	protected void init(){
		map.put(3, new ClassHandler(Payload_3.class));
		map.put(3, new ClassHandler(Payload_4.class));
	}

}
