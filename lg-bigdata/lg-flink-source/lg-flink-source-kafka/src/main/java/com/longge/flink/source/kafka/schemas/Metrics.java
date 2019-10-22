package com.longge.flink.source.kafka.schemas;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Map;
/**
 * @author: jianglong
 * @description: kafka传输对象定义
 * @date: 2019-10-22
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Metrics {
    //主题名称
    private String name;

    //消息生成时间
    private Long timestamp;

    //生成标记
    private Map<String, String> tags;

    //消息体属性
    private Map<String, Object> fields;

}
