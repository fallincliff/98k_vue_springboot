package com.demo.repository;

import com.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Administrator
 */
public interface UserRepository extends JpaRepository<User,Integer> {

    List<User> findByName(String name);
}
