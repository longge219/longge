package com.longge.gather.business.model;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
/**
 * @description RTU状态数据
 * @author jianglong
 * @create 2019-10-23
 **/
@Data
public class EquipStateData extends TopicData {

    private double V;//供电电压

    private double T;//温度

    private double H;//湿度

    @JSONField(name = "4G")
    private double G; //移动信号强度

    private int warning;//告警次数

}
