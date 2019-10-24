package com.longge.gather.business.service.impl;
import com.longge.gather.business.constant.KafkaTopicConstant;
import com.longge.gather.business.model.EquipAcqData;
import com.longge.gather.business.model.TopicData;
import com.longge.gather.business.service.PayloadService;
import com.longge.gather.mqtt.code.emun.DataType;
import com.longge.gather.mqtt.code.protocol.Payload_4;
import com.longge.gather.mqtt.code.protocol.ProtocolHead;
import com.longge.gather.utils.DateUtils;
import com.longge.gather.utils.FastJsonUtils;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @description Payload3内容协议业务处理接口实现
 * @author jianglong
 * @create 2019-10-24
 **/
@Service("payload4ServiceImpl")
public class Payload4ServiceImpl implements PayloadService {

    /**Payload_4消息体处理*/
    @Override
    public Map<String, List<TopicData>> toTopicData(String deviceId, ProtocolHead protocolHead) {
            Payload_4 payload4 = (Payload_4)protocolHead;
            //返回结果
            Map<String,List<TopicData>> topicDataMap = new HashMap<String,List<TopicData>>();
            List<TopicData> topicDataList = new ArrayList<TopicData>();
            //采集的json数据
            Map<String, String> pubData = FastJsonUtils.jsonToMap(payload4.getPublishDataStr());
            for(Map.Entry<String, String> entry : pubData.entrySet()) {
                String key = entry.getKey();
                String jsonValue = String.valueOf(entry.getValue());
                String dataType = key.split("_")[0];
                //裂缝计
                if(dataType.equals(DataType.CrackMeter.getDataTypeValue())){
                    Map<String, String> jsonValueMap = FastJsonUtils.jsonToMap(jsonValue);
                    for (Map.Entry<String, String> jsonValueEntry : jsonValueMap.entrySet()) {
                        EquipAcqData equipAcqData = new EquipAcqData();
                        equipAcqData.setEquipNum(deviceId);
                        equipAcqData.setAcqTime(DateUtils.dateToTime(jsonValueEntry.getKey()));
                        equipAcqData.setDataType(dataType);
                        equipAcqData.setEquipSerialNum(key.split("_")[1]);
                        equipAcqData.setValueStr(String.valueOf(jsonValueEntry.getValue()));
                        topicDataList.add(equipAcqData);
                        topicDataMap.put(KafkaTopicConstant.EQUIPACQDATA,topicDataList);
                    }
                }
                else{

                }
            }
            return topicDataMap;
    }
}
