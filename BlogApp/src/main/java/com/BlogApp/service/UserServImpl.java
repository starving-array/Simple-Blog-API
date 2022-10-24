package com.BlogApp.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BlogApp.exceptions.LoginException;
import com.BlogApp.exceptions.UserException;
import com.BlogApp.module.CurrentSession;
import com.BlogApp.module.User;
import com.BlogApp.repository.SessionDao;
import com.BlogApp.repository.UserDao;

@Service
public class UserServImpl implements UserService {

	@Autowired
	private UserDao uDao;
	@Autowired
	private SessionDao sessionDao;
	@Autowired
	private UserDao udao;

	@Override
	public User createAccount(User user) throws UserException {
		User emailCheck = uDao.findByEmail(user.getEmail());
		if (emailCheck != null) {
			throw new UserException("Email already registered with us");
		}
		User phoneCheck = uDao.findByContact(user.getContact());
		if (phoneCheck != null) {
			throw new UserException("Phone number already registered with us");
		}
		return uDao.save(user);
	}

	@Override
	public List<User> getAllUser() throws UserException {
		List<User> users = uDao.findAll();
		if (users.isEmpty()) {
			throw new UserException("No user in database");
		}
		return users;
	}


	@Override
	public User follow(Integer id, String sessionId) throws UserException, LoginException {
		// session id verify for active user
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to first");
		}
		Optional<User> activeUser = udao.findById(cur.getUserId());
		if (activeUser.isEmpty()) {
			throw new LoginException("Please login with your account");
		}

		Optional<User> usertofollow = udao.findById(id);
		if (usertofollow.isEmpty()) {
			throw new LoginException("No user found");
		}
		// check if already exists
		Set<User> following = activeUser.get().getFollowingSet();
		Set<User> followers = usertofollow.get().getFollowerSet();
		if (following.contains(usertofollow.get())) {
			throw new UserException(" You are already following this user");
		}
		following.add(usertofollow.get());
		followers.add(activeUser.get());
		udao.save(usertofollow.get());
		udao.save(activeUser.get());
		return usertofollow.get();
	}

	@Override
	public User unFollow(Integer id, String sessionId) throws UserException, LoginException {
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to first");
		}
		Optional<User> activeUser = udao.findById(cur.getUserId());
		if (activeUser.isEmpty()) {
			throw new LoginException("Please login with your account");
		}

		Optional<User> usertofollow = udao.findById(id);
		if (usertofollow.isEmpty()) {
			throw new LoginException("No user found");
		}
		// check if already exists
		Set<User> following = activeUser.get().getFollowingSet();
		if (!following.contains(usertofollow.get())) {
			throw new UserException("You are not following this user");
		}
		following.remove(usertofollow.get());
		// save might need
		udao.save(usertofollow.get());
		udao.save(usertofollow.get());
		return usertofollow.get();
	}

	@Override
	public User updateAccount(User user, String sessionId) throws UserException, LoginException {
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to first");
		}
		Optional<User> activeUser = udao.findById(cur.getUserId());
		if (activeUser.isEmpty()) {
			throw new LoginException("Please login with your account");
		}
		User emailCheck = uDao.findByEmail(user.getEmail());
		if (emailCheck != null) {
			throw new UserException("Email already registered with us");
		}
		User phoneCheck = uDao.findByContact(user.getContact());
		if (phoneCheck != null) {
			throw new UserException("Phone number already registered with us");
		}
		
		return udao.save(user);
	}

}
