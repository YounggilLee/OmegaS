package com.omega.board.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.SQLException;

import java.util.ArrayList;



import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.omega.board.dto.BDto;
import com.omega.board.util.Constant;

public class BDao {

	
	
	JdbcTemplate template = null;
	


	public BDao() {

	
		template = Constant.template;

	}

	public ArrayList<BDto> list() {
		ArrayList<BDto> dtos = null;
		
		String query = "SELECT bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent "
				+ "FROM mvc_board order by bGroup DESC, bStep ASC";		
		dtos = (ArrayList<BDto>)template.query(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		
		return dtos;
	

	}

	public void write(final String bName, final String bTitle, final String bContent) {

		
		
		template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				// TODO Auto-generated method stub
				String query = "insert into mvc_board(bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent)"
						+ "values(mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0)";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, bName);
				ps.setString(2, bTitle);
				ps.setString(3, bContent);
				
				
				return ps;
			}
		});


	}

	public BDto contentView(String strID) {

		upHit(strID);
		
		String query = "SELECT * FROM mvc_board WHERE bId = " + strID;
		
		BDto dto = template.queryForObject(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		
		return dto;
		
	

	}

	public void modify(final String bId, final String bName, final String bTitle,final String bContent) {
		
		
		String query = "UPDATE mvc_board SET bName = ?, bTitle= ?, bContent = ? WHERE bId = ?";
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				
				ps.setString(1, bName);
				ps.setString(2, bTitle);
				ps.setString(3, bContent);
				ps.setInt(4, Integer.parseInt(bId));

			}
		});

	
	}
	
	
	public void delete(final String strID){
		
		String query = "DELETE FROM mvc_board WHERE bId = ?";
		
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				
				ps.setInt(1, Integer.parseInt(strID));
				
			}
		});
		

		
	}

	
	
	public BDto reply_view(String strID) {
		// TODO Auto-generated method stub
		
		String query = "SELECT * FROM mvc_board WHERE bId = " + strID;
		
		BDto dto =template.queryForObject(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		
		return dto;
		
		

	}
	
	
	public void reply(final String bId, final String bName, final String bTitle, final String bContent, final String bGroup, final String bStep, final String bIndent){
		
		
		String query = "INSERT INTO mvc_board(bId, bName, bTitle, bContent, bGroup, bStep, bIndent) VALUES(mvc_board_seq.nextval,?,?,?,?,?,?)";
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setString(1, bName);
				ps.setString(2, bTitle);
				ps.setString(3, bContent);
				ps.setInt(4, Integer.parseInt(bGroup));
				ps.setInt(5, Integer.parseInt(bStep) + 1);
				ps.setInt(6, Integer.parseInt(bIndent) + 1);
				
			}
		});
		

	}
	

	private void replyShape(final String strGroup, final String strStep){
		
		String query = "UPDATE mvc_board SET bStep = bStep + 1 WHERE bGroup = ? and bStep > ?";
		
template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
			
				ps.setInt(1, Integer.parseInt(strGroup));
				ps.setInt(2, Integer.parseInt(strStep));
			}
		});
		

	}
	
	private void upHit(final String bId) {
		
		String query = "UPDATE mvc_board SET bHit = bHit + 1 WHERE bId = ?";
		
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, Integer.parseInt(bId));
			}
		});
		

	}

}
