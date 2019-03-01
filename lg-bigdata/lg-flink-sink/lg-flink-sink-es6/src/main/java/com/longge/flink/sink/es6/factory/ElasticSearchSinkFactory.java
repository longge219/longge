package com.longge.flink.sink.es6.factory;
import com.longge.flink.sink.es6.properties.ElasticSearchSinkPropertiesConstants;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkBase;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.http.HttpHost;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/**
 * @description ES-SINK创建工厂
 * @author jianglong
 * @create 2019-02-25
 **/
public class ElasticSearchSinkFactory {

    /**创建ES-SINK*/
    public static <T> void createElasticSearchSink(StreamExecutionEnvironment env,SingleOutputStreamOperator<T> data, ElasticsearchSinkFunction<T> func) throws  Exception{
        ParameterTool parameterTool = (ParameterTool) env.getConfig().getGlobalJobParameters();
        List<HttpHost> hosts = getEsAddresses(parameterTool.get(ElasticSearchSinkPropertiesConstants.ELASTICSEARCH_HOSTS));
        ElasticsearchSink.Builder<T> esSinkBuilder = new ElasticsearchSink.Builder<>(hosts, func);
        //配置优化参数
        esSinkBuilder.setBulkFlushMaxActions(parameterTool.getInt(ElasticSearchSinkPropertiesConstants.ELASTICSEARCH_BULK_FLUSH_MAX_ACTIONS,40));
        esSinkBuilder.setBulkFlushBackoff(parameterTool.getBoolean(ElasticSearchSinkPropertiesConstants.ELASTICSEARCH_BULK_FLUSH_BACK_OFF,false));
        esSinkBuilder.setBulkFlushBackoffType(parameterTool.get(ElasticSearchSinkPropertiesConstants.ELASTICSEARCH_BULK_FLUSH_BACK_OFF_TYPE).equals("")? ElasticsearchSinkBase.FlushBackoffType.CONSTANT: ElasticsearchSinkBase.FlushBackoffType.EXPONENTIAL);
        esSinkBuilder.setBulkFlushBackoffDelay(parameterTool.getLong(ElasticSearchSinkPropertiesConstants.ELASTICSEARCH_BULK_FLUSH_BACK_OFF_DELAY));
        esSinkBuilder.setBulkFlushBackoffRetries(parameterTool.getInt(ElasticSearchSinkPropertiesConstants.ELASTICSEARCH_BULK_FLUSH_BACK_OFF_RETRIES,1));
        esSinkBuilder.setBulkFlushMaxSizeMb(parameterTool.getInt(ElasticSearchSinkPropertiesConstants.ELASTICSEARCH_BULK_FLUSH_MAX_SIZE_MB));
        esSinkBuilder.setBulkFlushInterval(parameterTool.getLong(ElasticSearchSinkPropertiesConstants.ELASTICSEARCH_BULK_FLUSH_INTERVAL));
        //设置失败处理器
        //esSinkBuilder.setFailureHandler();
        //设置窗口并行数
        data.addSink(esSinkBuilder.build());
    }

    /**解析配置文件的 es hosts*/
    private  static List<HttpHost> getEsAddresses(String hosts) throws MalformedURLException {
        String[] hostList = hosts.split(",");
        List<HttpHost> addresses = new ArrayList<>();
        for (String host : hostList) {
            if (host.startsWith("http")) {
                URL url = new URL(host);
                addresses.add(new HttpHost(url.getHost(), url.getPort()));
            } else {
                String[] parts = host.split(":", 2);
                if (parts.length > 1) {
                    addresses.add(new HttpHost(parts[0], Integer.parseInt(parts[1])));
                } else {
                    throw new MalformedURLException("invalid elasticsearch hosts format");
                }
            }
        }
        return addresses;
    }
}
