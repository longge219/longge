package com.longge.gather.business.model;
import lombok.Data;
/**
 * @description 设备上线下线事件数据
 * @author jianglong
 * @create 2019-10-23
 **/
@Data
public class EquipLineData extends TopicData {

    private boolean isConnect;//是否是上线

}
