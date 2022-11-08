package com.BlogApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BlogApp.exceptions.CommentException;
import com.BlogApp.exceptions.LoginException;
import com.BlogApp.exceptions.PostException;
import com.BlogApp.module.Comments;
import com.BlogApp.service.CommentsService;

@RestController
@RequestMapping("/comments")
public class CommentContoller {

	@Autowired
	private CommentsService cService;

	@GetMapping("/post/{postId}")
	public ResponseEntity<List<Comments>> getAllCommentByPost(@PathVariable("postId") Integer postId)
			throws PostException, CommentException {
		List<Comments> comments = cService.getAllCommentsByPostId(postId);
		return new ResponseEntity<List<Comments>>(comments, HttpStatus.OK);
	}

	@GetMapping("/{commentId}")
	public ResponseEntity<Comments> getCommentById(@PathVariable("commentId") Integer cId) throws CommentException {
		Comments comments = cService.getCommentsByCommentID(cId);
		return new ResponseEntity<Comments>(comments, HttpStatus.OK);
	}

	@PostMapping("/{postid}/{session}")
	public ResponseEntity<Comments> createNewCommentH(@PathVariable("postid") Integer postid,
			@RequestBody Comments comments, @PathVariable("session") String sessionId)
			throws PostException, LoginException {
		Comments newcComments = cService.createComment(postid, comments, sessionId);
		return new ResponseEntity<Comments>(newcComments, HttpStatus.CREATED);
	}

	@PutMapping("/{postid}/{session}")
	public ResponseEntity<Comments> updateCommentH(@PathVariable("postid") Integer postid,
			@RequestBody Comments comments, @PathVariable("session") String sessionId)
			throws PostException, LoginException, CommentException {
		Comments newcComments = cService.updateComment(postid, comments, sessionId);
		return new ResponseEntity<Comments>(newcComments, HttpStatus.OK);
	}

	@DeleteMapping("/{postid}/{comid}/{session}")
	public ResponseEntity<Comments> deleteCommentH(@PathVariable("postid") Integer postid,
			@PathVariable("comid") Integer comments, @PathVariable("session") String sessionId)
			throws PostException, LoginException, CommentException {
		Comments newcComments = cService.deleteComment(postid, comments, sessionId);
		return new ResponseEntity<Comments>(newcComments, HttpStatus.OK);
	}
}
