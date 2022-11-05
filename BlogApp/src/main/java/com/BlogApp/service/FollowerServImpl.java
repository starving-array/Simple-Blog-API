package com.BlogApp.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BlogApp.exceptions.LoginException;
import com.BlogApp.exceptions.UserException;
import com.BlogApp.module.CurrentSession;
import com.BlogApp.module.User;
import com.BlogApp.repository.FollowersDao;
import com.BlogApp.repository.SessionDao;
import com.BlogApp.repository.UserDao;

@Service
public class FollowerServImpl implements FollowersService{

	@Autowired
	private FollowersDao followersDao;
	@Autowired
	private SessionDao sessionDao;
	@Autowired
	private UserDao udao;
	@Override
	public Set<User> getFollwersById(Integer id, String sessionId) throws UserException, LoginException {
		// owner account details
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to first");
		}
		Optional<User> activeUser = udao.findById(cur.getUserId());
		if (activeUser.isEmpty()) {
			throw new LoginException("Please login with your account");
		}
		// account to look 
		Optional<User> usertofollow = udao.findById(id);
		if (usertofollow.isEmpty()) {
			throw new LoginException("No user found");
		}
		User activeAccount = activeUser.get();
		User otherUser = usertofollow.get(); 
		
		return null;
	}

	@Override
	public Set<User> getFollwingById(Integer id, String sessionId) throws UserException, LoginException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<User> getMutualById(Integer id, String sessionId) throws UserException, LoginException {
		// TODO Auto-generated method stub
		return null;
	}

} 
