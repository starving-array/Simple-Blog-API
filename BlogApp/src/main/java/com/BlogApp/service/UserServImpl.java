package com.BlogApp.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BlogApp.exceptions.LoginException;
import com.BlogApp.exceptions.UserException;
import com.BlogApp.module.CurrentSession;
import com.BlogApp.module.Followers;
import com.BlogApp.module.User;
import com.BlogApp.repository.FollowersDao;
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

	@Autowired
	private FollowersDao followersDao;

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
	public List<User> searchUserByName(String name) throws UserException {
		List<User> users = uDao.findByName(name);
		if (users.size() == 0) {
			throw new UserException("No user found");
		}
		return users;
	}

	@Override
	public Followers follow(Integer userId, String sessionId) throws UserException, LoginException {
		// session id verify for active user
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to first");
		}
		Optional<User> activeUserOptional = udao.findById(cur.getUserId());
		if (activeUserOptional.isEmpty()) {
			throw new LoginException("Please login with your account");
		}
		User activeUser = activeUserOptional.get();
		// ============================================================================

		Optional<User> usertofollowOptional = udao.findById(userId);
		if (usertofollowOptional.isEmpty()) {
			throw new LoginException("No user found");
		}
		// check if already exists
		User usertofollow = usertofollowOptional.get();
		// ============================================================================

		Followers newFollowers = new Followers();
		newFollowers.setFollower(activeUser);
		newFollowers.setFollowing(usertofollow);
		activeUser.getFollowingList().add(newFollowers);
		usertofollow.getFollowerList().add(newFollowers);

		return followersDao.save(newFollowers);
	}

	@Override
	public Followers unFollow(Integer userId, String sessionId) throws UserException, LoginException {
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to first");
		}
		Optional<User> activeUserOptional = udao.findById(cur.getUserId());
		if (activeUserOptional.isEmpty()) {
			throw new LoginException("Please login with your account");
		}
		User activeUser = activeUserOptional.get();
		// ============================================================================

		Optional<User> usertofollowOptional = udao.findById(userId);
		if (usertofollowOptional.isEmpty()) {
			throw new LoginException("No user found");
		}
		// check if already exists
		User usertofollow = usertofollowOptional.get();
		// check if already exists
		// ============================================================================
		Followers followers = followersDao.getByFollowerFollowing(activeUser.getUserId(), usertofollow.getUserId());
		if(followers==null) {
			throw new UserException("You are not following "+usertofollow.getName());
		}
		followersDao.delete(followers);	
		return followers;
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
