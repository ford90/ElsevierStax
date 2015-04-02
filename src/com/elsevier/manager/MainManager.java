package com.elsevier.manager;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import com.elsevier.model.Article;

public class MainManager implements BaseManager {
	
	private XMLEventReader reader;
	private Article		   article;
	private BaseManager	   last;
	
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
	
	public void process(XMLEventReader reader, Article article, MainManager parent) throws XMLStreamException{
		System.out.println("Inside MainManager process");
		XMLEvent event = null;
			while(reader.hasNext()){
			
				event = reader.nextEvent();
			
				if(event.isStartElement()){
					if(event.asStartElement().getName().toString().equals("item-info") ){
						ItemManager itemManager = new ItemManager();
						itemManager.process(reader,article, this);
						itemManager = null;
					}
				}

				if(event.isStartElement()){
					if(event.asStartElement().getName().toString().equals("head")){
						HeadManager headManager = new HeadManager();
						headManager.process(reader, article, parent);
						headManager = null;
					}
				}
				if(event.isStartElement()){
					if(event.asStartElement().getName().toString().equals("tail")){
						TailManager tailManager = new TailManager();
						tailManager.process(reader, article, this);
						tailManager = null;
						
					}
				}
			}
	}

}
