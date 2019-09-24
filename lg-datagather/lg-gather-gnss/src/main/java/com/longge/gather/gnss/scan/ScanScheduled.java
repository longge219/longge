package com.longge.gather.gnss.scan;
import com.longge.gather.gnss.ci.model.OriginalData;
import com.longge.gather.gnss.server.model.Observations;
import com.longge.plugins.kafka.producer.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * @description 扫描处理观测数据
 * @author jianglong
 * @create 2019-03-01
 **/
public class ScanScheduled extends ScanRunnable{

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public ScanScheduled(KafkaProducerService kafkaProducerService){
       this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public void doObservations(Observations observations) {
        System.out.println("接收机号:"+ observations.getSiteInfo().getSiteNo()+"时间:"+observations.getRefTime().getGpsWeekSec());
        //生成obs主题消息
        //kafkaProducerService.sendMessage("obs",originalData);
    }

    /**数据对象转换*/
    private OriginalData changeObservationsToOriginalData(Observations observations){
        OriginalData originalData = new OriginalData();
        return originalData;
    }

}
