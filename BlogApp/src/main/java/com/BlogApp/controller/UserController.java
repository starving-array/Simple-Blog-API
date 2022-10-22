package com.BlogApp.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BlogApp.exceptions.LoginException;
import com.BlogApp.exceptions.UserException;
import com.BlogApp.module.User;
import com.BlogApp.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService uService;
	
	@PostMapping("/new")
	public ResponseEntity<User> createAccountH(@Valid @RequestBody User user) throws UserException{
		User newUser = uService.createAccount(user);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
	
	@PatchMapping("/update/{sessionId}")
	public ResponseEntity<User> updateAccountH( @RequestBody User user, @PathVariable("sessionId") String sessionId) throws UserException, LoginException{
		User newUser = uService.updateAccount(user, sessionId);
		return new ResponseEntity<User>(newUser, HttpStatus.OK);
	}
	
	@GetMapping("/followers/{userId}")
	public ResponseEntity<Set<User>> getFollowesH(@PathVariable("userId") Integer userId) throws UserException{
		Set<User> folloerSet = uService.getFollwers(userId);
		return new ResponseEntity<Set<User>>(folloerSet, HttpStatus.OK);
	}
	
	@GetMapping("/following/{userId}")
	public ResponseEntity<Set<User>> getFollowingH(@PathVariable("userId") Integer userId) throws UserException{
		Set<User> folloerSet = uService.getFollowing(userId);
		return new ResponseEntity<Set<User>>(folloerSet, HttpStatus.OK);
	}
	
	@GetMapping("/search/{name}")
	public ResponseEntity<List<User>> searchUserByNameH(@PathVariable("name") String name) throws UserException{
		List<User> list = uService.searchUserByName(name);
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}
	
	@PatchMapping("/follow/{userId}/{sessionId}")
	public ResponseEntity<User> followUser(@Valid @PathVariable("userId") Integer userId, @PathVariable("sessionId") String sessionId) throws UserException, LoginException{
		User user = uService.follow(userId, sessionId);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PatchMapping("/unfollow/{userId}/{sessionId}")
	public ResponseEntity<User> unFollowUser(@Valid @PathVariable("userId") Integer userId, @PathVariable("sessionId") String sessionId) throws UserException, LoginException{
		User user = uService.unFollow(userId, sessionId);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	
	
}