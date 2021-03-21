package com.demo.controller;

import com.demo.entity.Text;
import com.demo.entity.User;
import com.demo.repository.TextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RestController
@RequestMapping("/text")
public class TextController {
    @Autowired
    private TextRepository textRepository;
    @RequestMapping("/findAll/{page}/{size}")
    public Page<Text> findAll(@PathVariable("page")Integer page,
                              @PathVariable("size")Integer size) {
        Pageable pageable= PageRequest.of(page-1,size);
        return textRepository.findAll(pageable);
    }
    @RequestMapping("/findAll")
    public List<Text> findAll() {
        return textRepository.findAll();
    }
    @GetMapping("/selectText")
    public Object SelectText(@RequestParam("tid")Integer tid){
        return textRepository.findById(tid);
    }
    @GetMapping("/selectByAuthor")
    public List<Text> SelectText(@RequestParam("author")String author){return textRepository.findByAuthor(author);}
    @GetMapping("/selectByPath")
    public List<Text> SelectByPath(@RequestParam("path")String path){return textRepository.findByPath(path);}
    @GetMapping("/insert")
    public Text InsertText(@RequestParam("tid")Integer tid,
                           @RequestParam("author")String author,
                           @RequestParam("path")String path,
                           @RequestParam("tag")String tag,
                           @RequestParam("picture")String picture){
        Text text=new Text();
        text.setTid(tid);
        text.setAuthor(author);
        text.setPath(path);
        text.setTag(tag);
        text.setPicture(picture);
        text.setUp(0);
        text.setCollection(0);
        return textRepository.save(text);
    }
    @GetMapping("/delete")
    public void deleteText(@RequestParam("tid")Integer tid){
         textRepository.deleteById(tid);
    }
    @GetMapping("/update")
    public Text updateText(@RequestParam("tid")Integer tid,
                           @RequestParam("author")String author,
                           @RequestParam("path")String path,
                           @RequestParam("tag")String tag,
                           @RequestParam("picture")String picture,
                           @RequestParam("up")Integer up,
                           @RequestParam("collection")Integer collection){
        Text text=new Text();
        text.setTid(tid);
        text.setAuthor(author);
        text.setPath(path);
        text.setTag(tag);
        text.setPicture(picture);
        text.setUp(up);
        text.setCollection(collection);
        return textRepository.save(text);
    }
}
