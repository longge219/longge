package com.longge.gather.gnss.scan;

import com.longge.gather.gnss.gnss.model.Observations;

/**
 * @description 扫描处理观测数据
 * @author jianglong
 * @create 2019-03-01
 **/
public class ScanScheduled extends ScanRunnable{

    @Override
    public void doObservations(Observations observations) {

        System.out.println("执行扫描观测数据");

    }

    /**生成kafka主题消息*/
}
