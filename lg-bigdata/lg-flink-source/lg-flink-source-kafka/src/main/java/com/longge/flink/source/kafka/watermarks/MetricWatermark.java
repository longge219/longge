package com.longge.flink.source.kafka.watermarks;
import com.longge.flink.source.kafka.schemas.Metrics;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;
import javax.annotation.Nullable;
/**
 * @author: jianglong
 * @description: 通过watermark对数据重排序，来保证整体数据流的有序性
 * @date: 2019-10-22
 * */
public class MetricWatermark implements AssignerWithPeriodicWatermarks<Metrics> {

    private final long   maxOutOfOrderness = 5000; //允许数据的最大乱序时间5秒

    private long  currentMaxTimestamp; //目前最大时间

    /**是从数据本身中提取 EventTime*/
    @Override
    public long extractTimestamp(Metrics metrics, long previousElementTimestamp) {
        long timestamp = metrics.getTimestamp() / (1000); //转换成秒
        this.currentMaxTimestamp = Math.max(timestamp,currentMaxTimestamp)  ;
        return timestamp;
    }

    /**获取当前水位线*/
    @Nullable
    @Override
    public Watermark getCurrentWatermark() {
        return new Watermark(currentMaxTimestamp - maxOutOfOrderness);
    }

}
