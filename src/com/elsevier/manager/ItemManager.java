package com.elsevier.manager;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import com.elsevier.model.Article;
import com.elsevier.model.Item;

public class ItemManager extends AbstractBaseManager{
	
	
	public ItemManager() {
		// TODO Auto-generated constructor stub
	}

	public void process(XMLEventReader reader, Article article, IBaseManager parent) throws XMLStreamException{
		
		Item item = new Item();
		String temp = "";
		XMLEvent event = null;
		while (reader.hasNext()) {
			event = reader.nextEvent();
			
			if(event.isStartElement()){
				stack.add(event.asStartElement());
			}
			
			if(event.isStartElement()){
				if(event.asStartElement().getName().toString().equals("doi")){
					event = reader.nextEvent();
					if(event.isCharacters()){
						temp = event.asCharacters().getData();
						item.setDoi(temp);
						System.out.println("DOI : " + temp);
					}
				}	
			}
			if(event.isStartElement()){
				if(event.asStartElement().getName().toString().equals("pii")){
					event = reader.nextEvent();
					if(event.isCharacters()){
						temp = event.asCharacters().getData();
						item.setPii(temp);
						System.out.println("PII : " + temp);
					}
				}
			}
			if(event.isStartElement()){
				if(event.asStartElement().getName().toString().equals("copyright")){
					event = reader.nextEvent();
					if(event.isCharacters()){
						temp = event.asCharacters().getData();
						item.setCopyRight(temp);
						System.out.println("copyright : " + temp);
					}
				}
			}
			
			if(event.isEndElement()){
				if (stack.size() > 0) {
					this.stack.pop();
				}
				if(event.asEndElement().getName().toString().equals("item-info")){
					article.setItem(item);
					parent.process(reader, article, parent);
				}
			}
		}
		
		
	}
}

