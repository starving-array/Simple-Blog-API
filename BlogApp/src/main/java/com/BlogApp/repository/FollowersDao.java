package com.BlogApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.BlogApp.module.Followers;

@Repository
public interface FollowersDao extends JpaRepository<Followers, Integer> {

	@Query("select c from Followers c where following=?1 and  follower=?2")
	public Followers getByFollowerFollowing(Integer followingId, Integer followerId);
}
