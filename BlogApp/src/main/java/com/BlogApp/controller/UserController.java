package com.BlogApp.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BlogApp.exceptions.LoginException;
import com.BlogApp.exceptions.UserException;
import com.BlogApp.module.Connection;
import com.BlogApp.module.User;
import com.BlogApp.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService uService;

	@CrossOrigin
	@PostMapping("/new")
	public ResponseEntity<User> createAccountH(@Valid @RequestBody User user) throws UserException {
		User newUser = uService.createAccount(user);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}

	@CrossOrigin
	@PatchMapping("/update/{sessionId}")
	public ResponseEntity<User> updateAccountH(@RequestBody User user, @PathVariable("sessionId") String sessionId)
			throws UserException, LoginException {
		User newUser = uService.updateAccount(user, sessionId);
		return new ResponseEntity<User>(newUser, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/search/{name}")
	public ResponseEntity<List<User>> searchUserByNameH(@PathVariable("name") String name) throws UserException {
		List<User> list = uService.searchUserByName(name);
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@PatchMapping("/follow/{userId}/{sessionId}")
	public ResponseEntity<Connection> followUser(@Valid @PathVariable("userId") Integer userId,
			@PathVariable("sessionId") String sessionId) throws UserException, LoginException {
		Connection user = uService.follow(userId, sessionId);
		return new ResponseEntity<Connection>(user, HttpStatus.OK);
	}

	@CrossOrigin
	@PatchMapping("/unfollow/{userId}/{sessionId}")
	public ResponseEntity<Connection> unFollowUser(@Valid @PathVariable("userId") Integer userId,
			@PathVariable("sessionId") String sessionId) throws UserException, LoginException {
		Connection user = uService.unFollow(userId, sessionId);
		return new ResponseEntity<Connection>(user, HttpStatus.OK);
	}

}
