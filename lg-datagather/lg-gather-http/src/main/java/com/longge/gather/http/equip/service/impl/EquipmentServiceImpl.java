package com.longge.gather.http.equip.service.impl;
import com.longge.gather.http.equip.dao.XcnetEquipmentInfoDao;
import com.longge.gather.http.equip.model.XcnetEquipmentInfo;
import com.longge.gather.http.equip.service.EquipmentService;
import com.longge.plugins.mysql.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @description 设备信息管理
 * @author jianglong
 * @create 2019-09-29
 **/
@Service
public class EquipmentServiceImpl extends BaseServiceImpl<XcnetEquipmentInfo> implements EquipmentService {

    @Autowired
    private XcnetEquipmentInfoDao xcnetEquipmentInfoDao;


}
