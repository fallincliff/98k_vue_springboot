package com.demo;

import com.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootTestApplicationTests {

    @Autowired
    private UserRepository userRepository;
    @Test
    void contextLoads() {
        System.out.println(userRepository.findAll());
    }

}
