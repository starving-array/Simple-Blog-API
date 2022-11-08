package com.BlogApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BlogApp.exceptions.LoginException;
import com.BlogApp.exceptions.UserException;
import com.BlogApp.module.User;
import com.BlogApp.repository.FollowersDao;
import com.BlogApp.repository.SessionDao;
import com.BlogApp.repository.UserDao;

@Service
public class FollowerServImpl implements FollowersService {

	@Autowired
	private FollowersDao followersDao;
	@Autowired
	private SessionDao sessionDao;
	@Autowired
	private UserDao udao;

	@Override
	public List<User> getFollwersById(Integer userId) throws UserException, LoginException {
		// owner account details

		Optional<User> userOptional = udao.findById(userId);
		if (userOptional.isEmpty()) {
			throw new LoginException("No user found");
		}
		User activeAccount = userOptional.get();
		List<User> followersList = followersDao.getByFollower(userId);
		if (followersList.size() == 0) {
			throw new UserException("You have no follower");
		}
		return null;
	}

	@Override
	public List<User> getFollwingById(Integer userId) throws UserException, LoginException {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public Set<User> getMutualById(Integer id, String sessionId) throws UserException, LoginException {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
