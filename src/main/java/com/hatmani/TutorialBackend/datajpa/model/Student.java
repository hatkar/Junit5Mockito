package com.hatmani.TutorialBackend.datajpa.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class Student implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
private String name;
private String email;
public Long getId() {
	return id;
} 
public void setId(Long id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public Student( String name, String email) {
	super();
	
	this.name = name;
	this.email = email;
}
public Student(Long id, String name, String email) {
	super();
	this.id = id;
	this.name = name;
	this.email = email;
}
public Student() {
	super();
	// TODO Auto-generated constructor stub
}


	
}
