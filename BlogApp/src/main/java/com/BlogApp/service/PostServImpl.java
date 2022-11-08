package com.BlogApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BlogApp.exceptions.LoginException;
import com.BlogApp.exceptions.PostException;
import com.BlogApp.module.CurrentSession;
import com.BlogApp.module.Post;
import com.BlogApp.module.User;
import com.BlogApp.repository.PostDao;
import com.BlogApp.repository.SessionDao;
import com.BlogApp.repository.UserDao;

@Service
public class PostServImpl implements PostService {

	@Autowired
	private SessionDao sessionDao;

	@Autowired
	private UserDao udao;

	@Autowired
	private PostDao pdao;

	@Override
	public List<Post> getAllPost() throws PostException {

		List<Post> ListOfPosts = pdao.findAll();
		if (ListOfPosts.size() == 0) {
			throw new PostException("No post found in the database");
		}
		return ListOfPosts;
	}

	@Override
	public Post getPostById(Integer postid) throws PostException {
		Optional<Post> postopt = pdao.findById(postid);
		if (postopt.isEmpty()) {
			throw new PostException("Post doesn't exist");
		}
		Post postData = postopt.get();
		return postData;
	}

	@Override
	public Post createPost(Post post, String sessionId) throws LoginException {
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to post");
		}
		Optional<User> user = udao.findById(cur.getUserId());
		if (user.isEmpty()) {
			throw new LoginException("Please login with your account");
		}
		User userData = user.get();
		post.setUser(userData);
		return pdao.save(post);
	}

	@Override
	public Post updatePost(Post post, String sessionId) throws PostException, LoginException {
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to post");
		}
		Optional<User> user = udao.findById(cur.getUserId());
		if (user.isEmpty()) {
			throw new LoginException("Please login with your account");
		}
		Optional<Post> postopt = pdao.findById(post.getPostId());
		if (postopt.isEmpty()) {
			throw new PostException("Post doesn't exist");
		}

		User userData = user.get();
		Post postData = postopt.get();
		if (userData.getUserId() != postData.getUser().getUserId()) {
			throw new PostException("this post doesnot belongs to this id. Please log in with properid to update");
		}
		if (post.getPostBody() != null) {
			postData.setPostBody(post.getPostBody());
		}
		if (post.getPostHeading() != null) {
			postData.setPostHeading(post.getPostHeading());
		}
		post.setUser(userData);
		if (postData.getPostComments().size() != 0) {
			post.getPostComments().addAll(postData.getPostComments());
		}
		return pdao.save(post);
	}

	@Override
	public Post deletepost(Integer postid, String sessionId) throws PostException, LoginException {
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to post");
		}
		Optional<User> user = udao.findById(cur.getUserId());
		if (user.isEmpty()) {
			throw new LoginException("Please login with your account");
		}
		Optional<Post> postopt = pdao.findById(postid);
		if (postopt.isEmpty()) {
			throw new PostException("Post doesn't exist");
		}

		User userData = user.get();
		Post postData = postopt.get();
		if (userData.getUserId() != postData.getUser().getUserId()) {
			throw new PostException("this post doesnot belongs to this id. Please log in with properid to update");
		}
		pdao.delete(postData);
		return postData;
	}

	@Override
	public List<Post> getPagination(Integer paegno) throws PostException {
		// default post size per page is 10;
		int postPerPage = 10;
		int offsetSize = (paegno - 1) * postPerPage + 1;
//		List<Post> list = pdao.getPostByPage(offsetSize, postPerPage); // limit not support by jpa
		// need to implement through Pagable later
		List<Post> list = pdao.findAll();
		if (list.size() == 0 || list.size()< offsetSize) {
			throw new PostException("No data found");
		}
		List<Post> result = new ArrayList<>();
		
		int limit = postPerPage + offsetSize; // 11
		
		if(list.size()<offsetSize+postPerPage) {
			limit = list.size();
		}else {
			limit--;
		}
		for(int i = offsetSize-1; i<limit; i++) {
			result.add(list.get(i));
		}
		// post perpage
		
		return result;
	}

//
	@Override
	public Integer getTotalPage() throws PostException {
		List<Post> list = pdao.findAll();
		return list.size();
	}

}
