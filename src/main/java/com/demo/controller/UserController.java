package com.demo.controller;

import com.demo.entity.User;
import com.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/findAll")
    public List<User> findAll() {
        return userRepository.findAll();
    }
    @GetMapping("/selectUser")
    public Object SelectUser(Integer id){
        return userRepository.findById(id);
    }
    @GetMapping("/insert")
    public User InsertUser(@RequestParam("id") Integer id ,
                             @RequestParam("name") String name ,
                             @RequestParam("password")String password,
                             @RequestParam("sex")String sex,
                           @RequestParam("jurisdiction")Integer jurisdiction){
        User user=new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        user.setSex(sex);
        user.setJurisdiction(jurisdiction);
        return userRepository.save(user);}
}
