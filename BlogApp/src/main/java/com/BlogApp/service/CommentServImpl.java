package com.BlogApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BlogApp.exceptions.CommentException;
import com.BlogApp.exceptions.LoginException;
import com.BlogApp.exceptions.PostException;
import com.BlogApp.module.Comments;
import com.BlogApp.module.CurrentSession;
import com.BlogApp.module.Post;
import com.BlogApp.module.User;
import com.BlogApp.repository.CommentDao;
import com.BlogApp.repository.PostDao;
import com.BlogApp.repository.SessionDao;
import com.BlogApp.repository.UserDao;

@Service
public class CommentServImpl implements CommentsService {

	@Autowired
	private SessionDao sessionDao;

	@Autowired
	private UserDao udao;

	@Autowired
	private PostDao pdao;

	@Autowired
	private CommentDao cdao;

	@Override
	public List<Comments> getAllCommentsByPostId(Integer postId) throws PostException, CommentException {
		Optional<Post> postopt = pdao.findById(postId);
		if (postopt.isEmpty()) {
			throw new PostException("Post doesn't exist");
		}
		List<Comments> listOfComments = postopt.get().getPostComments();
		if (listOfComments.size() == 0) {
			throw new CommentException("No comment found for this post");
		}
		return listOfComments;
	}

	@Override
	public Comments getCommentsByCommentID(Integer commentId) throws CommentException {

		Optional<Comments> op = cdao.findById(commentId);
		if (op.isEmpty()) {
			throw new CommentException("No comment found for this post");
		}
		return op.get();
	}

	@Override
	public Comments createComment(Integer postId, Comments comments, String sessionId)
			throws PostException, LoginException {
		CurrentSession cur = sessionDao.findByUuid(sessionId); // user log in!
		if (cur == null) {
			throw new LoginException("Please log in to comment");
		}
		Optional<User> user = udao.findById(cur.getUserId());
		if (user.isEmpty()) {
			throw new LoginException("Please login with your account");
		}
		Optional<Post> postopt = pdao.findById(postId);
		if (postopt.isEmpty()) {
			throw new PostException("Post doesn't exist");
		}
		User userData = user.get();
		Post postData = postopt.get();
		// session id == post id
		if (userData.getUserId() != postData.getUser().getUserId()) {// post belong to user check
			throw new PostException("this post doesnot belongs to this id. Please log in with properid to update");
		}
		postData.getPostComments().add(comments);
		comments.setPostComment(postData);
		postData.setUser(userData);
		return cdao.save(comments);

	}

	@Override
	public Comments updateComment(Integer postId, Comments comments, String sessionId)
			throws PostException, CommentException, LoginException {
		CurrentSession cur = sessionDao.findByUuid(sessionId); // user log in!
		if (cur == null) {
			throw new LoginException("Please log in to comment");
		}
		Optional<User> user = udao.findById(cur.getUserId());
		if (user.isEmpty()) {
			throw new LoginException("Please login with your account");
		}
		Optional<Post> postopt = pdao.findById(postId);
		if (postopt.isEmpty()) {
			throw new PostException("Post doesn't exist");
		}
		User userData = user.get();
		Post postData = postopt.get();
		// session id == post id
		if (userData.getUserId() != postData.getUser().getUserId()) {// post belong to user check
			throw new PostException("this post doesnot belongs to this id. Please log in with properid to update");
		}
		Optional<Comments> comdt = cdao.findById(comments.getCommentId());
		if (comdt.isEmpty()) {
			throw new CommentException("Comment not exists");
		}
		Comments comdata = comdt.get();
		if (comdata.getPostComment().getPostId() != postData.getPostId()) {
			throw new CommentException("This comment doesn't belongs to this post");
		}
		postData.getPostComments().add(comments);
		comments.setPostComment(postData);
		postData.setUser(userData);
		pdao.save(postData);
		return comments;
	}

	@Override
	public Comments deleteComment(Integer postId, Integer commentID, String sessionId)
			throws PostException, CommentException, LoginException {
		CurrentSession cur = sessionDao.findByUuid(sessionId); // user log in!
		if (cur == null) {
			throw new LoginException("Please log in to comment");
		}
		Optional<User> user = udao.findById(cur.getUserId());
		if (user.isEmpty()) {
			throw new LoginException("Please login with your account");
		}
		Optional<Post> postopt = pdao.findById(postId);
		if (postopt.isEmpty()) {
			throw new PostException("Post doesn't exist");
		}
		User userData = user.get();
		Post postData = postopt.get();
		// session id == post id
		if (userData.getUserId() != postData.getUser().getUserId()) {// post belong to user check
			throw new PostException("this post doesnot belongs to this id. Please log in with properid to update");
		}
		Optional<Comments> comdt = cdao.findById(commentID);
		if (comdt.isEmpty()) {
			throw new CommentException("Comment not exists");
		}
		Comments comdata = comdt.get();
		if (comdata.getPostComment().getPostId() != postData.getPostId()) {
			throw new CommentException("This comment doesn't belongs to this post");
		}

//		postData.getPostComments().remove(comdata);
//		postData.setUser(userData);
//		pdao.save(postData);
		cdao.delete(comdata);
		return comdata;
	}

}
