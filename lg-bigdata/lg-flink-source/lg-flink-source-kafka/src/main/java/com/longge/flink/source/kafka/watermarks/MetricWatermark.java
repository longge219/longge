package com.longge.flink.source.kafka.watermarks;
import com.longge.flink.source.kafka.schemas.Metrics;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;
import javax.annotation.Nullable;

/**通过watermark对数据重排序，来保证整体数据流的有序性*/
public class MetricWatermark implements AssignerWithPeriodicWatermarks<Metrics> {

    private long currentTimestamp = Long.MIN_VALUE;

    @Nullable
    @Override
    public Watermark getCurrentWatermark() {
        return new Watermark(currentTimestamp == Long.MIN_VALUE ? Long.MIN_VALUE : currentTimestamp - 1);
    }

    @Override
    public long extractTimestamp(Metrics metrics, long l) {
        long timestamp = metrics.getTimestamp() / (1000 * 1000);
        this.currentTimestamp = timestamp;
        return timestamp;
    }
}
