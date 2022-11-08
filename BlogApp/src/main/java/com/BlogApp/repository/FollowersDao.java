package com.BlogApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.BlogApp.module.Followers;
import com.BlogApp.module.User;


public interface FollowersDao extends JpaRepository<Followers, Integer> {

	@Query("select c.follower from Followers c")
	public List<User> getByFollower(Integer id);
	
	@Query("select c from Followers c ")
	public List<User> getByFollowing(Integer id);
}
