package com.BlogApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BlogApp.module.Comments;


@Repository
public interface CommentDao extends JpaRepository<Comments, Integer> {

}
