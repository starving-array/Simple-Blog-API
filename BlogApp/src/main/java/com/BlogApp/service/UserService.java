package com.BlogApp.service;

import java.util.List;
import java.util.Set;

import com.BlogApp.exceptions.LoginException;
import com.BlogApp.exceptions.UserException;
import com.BlogApp.module.User;

public interface UserService {

	public User createAccount(User user) throws UserException;
	
	public User updateAccount(User user, String sessionId) throws UserException, LoginException;
	
	public List<User> getAllUser() throws UserException;
	
	public Set<User> getFollwers(Integer userid) throws UserException;
	
	public Set<User> getFollowing(Integer userid) throws UserException;
	
	public List<User> searchUserByName(String name) throws UserException;
	
	public User follow(Integer id, String sessionId) throws UserException, LoginException;
	
	public User unFollow(Integer id, String sessionId) throws UserException, LoginException;
	

}
