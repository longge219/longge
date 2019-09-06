package com.longge.plugins.es.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: jiang long
 * @Description: ES存储对象
 * @Date: 2019-03-01
 */
@Data
@ToString
@NoArgsConstructor
public class EsModel {

    private String id;

    private String name;

    private int age;

    private Date date;
}
