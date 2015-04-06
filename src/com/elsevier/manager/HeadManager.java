package com.elsevier.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.elsevier.model.Article;
import com.elsevier.model.Author;
import com.elsevier.model.Head;

public class HeadManager extends AbstractBaseManager {
	
	private Stack<StartElement> stack = new Stack<StartElement>();
	private List<Author> authors	  = new ArrayList<Author>();
	private Map<String, String>	 affils		  = new HashMap<String,String>();
	private List<String> keywords           = new ArrayList<String>();

	public HeadManager(){
		
	}
	
	@SuppressWarnings("unchecked")
	public void process(XMLEventReader reader, Article article, IBaseManager parent) throws XMLStreamException{
		Head head 	= new Head();
		String temp = null;
		System.out.println("Inside headProcess");
		XMLEvent event = null;
		while(reader.hasNext()){
			event = reader.nextEvent();
			if(event.isStartElement()){
				stack.add(event.asStartElement());
			}
			
			if(event.isStartElement()){
				if(event.asStartElement().getName().toString().equals("title")){
					event = reader.nextEvent();
					if(event.isCharacters()){
						temp = event.asCharacters().getData();
						head.setTitle(temp);
					}
				}				
			}
			
			if(event.isStartElement()){
				if(event.asStartElement().getName().toString().equals("simple-para") && 
					stack.get(stack.size()-2).getName().toString().equals("abstract-sec") ){
//					System.out.println("Inside abstract sec");
					event = reader.nextEvent();
					if(event.isCharacters()){
						temp = event.asCharacters().getData().toString();
						// Perform checks on abstract & title
						head.setAbs(temp);
//						System.out.println("abs : " + temp);
//						System.out.println("abs-sec : " + event.asCharacters().getData().toString());
					}
				}				
			}
			
			if(event.isStartElement()){
				if(event.asStartElement().getName().toString().equals("author")) {
					Author author = new Author();
					while(reader.hasNext()) {
						event = reader.nextEvent();
						
						if(event.isEndElement()){
							if( event.asEndElement().getName().toString().equals("author")){
								authors.add(author);
								break;
							}
						}
						if(event.isStartElement()){
							if(event.asStartElement().getName().toString().equals("given-name")) {
								event = reader.nextEvent();
								if(event.isCharacters()){
									temp = 	event.asCharacters().getData();
									author.setFirstName(temp);
								}
							}
						}
						if(event.isStartElement()){
							if(event.asStartElement().getName().toString().equals("surname")) {
								event = reader.nextEvent();
								if(event.isCharacters()){
									temp = 	event.asCharacters().getData();
									author.setLastName(temp);
//									System.out.println("last : " + temp);
								}
							}
						}
						if(event.isStartElement()){
							if(event.asStartElement().getName().toString().equals("cross-ref")){
								String crossId = null;
								for(Iterator<Attribute> attrib = event.asStartElement().getAttributes(); attrib.hasNext(); ){
									Attribute element = attrib.next();
									if(element.getName().toString().equals("refid")&& !element.getValue().contains("cor")){
										crossId = element.getValue();
										author.setId(crossId);
									}
								}
							}
						}
					}
					
				}
			}
			
			if(event.isStartElement()){
				if(event.asStartElement().getName().toString().equals("affiliation") && 
						stack.get(stack.size()-2).getName().toString().equals("author-group")) {
						processAffil(event, reader);
				}
			}
			
			if(event.isStartElement()){
				if(event.asStartElement().getName().toString().equals("text") &&
						stack.get(stack.size()-2).getName().toString().equals("keyword")) {
					String keyword = null;
//					System.out.println()
					keyword = reader.nextEvent().asCharacters().getData().toString();
					keywords.add(keyword);
				}
			}
			
			if(event.isEndElement()){
				if (stack.size() > 0) {
					this.stack.pop();
				}
				if(event.asEndElement().getName().toString().equals("head")){
					assignAffiliations();
					head.setAuthors(authors);
					head.setKeywords(keywords);
					article.setHead(head);
					parent.process(reader, article, parent);
				}
			}
			
		}
		
	}
	
	private void assignAffiliations(){
		String key = null;
		String affText = null;
		if(affils.size() == 1){
			affText = affils.entrySet().iterator().next().getValue();
			for(Author author: authors){
				author.setAffiliation(affText);
				
			}
		}
		else if(affils.size() > 1){
			System.out.println("Inside affils.size > 0");
			for(Author author : authors){
				for(String value: affils.keySet()){
					key = value.toString();
					if(author.getId().equals(key)){
						author.setAffiliation(affils.get(value).toString());
					}
				}
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void processAffil(XMLEvent event, XMLEventReader reader) throws XMLStreamException{
//		System.out.println("Inside processAffil");
		String affId   = null;
		String affText = null;
		
		for(Iterator<Attribute> temp = event.asStartElement().getAttributes(); temp.hasNext(); ){
			Attribute element = temp.next();
			if(element.getName().toString().equals("id")){
				affId = element.getValue();
//				System.out.println("id : " + element.getValue());
			}
		}
		

		while(reader.hasNext()){
			event = reader.nextEvent();
			if(event.isEndElement()){
				if(event.asEndElement().getName().toString().equals("affiliation")){
//					System.out.println("Break out of affiliation");
					affils.put(affId, affText);
					break;
				}
			}
			if(event.isStartElement()){
				if(event.asStartElement().getName().toString().equals("textfn")){
					affText = reader.nextEvent().asCharacters().getData();
//					System.out.println(reader.nextEvent().asCharacters().getData());
				}
			}
		}
		
	}

/*	@Override
	public void process(XMLEventReader reader, Article article, IBaseManager parent) throws XMLStreamException {

	}*/
	
}
