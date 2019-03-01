package com.longge.flink.rtk.flink.factory;
import com.longge.flink.rtk.flink.properties.TaskRtkPropertiesConstants;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExecutionEnvFactory {

    public static ParameterTool createParameterTool(String[] args) {
        try {
            return ParameterTool
                    .fromPropertiesFile(ExecutionEnvFactory.class.getResourceAsStream(TaskRtkPropertiesConstants.PROPERTIES_FILE_NAME))
                    .mergeWith(ParameterTool.fromArgs(args))
                    .mergeWith(ParameterTool.fromSystemProperties())
                    .mergeWith(ParameterTool.fromMap(getenv()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static StreamExecutionEnvironment createStreamExecutionEnvironment(ParameterTool parameterTool) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //设置并行度为，默认并行度是当前机器CPU数量
        env.setParallelism(parameterTool.getInt(TaskRtkPropertiesConstants.STREAM_PARALLELISM, 5));
        env.getConfig().disableSysoutLogging();
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(4, 10000));
        if (parameterTool.getBoolean(TaskRtkPropertiesConstants.STREAM_CHECKPOINT_ENABLE, true)) {
            env.enableCheckpointing(parameterTool.getInt(TaskRtkPropertiesConstants.STREAM_CHECKPOINT_INTERVAL, 1000));
        }
        // 告诉系统按照 EventTime 处理，默认是使用processtime
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        //供后续连接source或者sink使用配置
        env.getConfig().setGlobalJobParameters(parameterTool);
        return env;
    }

    private static Map<String, String> getenv() {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}
