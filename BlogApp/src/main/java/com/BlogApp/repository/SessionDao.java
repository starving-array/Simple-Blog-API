package com.BlogApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BlogApp.module.CurrentSession;


public interface SessionDao extends JpaRepository<CurrentSession, Integer> {

	public CurrentSession findByUuid(String uuid);

}