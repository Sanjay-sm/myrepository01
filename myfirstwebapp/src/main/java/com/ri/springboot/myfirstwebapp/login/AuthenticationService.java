package com.ri.springboot.myfirstwebapp.login;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService 
{
    public boolean authenticate(String username, String password) 
    {
    	boolean isvalidUsername = username.equalsIgnoreCase("rockinterview");
    	boolean isValidPassword = password.equalsIgnoreCase("password");
    	return isValidPassword && isvalidUsername;
    }
}
