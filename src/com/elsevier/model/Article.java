package com.elsevier.model;

public class Article {
	
	private Item item;
	private Head head;
	private Tail tail;
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Head getHead() {
		return head;
	}
	public void setHead(Head head) {
		this.head = head;
	}
	public Tail getTail() {
		return tail;
	}
	public void setTail(Tail tail) {
		this.tail = tail;
	}
	
	
	public String toString(){
		return item + " : \n" + head + " : \n" + tail;
	}

}
