package com.BlogApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BlogApp.module.Post;



public interface PostDao extends JpaRepository<Post, Integer>{

//	@Query("select c from Post c  LIMIT 0, 1")
//	public List<Post> getPostByPage(Integer offsetSize, Integer limitSize);
//
//	Pageable
}
