package com.demo.repository;

import com.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 */
public interface UserRepository extends JpaRepository<User,Integer> {

}
