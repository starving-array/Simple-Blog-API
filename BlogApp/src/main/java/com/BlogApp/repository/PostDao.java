package com.BlogApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BlogApp.module.Post;


@Repository
public interface PostDao extends JpaRepository<Post, Integer>{

}
