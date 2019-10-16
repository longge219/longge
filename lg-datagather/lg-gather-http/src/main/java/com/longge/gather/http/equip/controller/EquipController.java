package com.longge.gather.http.equip.controller;
import com.longge.gather.http.equip.model.XcnetEquipmentInfo;
import com.longge.gather.http.equip.service.EquipmentService;
import com.longge.gather.http.equip.vo.RequestVo;
import com.longge.gather.http.equip.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * @description 设备信息管理
 * @author jianglong
 * @create 2019-09-29
 **/
@RestController
public class EquipController {

    @Autowired
    private EquipmentService equipmentServiceImpl;

    @PostMapping(value = "/register_de",produces="application/json")
    @ResponseBody public ResponseVo register(@RequestParam String register_code, @RequestBody RequestVo requestVo){
        System.out.println("注册");
        XcnetEquipmentInfo xcnetEquipmentInfo = new XcnetEquipmentInfo();
        xcnetEquipmentInfo.setEquipCode(requestVo.getSn());
        xcnetEquipmentInfo = equipmentServiceImpl.selectOne(xcnetEquipmentInfo);
        return  new ResponseVo();
    }



    @RequestMapping(value = "/register_de/{register_code}", method = RequestMethod.POST,produces="application/json")
    @ResponseBody public ResponseVo register2(@PathVariable ("register_code") String register_code, @RequestBody(required=false) RequestVo requestVo){
        System.out.println("注册");
        return  new ResponseVo();
    }


}
