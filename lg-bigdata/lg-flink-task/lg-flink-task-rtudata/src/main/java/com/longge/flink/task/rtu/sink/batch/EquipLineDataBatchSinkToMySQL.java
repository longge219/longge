package com.longge.flink.task.rtu.sink.batch;
import com.longge.flink.source.kafka.model.EquipLineData;
import com.longge.sink.mysql.config.MysqlConfig;
import com.longge.sink.mysql.core.AbstratBatchSinkToMySQL;
import java.sql.Timestamp;
import java.util.List;
/**
 * @description 批量数据写入mysql
 * @author jianglong
 * @create 2019-10-29
 **/
public class EquipLineDataBatchSinkToMySQL extends AbstratBatchSinkToMySQL<EquipLineData> {

    public EquipLineDataBatchSinkToMySQL(MysqlConfig mysqlConfig){
        mysqlConfig.setExeSql("insert into equiplinedata(equipnum, isline, acqtime) values(?, ?, ?);");
        super.initConfig(mysqlConfig);
    }

    /**
     * 每条数据的插入都要调用一次 invoke() 方法
     */
    @Override
    public void invoke(List<EquipLineData> value, Context context) throws Exception {
        //遍历数据集合
        for (EquipLineData equipLineData : value) {
            ps.setString(1, equipLineData.getEquipNum());
            ps.setBoolean(2,equipLineData.isConnect());
            ps.setTimestamp(3,new Timestamp(equipLineData.getAcqTime()));
            ps.addBatch();
        }
        int[] count = ps.executeBatch();//批量后执行
        System.out.println("成功了插入了" + count.length + "行数据");
    }
}
