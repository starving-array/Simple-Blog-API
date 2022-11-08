package com.BlogApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BlogApp.exceptions.LoginException;
import com.BlogApp.exceptions.PostException;
import com.BlogApp.module.Post;
import com.BlogApp.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired
	private PostService pservice;

	@CrossOrigin
	@GetMapping("/")
	public ResponseEntity<List<Post>> getAllPostH() throws PostException {
		List<Post> list = pservice.getAllPost();
		return new ResponseEntity<List<Post>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/{id}")
	public ResponseEntity<Post> getPostById(@PathVariable("id") Integer postId) throws PostException {
		Post post = pservice.getPostById(postId);
		return new ResponseEntity<Post>(post, HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping("/{idSes}")
	public ResponseEntity<Post> createPostH(@RequestBody Post post, @PathVariable("idSes") String sessionID)
			throws LoginException {
		Post postNewPost = pservice.createPost(post, sessionID);
		return new ResponseEntity<Post>(postNewPost, HttpStatus.CREATED);
	}

	@CrossOrigin
	@PutMapping("/{idSes}")
	public ResponseEntity<Post> updatePostH(@RequestBody Post post, @PathVariable("idSes") String sessionID)
			throws LoginException, PostException {
		Post postNewPost = pservice.updatePost(post, sessionID);
		return new ResponseEntity<Post>(postNewPost, HttpStatus.OK);
	}

	@CrossOrigin
	@DeleteMapping("{idpost}/{idSes}")
	public ResponseEntity<Post> deletetePostH(@PathVariable("idpost") Integer postid,
			@PathVariable("idSes") String sessionID) throws LoginException, PostException {
		Post postNewPost = pservice.deletepost(postid, sessionID);
		return new ResponseEntity<Post>(postNewPost, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/page:/{pageno}")
	public ResponseEntity<List<Post>> getPostByPage(@PathVariable("pageno") Integer pageNo) throws PostException {
		List<Post> list = pservice.getPagination(pageNo);
		return new ResponseEntity<List<Post>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/pagetotal")
	public ResponseEntity<Integer> getTotalPostSize() throws PostException {
		Integer pageTotal = pservice.getTotalPage();
		return new ResponseEntity<Integer>(pageTotal, HttpStatus.OK);
	}

}
