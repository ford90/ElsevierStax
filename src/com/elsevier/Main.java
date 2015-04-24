package com.elsevier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;








import com.elsevier.dao.ArticleDao;
import com.elsevier.dao.ElsevierDs;
import com.elsevier.manager.MainManager;
import com.elsevier.model.Article;


public class Main {

	public static void main(String[] args) throws FileNotFoundException, XMLStreamException, SQLException, ClassNotFoundException {
		String fileName = "S0895717712002804.xml";
		String path = "V:\\peter\\elsevier";
		try{
			
			File folder = new File(path);
		
			for(String file : folder.list()){
				XMLInputFactory inputFactory 	= XMLInputFactory.newInstance();

				InputStream		inputStream	 	= new FileInputStream(path + "\\" + file);
			
				XMLEventReader	reader			= inputFactory.createXMLEventReader(inputStream);
			
				MainManager manager = new MainManager(reader);
					
				Article article = manager.start();
				System.out.println("Article");	
			
				ArticleDao dao = new ArticleDao();
			
//				dao.insertArticle2(article, fileName);
				dao.insertArticle3(article, fileName);
				
			}
		} catch(Exception e){e.printStackTrace();}
	}
}
