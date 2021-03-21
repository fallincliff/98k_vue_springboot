package com.demo.controller;

import com.demo.entity.User;
import com.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @RequestMapping("/findAll/{page}/{size}")
    public Page<User> findAll(@PathVariable("page")Integer page,
                              @PathVariable("size")Integer size) {
        Pageable pageable= PageRequest.of(page-1,size);
        return userRepository.findAll(pageable);
    }
    @GetMapping("/findAll")
    public List<User> findAll() {
        return userRepository.findAll();
    }
    @GetMapping("/selectUser")
    public Object SelectUser(Integer id){
        return userRepository.findById(id);
    }
    @GetMapping("/selectUserByname")
    public List<User> SelectUser(@RequestParam("name")String name){
        return userRepository.findByName(name);
    }
    @GetMapping("/insert")
    public User InsertUser(@RequestParam("id") Integer id ,
                             @RequestParam("name") String name ,
                             @RequestParam("password")String password,
                             @RequestParam("sex")String sex,
                           @RequestParam("jurisdiction")Integer jurisdiction,
                           @RequestParam("email")String email,
                           @RequestParam("pic")String pic){
        User user=new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        user.setSex(sex);
        user.setJurisdiction(jurisdiction);
        user.setEmail(email);
        user.setPic(pic);
        return userRepository.save(user);}
}
