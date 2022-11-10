package com.BlogApp.module;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer postId;
	private String postHeading;
	private String postBody;
	@JsonIgnore
	private LocalDate dateCreated;
	// timestamp edit
	@JsonIgnore
	private LocalDate dateModified;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "User_Id", referencedColumnName = "userId")
	private User user;

	@JsonIgnore
	@OneToMany(mappedBy = "postComment", cascade = CascadeType.ALL)
	private List<Comments> postComments;
}
