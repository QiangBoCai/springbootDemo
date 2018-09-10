package com.lance.pojo;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;  
	  
    private Long userId;  
  
    private String username;  
  
    private String email;  
  
    public Long getUserId() {  
        return userId;  
    }  
  
    public void setUserId(Long userId) {  
        this.userId = userId;  
    }  
  
    public String getUsername() {  
        return username;  
    }  
  
    public void setUsername(String username) {  
        this.username = username;  
    }  
  
    public String getEmail() {  
        return email;  
    }  
  
    public void setEmail(String email) {  
        this.email = email;  
    }  
  
}
