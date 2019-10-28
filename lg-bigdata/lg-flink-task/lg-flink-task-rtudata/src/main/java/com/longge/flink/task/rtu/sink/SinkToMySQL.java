package com.longge.flink.task.rtu.sink;
import com.longge.flink.source.kafka.model.EquipLineData;
import com.longge.flink.task.rtu.utils.DateUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.*;

/**
 * Desc: sink 数据到 mysql
 * Created by zhisheng_tian on 2019-02-17
 * Blog: http://www.54tianzhisheng.cn/tags/Flink/
 */
public class SinkToMySQL extends RichSinkFunction<EquipLineData> {
    PreparedStatement ps;
    private Connection connection;

    /**
     * open() 方法中建立连接，这样不用每次 invoke 的时候都要建立连接和释放连接
     *
     * @param parameters
     * @throws Exception
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        connection = getConnection();
        String sql = "insert into equiplinedata(equipnum, isline, acqtime) values(?, ?, ?);";
        ps = this.connection.prepareStatement(sql);
    }

    @Override
    public void close() throws Exception {
        super.close();
        //关闭连接和释放资源
        if (connection != null) {
            connection.close();
        }
        if (ps != null) {
            ps.close();
        }
    }

    /**
     * 每条数据的插入都要调用一次 invoke() 方法
     */
    @Override
    public void invoke(EquipLineData equipLineData, Context context) throws Exception {
        //组装数据，执行插入操作
        ps.setString(1, equipLineData.getEquipNum());
        ps.setBoolean(2,equipLineData.isConnect());
        ps.setTimestamp(3,new Timestamp(equipLineData.getAcqTime()));
        ps.executeUpdate();
    }

    private static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //注意，替换成自己本地的 mysql 数据库地址和用户名、密码
            con = DriverManager.getConnection("jdbc:mysql://10.30.0.160:3306/xcnet?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2b8", "root", "123456");
        } catch (Exception e) {
            System.out.println("-----------mysql get connection has exception , msg = "+ e.getMessage());
        }
        return con;
    }
}
