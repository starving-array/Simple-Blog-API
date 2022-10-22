package com.BlogApp.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BlogApp.exceptions.LoginException;
import com.BlogApp.exceptions.UserException;
import com.BlogApp.module.CurrentSession;
import com.BlogApp.module.LoginDTO;
import com.BlogApp.module.User;
import com.BlogApp.repository.SessionDao;
import com.BlogApp.repository.UserDao;

import net.bytebuddy.utility.RandomString;

@Service
public class LoginServImpl implements LoginService {

	@Autowired
	private UserDao uDao;

	@Autowired
	private SessionDao sDao;
	
	@Override
	public String logIntoAccount(LoginDTO dto) throws LoginException {

		User existingUser = uDao.findByContact(dto.getMobileNo());

		if (existingUser == null) {

			throw new LoginException("Please Enter a valid mobile number");

		}

		Optional<CurrentSession> validUserSessionOpt = sDao.findById(existingUser.getUserId());

		if (validUserSessionOpt.isPresent()) {

			throw new LoginException("User already Logged In with this number");

		}

		if (existingUser.getPassword().equals(dto.getPassword())) {

			String key = RandomString.make(5);

			CurrentSession currentUserSession = new CurrentSession(existingUser.getUserId(), key,
					LocalDateTime.now());

			sDao.save(currentUserSession);

			return currentUserSession.toString();
		} else
			throw new LoginException("Please Enter a valid password");

	}

	// **************************************************

	@Override
	public String logOutFromAccount(String key) throws LoginException {

		CurrentSession validUserSession = sDao.findByUuid(key);

		if (validUserSession == null) {
			throw new LoginException("User Not Logged In with this number");
		}

		sDao.delete(validUserSession);

		return "Logged Out..!!";

	}

	// **************************************************
//
//	@Override
//	public String deleteUser(String key, Integer userId) throws LoginException, UserException {
//		// TODO Auto-generated method stub
//
//		CurrentUserSession validUserSession = sDao.findByUuid(key);
//		Optional<User> validUser = uDao.findById(userId);
//
//		if (validUserSession == null) {
//			throw new LoginException("Please enter correct key..!!");
//
//		}
//		if (validUser.isEmpty()) {
//			throw new UserException("Please enter correct user id..!!");
//
//		}
//
//		if (validUserSession.getUserId() == validUser.get().getUserId()) {
//			sDao.delete(validUserSession);
//			uDao.delete(validUser.get());
//		}
//
//		return "Account deleted..!!";
//
//	}

	// **************************************************

	@Override
	public User getUser(String key, Integer userId) throws LoginException, UserException {
		// TODO Auto-generated method stub
		User user = new User();
		CurrentSession validUserSession = sDao.findByUuid(key);
		Optional<User> validUser = uDao.findById(userId);

		if (validUserSession == null) {
			throw new LoginException("Please enter correct key..!!");

		}
		if (validUser.isEmpty()) {
			throw new UserException("Please enter correct user id..!!");

		}

		if (validUserSession.getUserId() == validUser.get().getUserId()) {
			user = validUser.get();
			return user;
		}
		throw new UserException("Please enter correct user id..!!");

	}
}
