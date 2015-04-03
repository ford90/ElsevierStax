package com.elsevier.manager;

import java.util.Stack;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;

import com.elsevier.model.Article;

public abstract class AbstractBaseManager implements IBaseManager{

	protected Stack<StartElement> stack = new Stack<StartElement>();
	@Override
	public abstract void process(XMLEventReader reader, Article article, IBaseManager parent) throws XMLStreamException;

}
