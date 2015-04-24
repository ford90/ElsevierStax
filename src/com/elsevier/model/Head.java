package com.elsevier.model;

import java.util.List;

public class Head {

	private String title;
	private String subTitle;
	private String abs;
	private List<Author> authors;
	private List<String> keywords;
	
	public void setTitle(String title){
		
		if(title.contains(":")){
			this.subTitle = title.substring(title.indexOf(":")+1, title.length());
			this.title    = title.substring(0, title.indexOf(":"));
		}
		this.title = title;
	}
	public String getTitle(){
		return title;
	}
	
	public void setAbs(String abs){
		this.abs = abs;
	}
	public String getAbs(){
		return abs;
	}
	public void setAuthors(List<Author> authors){
		this.authors = authors;
	}
	public List<Author> getAuthors(){
		return this.authors;
	}
	public List<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	public String toString(){
		return title + " : " + subTitle + " :\n " + abs + " :\n " + keywords + ":\n " +
				authors.toString();
	}
}
