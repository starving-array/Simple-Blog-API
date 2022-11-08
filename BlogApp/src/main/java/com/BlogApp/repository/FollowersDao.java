package com.BlogApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.BlogApp.module.Connection;
import com.BlogApp.module.User;

public interface FollowersDao extends JpaRepository<Connection, Integer> {

	@Query("select c from Connection c where following=?1 and  follower=?2")
	public Connection getByFollowerFollowing(User foId, User follId );

	@Query("select c.follower from Connection c where following=?1")
	public List<User> getFollower(User user);
}

//user_id=?1 and  followingId=?2