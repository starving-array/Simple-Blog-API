package com.BlogApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BlogApp.module.Followers;

@Repository
public interface FollowersDao extends JpaRepository<Followers, Integer> {

}
