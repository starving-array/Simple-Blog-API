package com.BlogApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BlogApp.exceptions.LoginException;
import com.BlogApp.exceptions.UserException;
import com.BlogApp.module.User;
import com.BlogApp.service.FollowersService;

@RestController
@RequestMapping("/connect")
public class ConnectionController {

	@Autowired
	private FollowersService followersService;

	@CrossOrigin
	@GetMapping("/following/{userid}")
	public ResponseEntity<List<User>> getFollowing(@PathVariable("userid") Integer userId) throws UserException, LoginException {
		List<User> list = followersService.getFollwingById(userId);
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}
	@CrossOrigin
	@GetMapping("/follower/{userid}")
	public ResponseEntity<List<User>> getFollower(@PathVariable("userid") Integer userId) throws UserException, LoginException {
		List<User> list = followersService.getFollwersById(userId);
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}
}
