package com.longge.gather.mqtt.code.emun;
/**
 * @description 传输数据代码枚举类型
 * @author jianglong
 * @create 2019-10-14
 **/
public enum DataType {

    RtuState("000"), //RTU工作状态(默认24小时采集上报一次)

    WaterPressure("001"), //水压力--Pa帕(持续采集，默认2小时、加报每分钟一次)

    JointMeter("002"), //测缝计--mm毫米(持续采集，默认2小时、加报每分钟一次)

    RainGauge("003"), //雨量计--mm毫米(持续采集，上报区间值，默认值为0时2小时报一次，值不为0时10分钟上报一次，加报每1分钟一次)

    Secondary("004"), //次生--Hz赫兹(持续采集，默认2小时、加报每分钟一次)

    LandPressureGauge("005"), //土地压力计--Pa帕(持续采集，默认2小时、加报每分钟一次)

    VibratingStressMeter("006"), //振弦式应力计--kn(千牛)(持续采集，默认2小时、加报每分钟一次)

    CrackMeter("007"), //裂缝计--mm毫米(持续采集，默认2小时、加报每分钟一次)

    GnssDelta("008"), //GNSS结果数据--mm毫米(持续采集，默认2小时、加报每分钟一次)

    SoilMoistureContent("009"), //土壤含水率--%(持续采集，默认2小时、加报每分钟一次)

    EarthSurfaceDisplacement("010"), //地表位移--mm毫米(持续采集，默认2小时、加报每分钟一次)

    DeepDisplacement("011"), //深部位移--mm毫米(持续采集，默认2小时、加报每分钟一次)

    Inclinometer("012"), //倾角仪--°度(持续采集，默认2小时、加报每分钟一次)

    Clinometer("013"), //倾斜仪--mm毫米(持续采集，默认2小时、加报每分钟一次)

    UndergroundWater("014"), //地下水--m米(持续采集，默认2小时、加报每分钟一次)

    FixedSurvey("015"), //固定测斜--mm毫米(持续采集，默认2小时、加报每分钟一次)

    MultipointDisplacement("016"), //多点位移--mm毫米(持续采集，默认2小时、加报每分钟一次)

    SurfaceDisplacement("017"), //表面位移--mm毫米(持续采集，默认2小时、加报每分钟一次)

    Osmometer("018"), //渗压计--Pa帕(持续采集，默认2小时、加报每分钟一次)

    SlideHole("019"), //滑动测斜孔--°度(持续采集，默认2小时、加报每分钟一次)

    SettlementMeter ("020"), //沉降仪--mm毫米(持续采集，默认2小时、加报每分钟一次)

    LevelMeter("021"), //物位计--mm毫米(持续采集，默认2小时、加报每分钟一次)

    CurrentMeter ("022"), //流速仪--m/s(米/秒)(持续采集，默认2小时、加报每分钟一次)

    GnssBinaryData ("023"); //二进制数据(默认每1小时传一次)

    private final String dataType;

    DataType(String dataType) {
        this.dataType = dataType;
    }

}