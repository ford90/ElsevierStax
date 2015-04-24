package com.elsevier.manager;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import com.elsevier.model.Article;

public class MainManager extends AbstractBaseManager {
	
	private XMLEventReader reader;
	private Article		   article;
	private IBaseManager   manager;
	
	public MainManager(XMLEventReader reader){
		this.reader  = reader;
		this.article = new Article();
	}
	
	public Article start(){
		
		try {
			process(reader,article, this);
			
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.article;
	}
	
	@Override
	public void process(XMLEventReader reader, Article article, IBaseManager parent) throws XMLStreamException{
		System.out.println("Inside MainManager process");
		XMLEvent event = null;
		while(reader.hasNext()){
			event = reader.nextEvent();
			if(event.isStartElement()){
				if(event.asStartElement().getName().toString().equals("item-info") ){
//					ItemManager itemManager = new ItemManager();
//					itemManager.process(reader,article, this);
//					itemManager = null;
					manager = new ItemManager();
					manager.process(reader, article, this);
				}
			}

			if(event.isStartElement()){
				if(event.asStartElement().getName().toString().equals("head")){
					manager = new HeadManager();
					manager.process(reader, article, this);
				}
			}
			if(event.isStartElement()){
				if(event.asStartElement().getName().toString().equals("tail")){
					manager = new TailManager();
					manager.process(reader, article, this);
				}
			}
		}
	}	
}
