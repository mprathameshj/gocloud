package com.example.GoCloud.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.GoCloud.Dao.RegisterDao;
import com.example.GoCloud.Dao.UserDao;
import com.example.GoCloud.DataHolder.Client;
import com.example.GoCloud.DataHolder.User;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



@RestController

public class MyController {

	@Autowired
	RegisterDao registerDao;
	
	//UserDao
	@Autowired
	UserDao userDao;
	
	//To create new schema
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	
	
	@GetMapping("/home/students")
	public  List<Client> home() {
		 List<Client> clients = registerDao.findAll();
	        
	        return clients;
	}
	
	
	  
	   
	   //Code to register new users/clients
	@PostMapping("/create")
	public ResponseEntity<String>  create_client(@RequestBody User user) {
		
	    if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Invalid user data");
	    }
	    
	    if(userDao.existsById(user.getId())) 
	    	return ResponseEntity.status(HttpStatus.CONFLICT).body("User already registered");

	    
	    try {
	    if(userDao.save(user)!=null) {
	    //Create new schema in the database	
	    	String dbResultString=createDatabase(user.getDbname());
	    	
	    	if(dbResultString.equals("ok")) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Databse Created successfully");
	    	}
	    	
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating database: " + dbResultString);

	  
	    }
	    }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
	    }
	    
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
	}


	  
	  //Method to create database
	    public String createDatabase( String id) {
	        // Constructing the database name
	        String dbName = "db_" + id;

	        try {
	            // Creating the new database/schema
	            jdbcTemplate.execute("CREATE DATABASE " + dbName);

	            return "ok";
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Error occurred while creating database " + dbName;
	        }
	    }
	
	    
	    
	 //To handle the use requests
	 // Endpoint to handle get requests
	    @SuppressWarnings("deprecation")
		@GetMapping("/read")
	    public List<Map<String, Object>> executeQuery(
	    		 @RequestParam("SHA") String SHA,
	    	     @RequestParam("quary") String quary,
	    	     @RequestParam("clientid") String clientid
	    		) {
	        
	    	User user=null;
	    	try {
	    	user=userDao.getById(Long.parseLong(clientid));
	    	}catch(Exception e) {
	    		return null;
	    	}
	    	
	    	if(user==null) return null;
	    	
	    	String SHA1=user.getSHA();
	    	
	    	
	    	if(!SHA1.equals(SHA)) return null;


	        try {
	            // Execute the SQL query and fetch the result
	        	
	            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(quary);
	            return resultList;

	        } catch (Exception e) {
	            // Handle any errors
	            e.printStackTrace();
	            return null; // or return an empty list or throw an exception based on your requirement
	        }
	    	
	    }
	    
	    @GetMapping("test")
	    public String test() {
	    	return "Application is running";
	    }
	    
	    
	    //To handle write requests
	    @SuppressWarnings("deprecation")
		@PostMapping("/write")
	    public String execute(@RequestBody com.example.GoCloud.DataHolder.RequestBody  rb) {
	    	
	    	User user=userDao.getById(Long.parseLong(rb.getClientid()));
	    	if(user==null) return "Unathoried Access";
	    	
	    	String SHA=user.SHA;
	    	String quary=rb.getQuary();
	    	
	    	if(!SHA.equals(rb.SHA)) return "Unathoried Access";
	    	
	    	try {
	    		
	    	}catch(Exception e) {
	    		return e.toString();
	    	}
	    	
	    	return "";
	    	
	    } 
	    
	    
	    
	    
}
