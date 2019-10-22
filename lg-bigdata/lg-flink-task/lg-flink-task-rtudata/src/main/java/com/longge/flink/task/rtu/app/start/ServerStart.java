package com.longge.flink.task.rtu.app.start;
import com.longge.flink.source.kafka.factory.KafkaSourceFactory;
import com.longge.flink.source.kafka.schemas.Metrics;
import com.longge.flink.task.rtu.flink.factory.ExecutionEnvFactory;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.stereotype.Component;
/**
 * @description 服务启动类--主类
 * @author jianglong
 * @create 2019-02-25
 **/
@Component
public class ServerStart implements BootstrapServer{
    @Override
    public void start() {
        try{
             //创建运行环境并加载运行配置
            final ParameterTool parameterTool = ExecutionEnvFactory.createParameterTool();
            StreamExecutionEnvironment env = ExecutionEnvFactory.createStreamExecutionEnvironment(parameterTool);
            //从kafka读取数据
            DataStreamSource<Metrics> data = KafkaSourceFactory.createKafkaSource(env);
            //存储任务

            //执行任务
            env.execute("longge-flink");
        }catch ( Exception e){
                e.printStackTrace();
        }

    }
}
