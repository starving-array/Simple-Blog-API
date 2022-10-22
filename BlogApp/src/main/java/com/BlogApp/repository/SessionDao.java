package com.BlogApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BlogApp.module.CurrentSession;

@Repository
public interface SessionDao extends JpaRepository<CurrentSession, Integer> {

	public CurrentSession findByUuid(String uuid);

}