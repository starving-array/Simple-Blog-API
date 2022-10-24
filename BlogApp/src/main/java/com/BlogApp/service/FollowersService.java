package com.BlogApp.service;

import java.util.Set;

import com.BlogApp.exceptions.UserException;
import com.BlogApp.module.User;

public interface FollowersService {
	
	public Set<User> getFollwersById(Integer id, String sessionId) throws UserException;

	public Set<User> getFollwingById(Integer id, String sessionId) throws UserException;

	public Set<User> getMutualById(Integer id, String sessionId) throws UserException;
}
