package com.BlogApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BlogApp.module.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

	public User findByEmail(String email);

	public User findByContact(String contact);
	
//	@Query("select c from User c where name like =?%1%")
	public List<User> findByName(String name);


}