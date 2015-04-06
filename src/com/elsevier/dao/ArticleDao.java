package com.elsevier.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.elsevier.model.Article;
import com.elsevier.model.Head;
import com.elsevier.model.Item;


public class ArticleDao {
	private Connection conn;
	private DriverManager dManager;
	
	public ArticleDao(){
	}
	
	public void getData() throws SQLException, ClassNotFoundException {
		String host = "jdbc:oracle:thin:@172.16.10.89:1521:ACM2";
		String user = "xml";
		String password = "Acmw3c";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		conn = DriverManager.getConnection(host, user, password);
		
		System.out.println("Connection made");
		
		conn.close();
			
	}
	
	public void insertArticledep(Article article){
		
	}

	public void insertArticle(Article article) throws SQLException {
		String host = "jdbc:oracle:thin:@172.16.10.89:1521:ACM2";
		String user = "xml";
		String password = "Acmw3c";
		Head head = article.getHead();
		Item item = article.getItem();
		String sql = "Insert into Peter_Genericprdcl_Article (PUBLISHER, TITLE, COPYRIGHT, "+
						"ART_TYPE, DOI, ABSTRACT)"
				+ " VALUES(?,?,?,?,?,?)";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(host, user, password);
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "StAx Test");
			ps.setString(2, head.getTitle());
			ps.setString(3, item.getCopyRight());
			ps.setString(4, "art_type");
			ps.setString(5,  item.getDoi());
			ps.setString(6, head.getTitle());
			ps.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			conn.close();
		}
		
		System.out.println("Connection made");
		
	}
	
	
}
