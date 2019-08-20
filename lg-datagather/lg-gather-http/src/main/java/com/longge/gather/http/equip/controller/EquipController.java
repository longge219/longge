package com.longge.gather.http.equip.controller;
import com.longge.gather.http.equip.vo.RequestVo;
import com.longge.gather.http.equip.vo.ResponseVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EquipController {

    @RequestMapping(value = "/register_de", method = RequestMethod.POST)
    public ResponseVo register(@RequestBody RequestVo requestVo)throws Exception{
            System.out.println("注册");
            return  new ResponseVo();
    }
}
