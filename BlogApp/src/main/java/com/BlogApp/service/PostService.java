package com.BlogApp.service;

import java.util.List;

import com.BlogApp.exceptions.LoginException;
import com.BlogApp.exceptions.PostException;
import com.BlogApp.module.Post;

public interface PostService {

	public List<Post> getAllPost() throws PostException;

	public Post getPostById(Integer postid) throws PostException;

	public Post createPost(Post post, String sessionId) throws LoginException;

	public Post updatePost(Post post, String sessionId) throws PostException, LoginException;

	public Post deletepost(Integer postid, String sessionId) throws PostException, LoginException;

	public List<Post> getPagination(Integer paegno) throws PostException;
//
	public Integer getTotalPage() throws PostException;
}
