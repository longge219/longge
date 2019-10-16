package com.longge.plugins.mysql.config.mapper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import tk.mybatis.mapper.entity.Config;

/**
 * @description 映射配置前缀
 * @author jianglong
 * @create 2019-09-24
 **/
@ConfigurationProperties(prefix = "mapper")
public class MapperProperties extends Config{
}
