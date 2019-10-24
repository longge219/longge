package com.longge.flink.source.kafka.model;
import com.longge.flink.source.kafka.utils.DateUtils;
import lombok.Data;
import java.io.Serializable;

/**
 * @description 主题消息父类
 * @author jianglong
 * @create 2019-10-23
 **/
@Data
public class TopicData implements Serializable{

     String equipNum; //设备编号

     long  acqTime;//采集时间及数据产生时间

     long recTime = DateUtils.getCurrentTime();//服务器接收数据时间
}
