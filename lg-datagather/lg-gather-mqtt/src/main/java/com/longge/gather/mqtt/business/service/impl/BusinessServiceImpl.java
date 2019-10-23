package com.longge.gather.mqtt.business.service.impl;
import com.longge.gather.mqtt.business.service.BusinessService;
import com.longge.gather.mqtt.code.PayloadDecode;
import com.longge.gather.mqtt.code.protocol.Payload_3;
import com.longge.gather.mqtt.code.protocol.Payload_4;
import com.longge.gather.mqtt.code.protocol.ProtocolHead;
import com.longge.gather.mqtt.common.fastjson.FastJsonUtils;
import com.longge.plugins.kafka.producer.KafkaProducerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * @description MQTT控制报文业务处理
 * @author jianglong
 * @create 2019-09-09
 **/
@Service("businessServiceImpl")
public class BusinessServiceImpl implements BusinessService {

    //kafka生产者
    @Resource
    private KafkaProducerService  kafkaProducerServiceImpl;

    //上传数据点字符标识
    private static final String publishDataFlag = "$dp";

    //kafka主题消息
    private static final String kafkaTopic = "rtudata";

    /**
     *@description: publish报文业务处理
     *@param topic  主题
     *@param payload  消息内容
     *@return boolean
     */
    public boolean doPublishPacket(String topic, byte[] payload){
        if (StringUtils.isBlank(topic) || payload == null || payload.length==0) {
            return false;
        }else {
            if (topic.equals(publishDataFlag)) {
                PayloadDecode payloadDecode = new PayloadDecode();
                try {
                    ProtocolHead protocolHead = payloadDecode.decode(payload);
                    if (!Objects.isNull(protocolHead)) {
                        if (protocolHead instanceof Payload_3) {
                            Payload_3 payload_3 = (Payload_3) protocolHead;
                            kafkaProducerServiceImpl.sendMessage(kafkaTopic, payload_3.getPublishDataStr());
                            Map<String, String> pubData = FastJsonUtils.jsonToMap(payload_3.getPublishDataStr());
                            for (Map.Entry<String, String> entry : pubData.entrySet()) {
                                String key = entry.getKey();
                                String value = String.valueOf(entry.getValue());
                                System.out.println("key:" + key);
                                System.out.println("value:" + value);
                            }
                        } else if (protocolHead instanceof Payload_4) {
                            Payload_4 payload_4 = (Payload_4) protocolHead;
                            Map<String, String> pubData = FastJsonUtils.jsonToMap(payload_4.getPublishDataStr());
                            for (Map.Entry<String, String> entry : pubData.entrySet()) {
                                String key = entry.getKey();
                                String value = String.valueOf(entry.getValue());
                                System.out.println("key:" + key);
                                System.out.println("value:" + value);
                            }
                            kafkaProducerServiceImpl.sendMessage(kafkaTopic, payload_4.getPublishDataStr());
                        } else {
                            System.out.println("未定义处理");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("MQTT未定义的publish主题消息"+topic);
            }
        }
        return true;
    }

    private void  doPayload_3(Map<String,String> pubData){
        if(!pubData.isEmpty()){
            for(Map.Entry<String,String> entry: pubData.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();
            }
        }
    }
}
