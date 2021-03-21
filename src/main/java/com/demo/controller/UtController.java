package com.demo.controller;


import com.demo.entity.Text;
import com.demo.entity.User;
import com.demo.entity.Ut;
import com.demo.repository.UtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ut")
public class UtController {
    @Autowired
    private UtRepository utRepository;
    @GetMapping("/findByUidandTid")
    public List<Ut> findByUidandTid(@RequestParam("uid")Integer uid,
                            @RequestParam("tid")Integer tid) {
        return utRepository.findByUidandTid(uid,tid);
    }
    @GetMapping("/findByUidandCollection")
    public List<Ut> findByUidandCollection(@RequestParam("uid")Integer uid,
                                    @RequestParam("collection")Integer collection) {
        return utRepository.findByUidandCollection(collection,uid);
    }
    @GetMapping("/insert")
    public Ut InsertUt(@RequestParam("uid")Integer uid,
                           @RequestParam("tid")Integer tid,
                           @RequestParam("name")String name,
                           @RequestParam("path")String path,
                            @RequestParam("author")String author,
                           @RequestParam("up")Integer up,
                         @RequestParam("collection")Integer collection){
        Ut text=new Ut();
        text.setTid(tid);
        text.setUid(uid);
        text.setPath(path);
        text.setAuthor(author);
        text.setName(name);
        text.setUp(up);
        text.setCollection(collection);
        return utRepository.save(text);
    }

}
