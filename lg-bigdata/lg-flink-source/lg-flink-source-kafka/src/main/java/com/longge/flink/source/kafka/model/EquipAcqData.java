package com.longge.flink.source.kafka.model;
import lombok.Data;

/**
 * @description RTU监测数据
 * @author jianglong
 * @create 2019-10-23
 **/
@Data
public class EquipAcqData extends TopicData{

    private String dataType; //数据类型及传感器类型

    private String equipSerialNum; //设备序号

    private String valueStr;//采集数据

}
