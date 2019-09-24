package com.longge.gather.http.equip.model;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
@Table(name = "equipment")
@Data
public class Equipment implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "equipsn")
    private String equipSn;

    @Column(name = "registercode")
    private String registerCode;
}