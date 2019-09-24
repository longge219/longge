package com.longge.gather.mqtt.common.util;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
/**
 * @description Ip操作
 * @author jianglong
 * @create 2019-09-09
 **/
public class IpUtils {

    /** 获取外网IP*/
    public static String internetIp() {
        try {

            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            InetAddress inetAddress = null;
            Enumeration<InetAddress> inetAddresses = null;
            while (networks.hasMoreElements()) {
                inetAddresses = networks.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null
                            && inetAddress instanceof Inet4Address
                            && !inetAddress.isSiteLocalAddress()
                            && !inetAddress.isLoopbackAddress()
                            && inetAddress.getHostAddress().indexOf(":") == -1) {
                        return inetAddress.getHostAddress();
                    }
                }
            }

            return null;

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
    /** 获取内网IP*/
    public static String intranetIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**获取服务启动host*/
    public static String getHost(){
        return internetIp()==null?intranetIp():internetIp();
    }
 }
