package com.omega.board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.omega.board.dto.BDto;

public class BDao {
	
	DataSource dataSource;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String uid = "scott";
	String upw = "1234";
	
	
	public BDao(){
		
	/*	try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
			
		} catch (NamingException e) {
			
			e.printStackTrace();
		}*/	
		
	}
	
	public ArrayList<BDto> list(){
		
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		
		
		try{
			//connection = dataSource.getConnection();
			connection = DriverManager.getConnection(url,uid,upw);
			String query  = "SELECT bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent "
					+ "FROM mvc_board order by bGroup DESC, bStep ASC";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				dtos.add(dto);
						
			}
		}catch(Exception e){
			
			e.printStackTrace();
			
		}finally{
			
				try {
					
					if(resultSet != null) resultSet.close();
					if(preparedStatement != null) preparedStatement.close();
					if(connection != null) connection.close();
					
				} catch (SQLException e2) {
				
					e2.printStackTrace();
				}
		}
		
		
		return dtos;
		
	}
	
	public void write(String bName, String bTitle, String bContent){
		
		
		
		try {
			//connection = dataSource.getConnection();
			connection = DriverManager.getConnection(url,uid,upw);
			String query  = "insert into mvc_board(bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent)"
					+ "values(mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			
			int result = preparedStatement.executeUpdate();
			System.out.println(result);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally{
			
			try{	
				
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
				
				}catch(Exception e2){
				
					e2.printStackTrace();
				
				}
			}
		
	}
	
	public BDto contentView(String strID){
		
		upHit(strID);		
		BDto dto = null;		
		
		try {
			
			connection = DriverManager.getConnection(url,uid,upw);
			String query = "SELECT * FROM mvc_board WHERE bId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(strID));			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()){
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				
				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}finally{
			
			
				try {
					
					if(resultSet != null) resultSet.close();
					if(preparedStatement != null) preparedStatement.close();
					if(connection != null) connection.close();
					
				} catch (SQLException e2) {

					e2.printStackTrace();
				}			
			
		}		
		
		
		return dto;
		
	}
	
	private void upHit(String bId){
		
		try {
			
			connection = DriverManager.getConnection(url,uid,upw);
			String query = "UPDATE mvc_board SET bHit = bHit + 1 WHERE bId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bId);
			
			int result = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}finally{
			
			
				try {
					
					if(preparedStatement != null) preparedStatement.close();
					if(connection != null) connection.close();
					
				} catch (SQLException e2) {

					e2.printStackTrace();
				}			
			
		}		
		
	}
	
	
}
