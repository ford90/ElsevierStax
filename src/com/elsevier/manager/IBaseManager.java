package com.elsevier.manager;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;

import com.elsevier.model.Article;

public interface IBaseManager {
	public void process(XMLEventReader reader, Article article, IBaseManager parent) throws XMLStreamException;
}
