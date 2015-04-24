package com.elsevier.model;

public class Author {
	
	private String firstName;
	private String lastName;
	private String id;
	private String affiliation;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		firstName.replaceAll("$#", "&#");
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		lastName.replace("$#", "&#");
		this.lastName = lastName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAffiliation() {
		return affiliation;
	}
	public void setAffiliation(String affiliation) {
		if(affiliation.length() > 512)
			throw new IllegalArgumentException();
		this.affiliation = affiliation;
	}
	
	public String toString(){
		return firstName + " " + lastName + " : " + affiliation + "\n";
	}
}
