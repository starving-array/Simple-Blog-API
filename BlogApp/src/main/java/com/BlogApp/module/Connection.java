package com.BlogApp.module;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Connection {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer followerId;
	
	@JsonIgnore
	private LocalDate dateFollowed;
	// timestamp edit
	@JsonIgnore
	private LocalDate dateUnfollowed;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "userId")
	private User follower;
	
	@ManyToOne
	@JoinColumn(name="followingId", referencedColumnName = "userId")
	private User following;
}
