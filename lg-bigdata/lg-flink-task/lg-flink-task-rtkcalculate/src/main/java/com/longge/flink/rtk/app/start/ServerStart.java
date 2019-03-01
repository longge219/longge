package com.longge.flink.rtk.app.start;
import com.longge.flink.rtk.app.util.GsonUtil;
import com.longge.flink.rtk.flink.factory.ExecutionEnvFactory;
import com.longge.flink.rtk.flink.properties.TaskRtkPropertiesConstants;
import com.longge.flink.sink.es6.factory.ElasticSearchSinkFactory;
import com.longge.flink.source.kafka.factory.KafkaSourceFactory;
import com.longge.flink.source.kafka.schemas.Metrics;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.elasticsearch.client.Requests;
import org.springframework.stereotype.Component;
import org.elasticsearch.common.xcontent.XContentType;
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
            final ParameterTool parameterTool = ExecutionEnvFactory.createParameterTool(null);
            StreamExecutionEnvironment env = ExecutionEnvFactory.createStreamExecutionEnvironment(parameterTool);
            //从kafka读取数据
            DataStreamSource<Metrics> data = KafkaSourceFactory.createKafkaSource(env);
            //计算

            //写入ES
            ElasticSearchSinkFactory.createElasticSearchSink(env,data,(Metrics metric, RuntimeContext runtimeContext, RequestIndexer requestIndexer) -> {
                        requestIndexer.add(Requests.indexRequest()
                                .index("jianglong" + "_" + metric.getName()) //es 索引名
                                .type("jianglong") //es-type
                                .source(GsonUtil.toJSONBytes(metric), XContentType.JSON));
            });
            //执行任务
            env.execute("longge-flink");
        }catch ( Exception e){
                e.printStackTrace();
        }

    }
}
