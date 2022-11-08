package com.BlogApp.module;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;

	@NotBlank(message = "Name filed can't be empty")
	@Size(min = 3, max = 30, message = "Name should be 3 - 30 char")
	private String name;

	@NotBlank(message = "Contact filed can't be empty")
	@Size(min = 10, max = 10, message = "Mobile number is invalid. Must be 10 digit")
	private String contact;

	@NotBlank(message = "Password filed can't be empty")
//	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,100}$",
//	message = "Please enter a valid password which include upperCase") 
	@Size(min = 8, max = 20, message = "Password should be 8-20 digits")
	private String password;

//	@Past(message = "Date of birth can't be future")
//	@NotBlank(message = "Date of Birth filed can't be empty")
//	private LocalDate dateOfBirth;

    // timestamp create
	// timestamp edit
	@Email(message = "Please enter a valid email")
	@NotBlank(message = "Email filed can't be empty")
	private String email;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Post> listOfPost;

	@JsonIgnore
	@OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
	private List<Connection> followingList;

	@JsonIgnore
	@OneToMany(mappedBy = "following", cascade = CascadeType.ALL)
	private List<Connection> followerList;

}
