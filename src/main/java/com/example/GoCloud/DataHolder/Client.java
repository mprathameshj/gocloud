package com.example.GoCloud.DataHolder;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Client {
  
  @Id
 	
 public  Long id;
  
 public  String name;
  
 public  String password;

  // Constructors, getters, and setters if needed
}
