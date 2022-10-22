package com.BlogApp.service;


import com.BlogApp.exceptions.LoginException;
import com.BlogApp.exceptions.UserException;
import com.BlogApp.module.LoginDTO;
import com.BlogApp.module.User;

public interface LoginService {
	public String logIntoAccount(LoginDTO dto) throws LoginException;

	public String logOutFromAccount(String key) throws LoginException;

//	public String deleteUser(String key, Integer userId)throws LoginException, UserException;

	public User getUser(String key, Integer userId) throws LoginException, UserException;
}
