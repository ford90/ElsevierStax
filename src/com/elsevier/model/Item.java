package com.elsevier.model;

public class Item {
	private String pii;
	private String doi;
	private String copyRight;
	
	public String getPii() {
		return pii;
	}
	public void setPii(String pii) {
		this.pii = pii;
	}
	public String getDoi() {
		return doi;
	}
	public void setDoi(String doi) {
		this.doi = doi;
	}
	public String getCopyRight() {
		return copyRight;
	}
	public void setCopyRight(String copyRight) {
		this.copyRight = copyRight;
	}
	
	public String toString(){
		return pii + " " + doi + " " + copyRight;
	}
}
