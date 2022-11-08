package com.BlogApp.service;

import java.util.List;
import java.util.Set;

import com.BlogApp.exceptions.LoginException;
import com.BlogApp.exceptions.UserException;
import com.BlogApp.module.User;

public interface FollowersService {
	
	public List<User> getFollwersById(Integer userId) throws UserException, LoginException;

	public List<User> getFollwingById(Integer userId) throws UserException, LoginException;

//	public Set<User> getMutualById(Integer id, String sessionId) throws UserException, LoginException;
}
