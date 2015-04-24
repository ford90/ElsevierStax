package com.elsevier.manager;


import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import com.elsevier.model.Article;
import com.elsevier.model.Tail;

public class TailManager extends AbstractBaseManager {
	private List<String> references = new ArrayList<String>();
	
	@Override
	public void process(XMLEventReader reader, Article article, IBaseManager parent) throws XMLStreamException {
		System.out.println("Inside tailManager");
		Tail tail = new Tail();
		StringBuilder builder = new StringBuilder();
		XMLEvent event = null;
		while(reader.hasNext()){
			event = reader.nextEvent();
			if(event.isStartElement()){
				stack.add(event.asStartElement());
				
				/*
				if(event.asStartElement().getName().toString().equals("other-ref")){
					System.out.println("inside other-ref : " + 
							stack.get(stack.size()-2).getName().toString());
				}*/
				
				
				if( (event.asStartElement().getName().toString().equals("reference") || event.asStartElement().getName().toString().equals("other-ref") ) && 
						stack.get(stack.size()-2).getName().toString().equals("bib-reference") ){
					builder.append(event.toString());
					
					while(reader.hasNext()){
						event = reader.nextEvent();
						builder.append(event.toString());
						
						if(event.isEndElement()){
							if(event.asEndElement().getName().toString().equals("reference") || event.asEndElement().getName().toString().equals("other-ref") ){
								String reference = fixReference(builder);
								references.add(reference);
//								System.out.println(builder.toString());
								builder.delete(0, builder.length());
								break;
							}	
						}
					}
				}
				
			}
			
			if(event.isEndElement()){
				if(stack.size() > 0){
					stack.pop();
				}
				if(event.asEndElement().getName().toString().equals("tail")){
					tail.setReferences(references);
					article.setTail(tail);
				}
			}
		}
	}
	
	private String fixReference(StringBuilder builder){
		String temp = builder.toString();
		temp = temp.replaceAll("\\n", "");
		temp = temp.replaceAll("\\s+", " ");
		temp = temp.trim();
		temp = temp.replaceAll("<reference.*?>(.*)</reference>", "$1");
		temp = temp.replaceAll("<contribution(.*?)>(.*)</contribution>", "$2");
		temp = temp.replaceAll("<authors>(.*?)</authors>", "$1");
		temp = temp.replaceAll("<author>(.*?)</author>", "$1");
		temp = temp.replaceAll("<given-name>(.*?)</given-name>", "$1 ");
		temp = temp.replaceAll("</surname>   <title>", "</surname>. <title>");
		temp = temp.replaceAll("<name>(.*)</name>", "$1");
		temp = temp.replaceAll("<surname>(.*?)</surname>", "$1, ");
		temp = temp.replaceAll("<title>(.*?)</title>", "$1");
		temp = temp.replaceAll("<doi>(.*)</doi>", "$1");
		temp = temp.replaceAll("<edited-book>(.*?)</edited-book>", "$1");
		temp = temp.replaceAll("<book-series>(.*?)</book-series>", "$1");
		temp = temp.replaceAll("<book>(.*)</book>", "$1");
		temp = temp.replaceAll("<publisher>(.*)</publisher>", "$1");
		temp = temp.replaceAll("<maintitle>(.*?)</maintitle>", "$1:");
		temp = temp.replaceAll("<host>(.*?)</host>", "$1");
		temp = temp.replaceAll("<volume-nr>(.*?)</volume-nr>", "Vol. $1 ");
		temp = temp.replaceAll("<issue>(.*?)</issue>", "$1, ");
		temp = temp.replaceAll("<series>(.*?)</series>", "$1 ");
		temp = temp.replaceAll("<issue-nr>(.*?)</issue-nr>", "Issue $1");
		temp = temp.replaceAll("<date>(.*)</date>", "$1");
		temp = temp.replaceAll("<pages>(.*)</pages>", "$1");
		temp = temp.replaceAll("<first-page>(.*)</first-page>","pp. $1-");
		temp = temp.replaceAll("<last-page>(.*)</last-page>", "$1");
		temp = temp.replaceAll("<comment>(.*?)</comment>", "$1");
		temp = temp.replaceAll("<other-ref>(.*?)</other-ref>", "$1");
		temp = temp.replaceAll("<textref>(.*?)</textref>", "$1");
		temp = temp.replaceAll("<inter-ref(.*?)>(.*)</inter-ref>", "$2");
		temp = temp.replaceAll(", . ", ". ");
		temp = temp.replaceAll("\\s+", " ");
		temp = temp.trim();
//		System.out.println(temp);
		return temp;

	}
	
}
