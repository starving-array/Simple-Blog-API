package com.BlogApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BlogApp.exceptions.LoginException;
import com.BlogApp.exceptions.UserException;
import com.BlogApp.module.Connection;
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
	public List<User> getFollwingById(Integer userId) throws UserException, LoginException {
		// owner account details

		Optional<User> userOptional = udao.findById(userId);
		if (userOptional.isEmpty()) {
			throw new LoginException("No user found");
		}
		User activeAccount = userOptional.get();
		List<Connection> listOfFollowers = activeAccount.getFollowingList();
		List<User> userFollowingList = new ArrayList<>();
		for (int i = 0; i < listOfFollowers.size(); i++) {
			userFollowingList.add(listOfFollowers.get(i).getFollowing());
		}

		if (userFollowingList.size() == 0) {
			throw new UserException("You are not following anyone");
		}
		return userFollowingList;
	}

	@Override
	public List<User> getFollwersById(Integer userId) throws UserException, LoginException {
		Optional<User> userOptional = udao.findById(userId);
		if (userOptional.isEmpty()) {
			throw new LoginException("No user found");
		}
		User activeAccount = userOptional.get();
		List<Connection> listOfFollowers = activeAccount.getFollowerList();
		List<User> userFollowingList = new ArrayList<>();
		for (int i = 0; i < listOfFollowers.size(); i++) {
			userFollowingList.add(listOfFollowers.get(i).getFollower());
		}

		if (userFollowingList.size() == 0) {
			throw new UserException("You have no follower yet");
		}
		return userFollowingList;
	}

//	@Override
//	public Set<User> getMutualById(Integer id, String sessionId) throws UserException, LoginException {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
