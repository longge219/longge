package com.longge.gather.http.equip.service;
import com.longge.gather.http.equip.model.Equipment;
import com.longge.plugins.mysql.service.BaseService;

public interface EquipmentService extends BaseService<Equipment> {

	public void resiter(Equipment equipment);

}
