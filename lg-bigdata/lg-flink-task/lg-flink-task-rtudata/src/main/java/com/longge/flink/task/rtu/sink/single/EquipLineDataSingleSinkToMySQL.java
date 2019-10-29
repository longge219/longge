package com.longge.flink.task.rtu.sink.single;
import com.longge.flink.source.kafka.model.EquipLineData;
import com.longge.sink.mysql.config.MysqlConfig;
import com.longge.sink.mysql.core.AbstratSingleSinkToMySQL;
import java.sql.Timestamp;
/**
 * @description  单条数据写入mysql
 * @author jianglong
 * @create 2019-10-29
 **/
public class EquipLineDataSingleSinkToMySQL extends AbstratSingleSinkToMySQL<EquipLineData> {

        public EquipLineDataSingleSinkToMySQL(MysqlConfig mysqlConfig){
                mysqlConfig.setExeSql("insert into equiplinedata(equipnum, isline, acqtime) values(?, ?, ?);");
                super.initConfig(mysqlConfig);
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


}
