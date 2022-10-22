package com.BlogApp.service;

import java.util.List;

import com.BlogApp.exceptions.CommentException;
import com.BlogApp.exceptions.LoginException;
import com.BlogApp.exceptions.PostException;
import com.BlogApp.module.Comments;

public interface CommentsService {

	public List<Comments> getAllCommentsByPostId(Integer postId) throws PostException, CommentException;

	public Comments getCommentsByCommentID(Integer commentId) throws CommentException;

	public Comments createComment(Integer postId, Comments comments, String sessionId)
			throws PostException, LoginException;

	public Comments updateComment(Integer postId, Comments comments, String sessionId)
			throws PostException, CommentException, LoginException;

	public Comments deleteComment(Integer postId, Integer commentID, String sessionId)
			throws PostException, CommentException, LoginException;

}
