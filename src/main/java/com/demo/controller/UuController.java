package com.demo.controller;

import com.demo.entity.Ut;
import com.demo.entity.Uu;
import com.demo.repository.UuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/uu")
public class UuController {
    @Autowired
    private UuRepository uuRepository;

    @GetMapping("/findAll")
    public List<Uu> findAll(){
        return uuRepository.findAll();
    }
    //关注列表
    @GetMapping("/findByuid")
    public List<Uu> findByUid(@RequestParam("uid")Integer uid){
        return uuRepository.findByUid(uid,1);
    }
    //粉丝列表
    @GetMapping("/findByvipuid")
    public List<Uu> findByVipuid(@RequestParam("vipuid")Integer vipuid){
        return uuRepository.findByVipuid(vipuid,1);
    }
    //修改/添加
    @GetMapping("/insert")
    public Uu Insert(@RequestParam("uid")Integer uid,
                     @RequestParam("vipuid")Integer vipuid,
                     @RequestParam("name")String name,
                     @RequestParam("vipname")String vipname,
                     @RequestParam("vip")Integer vip){
            Uu text=new Uu();
            text.setUid(uid);
            text.setVipuid(vipuid);
            text.setName(name);
            text.setVipname(vipname);
            text.setVip(vip);
        return uuRepository.save(text);
    }
}
