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
	public Set<User> getFollwers(Integer userid) throws UserException {
		Optional<User> optional = uDao.findById(userid);
		if (optional.isEmpty()) {
			throw new UserException("User not found with this id");
		}
		Set<User> follower = optional.get().getFollowerSet();
		if (follower.size() == 0) {
			throw new UserException("No followers found");
		}
		return follower;
	}

	@Override
	public Set<User> getFollowing(Integer userid) throws UserException {
		Optional<User> optional = uDao.findById(userid);
		if (optional.isEmpty()) {
			throw new UserException("User not found with this id");
		}
		Set<User> following = optional.get().getFollowingSet();
		if (following.size() == 0) {
			throw new UserException("No followings found");
		}
		return following;
	}

	@Override
	public List<User> searchUserByName(String name) throws UserException {
		List<User> users = uDao.findByName(name);
		if (users.size() == 0) {
			throw new UserException("No user found");
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
		if (following.contains(usertofollow.get())) {
			throw new UserException(" You are already following this user");
		}
		following.add(usertofollow.get());
		// save might need
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
