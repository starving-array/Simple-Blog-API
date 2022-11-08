package com.BlogApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BlogApp.module.Comments;



public interface CommentDao extends JpaRepository<Comments, Integer> {

}
