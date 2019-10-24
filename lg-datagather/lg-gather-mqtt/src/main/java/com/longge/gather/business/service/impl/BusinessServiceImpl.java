package com.longge.gather.business.service.impl;
import com.longge.gather.business.constant.KafkaTopicConstant;
import com.longge.gather.business.model.EquipLineData;
import com.longge.gather.business.model.TopicData;
import com.longge.gather.business.service.BusinessService;
import com.longge.gather.business.service.PayloadService;
import com.longge.gather.mqtt.code.PayloadDecode;
import com.longge.gather.mqtt.code.emun.DataType;
import com.longge.gather.mqtt.code.protocol.Payload_3;
import com.longge.gather.mqtt.code.protocol.Payload_4;
import com.longge.gather.mqtt.code.protocol.ProtocolHead;
import com.longge.gather.utils.DateUtils;
import com.longge.plugins.kafka.producer.KafkaProducerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
/**
 * @description MQTT控制报文业务处理
 * @author jianglong
 * @create 2019-10-09
 **/
@Service("businessServiceImpl")
public class BusinessServiceImpl implements BusinessService {

    //kafka生产者
    @Resource
    private KafkaProducerService  kafkaProducerServiceImpl;

    //Payload3业务处理
    @Resource
    private PayloadService payload3ServiceImpl;

    //Payload4业务处理
    @Resource
    private PayloadService payload4ServiceImpl;

    //上传数据点字符标识
    private static final String publishDataFlag = "$dp";

    /**
     *@description: connect、diconnect报文业务处理
     *@param deviceId  设备ID
     *@param isConnect 是上线还是掉线
     *@return boolean
     */
    public void doLinePacket(String deviceId, boolean isConnect){
        EquipLineData equipLineData = new EquipLineData();
        equipLineData.setEquipNum(deviceId);
        equipLineData.setAcqTime(DateUtils.getCurrentTime());
        equipLineData.setConnect(isConnect);
        kafkaProducerServiceImpl.sendMessage(KafkaTopicConstant.EQUIPLINEDATA ,equipLineData);
     }

    /**
     *@description: publish报文业务处理
     *@param topic  主题
     *@param payload  消息内容
     *@return boolean
     */
    public boolean doPublishPacket(String deviceId, String topic, byte[] payload){
        if (StringUtils.isBlank(topic) || payload == null || payload.length==0) {
            return false;
        }else {
            if (topic.equals(publishDataFlag)) {
                PayloadDecode payloadDecode = new PayloadDecode();
                try {
                    ProtocolHead protocolHead = payloadDecode.decode(payload);
                    if (!Objects.isNull(protocolHead)) {
                        if (protocolHead instanceof Payload_3) {
                            Map<String, List<TopicData>> topicDataMap = payload3ServiceImpl.toTopicData(deviceId,protocolHead);
                            for (Map.Entry<String, List<TopicData>> entry : topicDataMap.entrySet()) {
                                 kafkaProducerServiceImpl.sendMessage(entry.getKey() ,entry.getValue());
                            }
                        } else if (protocolHead instanceof Payload_4) {
                            Map<String, List<TopicData>> topicDataMap = payload4ServiceImpl.toTopicData(deviceId,protocolHead);
                            for (Map.Entry<String, List<TopicData>> entry : topicDataMap.entrySet()) {
                                kafkaProducerServiceImpl.sendMessage(entry.getKey() ,entry.getValue());
                            }
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

}
