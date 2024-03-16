package com.example.GoCloud.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.example.GoCloud.DataHolder.User;

@Component
public interface UserDao extends JpaRepository<User,Long> {

}
