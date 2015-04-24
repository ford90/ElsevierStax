package com.elsevier.model;

import java.util.List;

public class Tail {
	private List<String> references;
	
	public void setReferences(List<String> references){
		this.references = references;
	}
	
	public List<String> getReferences(){
		return this.references;
	}
	
	@Override
	public String toString(){
		return references.toString();
	}

	
}
