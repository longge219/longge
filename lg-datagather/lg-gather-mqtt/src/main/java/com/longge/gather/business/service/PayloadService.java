package com.longge.gather.business.service;
import com.longge.gather.business.model.TopicData;
import com.longge.gather.mqtt.code.protocol.ProtocolHead;
import java.util.List;
import java.util.Map;
/**
 * @description Payload3内容协议业务处理接口
 * @author jianglong
 * @create 2019-10-24
 **/
public interface PayloadService {
    Map<String,List<TopicData>> toTopicData(String deviceId, ProtocolHead protocolHead);
}
