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
public class Comments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer commentId;
	private String commentBody;
	@JsonIgnore
	private LocalDate dateCreated;
	// timestamp edit
	@JsonIgnore
	private LocalDate dateModified;
	
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "Post_id", referencedColumnName = "postId")
	private Post postComment;
}
