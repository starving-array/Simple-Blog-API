package com.BlogApp.module;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Followers {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer followerId;
	
	

	@ManyToMany
	@JoinTable(name = "Followers", 
	joinColumns=@JoinColumn(name="To", referencedColumnName = "followerId"),
	inverseJoinColumns= @JoinColumn(name="From", referencedColumnName = "userID"))
	private Set<User> followerSet = new HashSet<>();
}
