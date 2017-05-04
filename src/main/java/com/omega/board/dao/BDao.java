package com.omega.board.dao;

import java.sql.Connection;
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
	
	public BDao(){
		
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
			
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
	}
	
	public ArrayList<BDto> list(){
		
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try{
			connection = dataSource.getConnection();
			String query  = "select bId, bName, bTitle, bContent, bDate, bHit, bGrouop, bStep, bIndent"
					+ "from mvc_board order by bGroup desc, bSteop asc";
			preparedStatement = connection.prepareStatement(query);
			
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
}
