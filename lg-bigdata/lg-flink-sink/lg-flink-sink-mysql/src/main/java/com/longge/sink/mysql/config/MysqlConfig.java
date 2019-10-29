package com.longge.sink.mysql.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @description MYSQL参数配置
 * @author jianglong
 * @create 2019-03-01
 **/

@Component
@ConfigurationProperties(prefix ="mysql.config")
@Data
public class MysqlConfig implements Serializable {

    private String driverClassName;

    private String url;

    private String userName;

    private String passWord;

    private int initialSize;

    private int maxTotal;

    private int minIdle;

    private String exeSql;
}
