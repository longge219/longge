package com.longge.gather.http.equip.service.impl;
import com.longge.gather.http.equip.model.Equipment;
import com.longge.gather.http.equip.service.EquipmentService;
import com.longge.plugins.mysql.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipmentServiceImpl extends BaseServiceImpl<Equipment> implements EquipmentService {

    /**批量重置密码*/
    @Transactional
    public void resiter(Equipment equipment) {
        updateByPrimaryKeySelective(equipment);
    }

}
