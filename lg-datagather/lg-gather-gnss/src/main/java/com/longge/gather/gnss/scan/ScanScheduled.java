package com.longge.gather.gnss.scan;
import com.longge.gather.gnss.gnss.model.Observations;
import com.longge.gather.gnss.scan.model.OriginalData;
import com.longge.gather.kafka.service.KafkaProducerService;
/**
 * @description 扫描处理观测数据
 * @author jianglong
 * @create 2019-03-01
 **/
public class ScanScheduled extends ScanRunnable{

    KafkaProducerService kafkaProducerService;

    public ScanScheduled(KafkaProducerService kafkaProducerService){
       this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public void doObservations(Observations observations) {
        OriginalData originalData = changeObservationsToOriginalData(observations);
        //生成obs主题消息
        kafkaProducerService.sendMessage("obs",originalData);
    }

    /**数据对象转换*/
    private OriginalData changeObservationsToOriginalData(Observations observations){
        OriginalData originalData = new OriginalData();
        return originalData;
    }

}
