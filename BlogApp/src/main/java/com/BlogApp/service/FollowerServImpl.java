package com.BlogApp.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BlogApp.exceptions.UserException;
import com.BlogApp.module.User;
import com.BlogApp.repository.FollowersDao;

@Service
public class FollowerServImpl implements FollowersService{

	@Autowired
	private FollowersDao followersDao;
	
	@Override
	public Set<User> getFollwersById(Integer id, String sessionId) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<User> getFollwingById(Integer id, String sessionId) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<User> getMutualById(Integer id, String sessionId) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

}
