package com.elsevier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;

import javax.xml.stream.XMLStreamException;


import com.elsevier.model.Article;
import com.eslevier.manager.MainManager;


public class Main {

	public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
		
		XMLInputFactory inputFactory 	= XMLInputFactory.newInstance();
		InputStream		inputStream	 	= new FileInputStream("C:\\Users\\peter.ford\\workspace\\ElsevierStAX\\resources\\S0020025514001625.xml");
		
		XMLEventReader	reader			= inputFactory.createXMLEventReader(inputStream);
		
		MainManager manager = new MainManager(reader);
		
		Article article = manager.start();
		System.out.println("Article");
		
		System.out.println(article);		
	}
}
