package com.longge.gather.http.equip.controller;
import com.longge.gather.http.equip.vo.RequestVo;
import com.longge.gather.http.equip.vo.ResponseVo;
import org.springframework.web.bind.annotation.*;

@RestController
public class EquipController {

    @PostMapping(value = "/register_de",produces="application/json")
    @ResponseBody public ResponseVo register(@RequestParam String register_code, @RequestBody RequestVo requestVo){
        System.out.println("注册");
        System.out.println(register_code);
        System.out.println(requestVo.getMac());
        System.out.println(requestVo.getSn());
        System.out.println(requestVo.getTitle());
        return  new ResponseVo();
    }



    @RequestMapping(value = "/register_de/{register_code}", method = RequestMethod.POST,produces="application/json")
    @ResponseBody public ResponseVo register2(@PathVariable ("register_code") String register_code, @RequestBody(required=false) RequestVo requestVo){
        System.out.println("注册");
        return  new ResponseVo();
    }
}
