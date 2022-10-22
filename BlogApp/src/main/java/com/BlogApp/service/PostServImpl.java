package com.BlogApp.service;

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
		post.setUser(userData);
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
		// TODO Auto-generated method stub
		return null;
	}

}
