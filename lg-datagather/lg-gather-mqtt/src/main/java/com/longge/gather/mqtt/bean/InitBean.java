package com.longge.gather.mqtt.bean;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * @description 服务端链接参数配置
 * @author jianglong
 * @create 2019-03-01
 **/
@ConfigurationProperties(prefix ="com.longge.server")
@Data
public class InitBean {

    private String protocol;

    private int port ;

    private String serverName ;

    private boolean keepalive ;

    private boolean reuseaddr ;


    private boolean tcpNodelay ;

    private int backlog ;

    private  int  sndbuf ;

    private int revbuf ;

    private int read ;

    private int write ;

    private int readAndWrite ;

    private boolean ssl ;

    private String jksFile;

    private String jksStorePassword;

    private String jksCertificatePassword;

    private int  initalDelay ;

    private  int period ;

    private int bossThread;

    private int workThread;

}
