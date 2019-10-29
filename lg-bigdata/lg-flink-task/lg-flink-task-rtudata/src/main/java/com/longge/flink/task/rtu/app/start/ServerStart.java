package com.longge.flink.task.rtu.app.start;
import com.longge.flink.source.kafka.factory.EquipLineDataKafkaSourceFactory;
import com.longge.flink.source.kafka.model.EquipLineData;
import com.longge.flink.task.rtu.flink.factory.ExecutionEnvFactory;
import com.longge.flink.task.rtu.sink.batch.EquipLineDataBatchSinkToMySQL;
import com.longge.flink.task.rtu.sink.single.EquipLineDataSingleSinkToMySQL;
import com.longge.sink.mysql.config.MysqlConfig;
import org.apache.flink.shaded.curator.org.apache.curator.shaded.com.google.common.collect.Lists;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.AllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
/**
 * @description 服务启动类--主类
 * @author jianglong
 * @create 2019-02-25
 **/
@Component
public class ServerStart implements BootstrapServer{

    @Autowired
    public  MysqlConfig mysqlConfig;

    @Override
    public void start() {
        try{
            //创建运行环境并加载运行配置
            final ParameterTool parameterTool = ExecutionEnvFactory.createParameterTool();
            StreamExecutionEnvironment env = ExecutionEnvFactory.createStreamExecutionEnvironment(parameterTool);
            /**单条存储*/
//            DataStreamSource<EquipLineData> equipLineDataKafkaSource = EquipLineDataKafkaSourceFactory.createEquipLineDataKafkaSource(env);
//            equipLineDataKafkaSource.addSink(new EquipLineDataSingleSinkToMySQL(mysqlConfig)); //数据 sink 到 mysql
            /**批量插入*/
            DataStreamSource<EquipLineData> equipLineDataKafkaSource = EquipLineDataKafkaSourceFactory.createEquipLineDataKafkaSource(env);
            //timeWindowAll 并行度只能为 1
            equipLineDataKafkaSource.timeWindowAll(Time.seconds(5)).apply(new AllWindowFunction<EquipLineData, List<EquipLineData>, TimeWindow>() {
                @Override
                public void apply(TimeWindow window, Iterable<EquipLineData> values, Collector<List<EquipLineData>> out) throws Exception {
                    ArrayList<EquipLineData> students = Lists.newArrayList(values);
                    if (students.size() > 0) {
                        System.out.println("1 分钟内收集到 student 的数据条数是：" + students.size());
                        out.collect(students);
                    }
                }
            }).addSink(new EquipLineDataBatchSinkToMySQL(mysqlConfig)).setParallelism(1);
            //执行任务
            env.execute("rtudata-flink");
        }catch ( Exception e){
                e.printStackTrace();
        }

    }
}
