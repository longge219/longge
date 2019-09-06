package com.longge.gather.http.equip.vo;
import lombok.Data;
import java.io.Serializable;

@Data
public class RequestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sn;

    private  String mac;

    private String title;

}
