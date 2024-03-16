package com.example.GoCloud.DataHolder;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Users")
public class User {

	@Id
  public Long id;

	
	
public   String SHA;
public String dbname ;


 public String getDbname() {
	return dbname;
}

 
 
public void setDbname(String dbname) {
	this.dbname = dbname;
}
 
public String getSHA() {
	return SHA;
}
public void setSHA(String sHA) {
	SHA = sHA;
}

public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
	
}
