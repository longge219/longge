package com.longge.flink.source.kafka.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @description 设备上线下线事件数据
 * @author jianglong
 * @create 2019-10-23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipLineData extends TopicData{

    private boolean isConnect;//是否是上线

}
