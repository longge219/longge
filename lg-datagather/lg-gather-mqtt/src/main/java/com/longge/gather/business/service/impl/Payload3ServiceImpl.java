package com.longge.gather.business.service.impl;
import com.longge.gather.business.constant.KafkaTopicConstant;
import com.longge.gather.business.model.EquipAcqData;
import com.longge.gather.business.model.EquipStateData;
import com.longge.gather.business.model.TopicData;
import com.longge.gather.business.service.PayloadService;
import com.longge.gather.mqtt.code.emun.DataType;
import com.longge.gather.mqtt.code.protocol.Payload_3;
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
@Service("payload3ServiceImpl")
public class Payload3ServiceImpl implements PayloadService {

    /**Payload_3消息体处理*/
    @Override
    public Map<String,List<TopicData>> toTopicData(String deviceId,ProtocolHead protocolHead){
        Payload_3 payload3 = (Payload_3)protocolHead;
        //返回结果
        Map<String,List<TopicData>> topicDataMap = new HashMap<String,List<TopicData>>();
        List<TopicData> topicDataList = new ArrayList<TopicData>();
        //采集的json数据
        Map<String, String> pubData = FastJsonUtils.jsonToMap(payload3.getPublishDataStr());
        for (Map.Entry<String, String> entry : pubData.entrySet()) {
            String key = entry.getKey();
            String jsonValue = String.valueOf(entry.getValue());
            String dataType = key.split("_")[0];
            //RTU工作状态
            if(dataType.equals(DataType.RtuState.getDataTypeValue())){
                EquipStateData equipStateData =  FastJsonUtils.jsonToObject(jsonValue, EquipStateData.class);
                equipStateData.setEquipNum(deviceId);
                equipStateData.setAcqTime(DateUtils.getCurrentTime());
                topicDataList.add(equipStateData);
                topicDataMap.put(KafkaTopicConstant.EQUIPSTATEDATA,topicDataList);
            }
            //裂缝计
            else if(dataType.equals(DataType.CrackMeter.getDataTypeValue())){
                EquipAcqData equipAcqData = new EquipAcqData();
                equipAcqData.setEquipNum(deviceId);
                equipAcqData.setAcqTime(DateUtils.getCurrentTime());
                equipAcqData.setDataType(dataType);
                equipAcqData.setEquipSerialNum(key.split("_")[1]);
                equipAcqData.setValueStr(jsonValue);
                topicDataList.add(equipAcqData);
                topicDataMap.put(KafkaTopicConstant.EQUIPACQDATA,topicDataList);
            }
            else{

            }

        }
        return topicDataMap;
    }
}
