package com.example.GoCloud.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.example.GoCloud.DataHolder.Client;

@Component
public interface RegisterDao extends JpaRepository<Client,Long>{

}
