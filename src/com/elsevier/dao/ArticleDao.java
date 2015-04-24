package com.elsevier.dao;


import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import com.elsevier.model.Article;
import com.elsevier.model.Author;

public class ArticleDao {
	private Connection conn;
	private DriverManager dManager;
	private ElsevierDataSource dataSource;

	// Change getInstance to static
	public ArticleDao(){
		dataSource = dataSource.getInstance();
		conn = dataSource.getConnection();
	}
	
	
	public void insertArticle2(Article article, String fileName){
		String sql = "{call elsevier_pkg_peter.stax(?,?,?,?,?,?,?,?,?,?,?,?)}";
		
		List<Object> firstNames  = new ArrayList<Object>();
		List<Object> lastNames   = new ArrayList<Object>();
		List<Object> middleNames = new ArrayList<Object>();
		List<Object> affils      = new ArrayList<Object>();
		List<Object> keywords    = new ArrayList<Object>();
		
		
		
		// Get first names, last names, affiliations
		for(Author author : article.getHead().getAuthors()){
//			firstName = author.getFirstName();
//			lastName  = author.getLastName();
//			affil     = author.getAffiliation();
//			affil     = "affil";
			firstNames.add(author.getFirstName());
			lastNames.add(author.getLastName());
			affils.add(author.getAffiliation());
		}
		
		for(String keyword : article.getHead().getKeywords()){
			keywords.add(keyword);
		}
		
		Object refs[] = article.getTail().getReferences().toArray();
		try{
//			Clob absClob = this.conn.createClob();
//			absClob.setString(0, article.getHead().getAbs());
			
			ArrayDescriptor vTab  = ArrayDescriptor.createDescriptor("ELSEVIER_VTAB_TYPE", dataSource.getConnection());
			ArrayDescriptor lTab  = ArrayDescriptor.createDescriptor("ELSEVIER_LTAB_TYPE", dataSource.getConnection());
			ArrayDescriptor elTab = ArrayDescriptor.createDescriptor("ELSEVIER_ELTAB_TYPE", dataSource.getConnection());
        
			ARRAY v_ref      = new ARRAY(lTab, conn, refs);
			ARRAY v_refid    = new ARRAY(vTab, conn, refs);
			ARRAY v_raw_ref  = new ARRAY(elTab, conn, refs);
			ARRAY kw         = new ARRAY(vTab, conn, keywords.toArray());
			ARRAY first_name = new ARRAY(vTab, conn, firstNames.toArray());
			ARRAY last_name  = new ARRAY(vTab, conn, lastNames.toArray());
			ARRAY aff        = new ARRAY(vTab, conn, affils.toArray());
			
			CallableStatement stmt = conn.prepareCall(sql);

			stmt.setArray(1, kw);
			stmt.setArray(2, first_name);
			stmt.setArray(3, last_name);
			stmt.setArray(4, aff);
			stmt.setArray(5, v_ref);
			stmt.setArray(6,  v_refid);
			stmt.setArray(7, v_raw_ref);
			stmt.setString(8, article.getItem().getDoi());
			stmt.setString(9, article.getItem().getPii());
			stmt.setString(10,article.getItem().getCopyRight());
			stmt.setString(11, fileName);
			stmt.setString(12, article.getHead().getAbs());
			
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Connection close");
	}
	
	
	public void insertArticle3(Article article, String fileName){
		String sql = "{call elsevier_pkg_peter.stax2(?)}";
		

		try{		

			
			CallableStatement stmt = conn.prepareCall(sql);
			
			stmt.setObject(1, article);
			
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Connection close");
	}
	
}
