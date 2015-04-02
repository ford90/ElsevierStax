package com.elsevier.manager;

import java.util.Stack;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;

import com.elsevier.model.Article;
import com.elsevier.model.Tail;

public class TailManager {
	
	private Stack<StartElement> stack = new Stack<StartElement>();
	
	public void process(XMLEventReader reader, Article article, MainManager parent) throws XMLStreamException{
		Tail tail = new Tail();
		String temp = null;
		System.out.println("Inside tailManager");
	}
}
