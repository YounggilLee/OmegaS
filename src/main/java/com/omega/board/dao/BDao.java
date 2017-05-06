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

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.omega.board.dto.BDto;
import com.omega.board.util.Constant;

public class BDao {

	//DataSource dataSource;
	
	JdbcTemplate template = null;
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String uid = "scott";
	String upw = "1234";

	public BDao() {

		/*
		 * try { Context context = new InitialContext(); dataSource =
		 * (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
		 * 
		 * } catch (NamingException e) {
		 * 
		 * e.printStackTrace(); }
		 */
		
		template = Constant.template;

	}

	public ArrayList<BDto> list() {
		ArrayList<BDto> dtos = null;
		
		String query = "SELECT bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent "
				+ "FROM mvc_board order by bGroup DESC, bStep ASC";		
		dtos = (ArrayList<BDto>)template.query(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		
		return dtos;
		
		/*ArrayList<BDto> dtos = new ArrayList<BDto>();

		try {
			// connection = dataSource.getConnection();
			connection = DriverManager.getConnection(url, uid, upw);
			String query = "SELECT bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent "
					+ "FROM mvc_board order by bGroup DESC, bStep ASC";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
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
		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {

				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e2) {

				e2.printStackTrace();
			}
		}

		return dtos;*/

	}

	public void write(final String bName, final String bTitle, final String bContent) {

		
		
		template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				// TODO Auto-generated method stub
				String query = "insert into mvc_board(bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent)"
						+ "values(mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0)";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, bName);
				ps.setString(2, bTitle);
				ps.setString(3, bContent);
				
				
				return null;
			}
		});
/*		try {
			// connection = dataSource.getConnection();
			connection = DriverManager.getConnection(url, uid, upw);
			String query = "insert into mvc_board(bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent)"
					+ "values(mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0)";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);

			int result = preparedStatement.executeUpdate();
			System.out.println(result);

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			try {

				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();

			} catch (Exception e2) {

				e2.printStackTrace();

			}
		}*/

	}

	public BDto contentView(String strID) {

		upHit(strID);
		
		String query = "SELECT * FROM mvc_board WHERE bId = ?";
		
		BDto dto = template.queryForObject(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		
		return dto;
		
		
		/*BDto dto = null;

		try {

			connection = DriverManager.getConnection(url, uid, upw);
			String query = "SELECT * FROM mvc_board WHERE bId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(strID));
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
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

		} finally {

			try {

				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e2) {

				e2.printStackTrace();
			}

		}

		return dto;*/

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

		/*try {

			connection = DriverManager.getConnection(url, uid, upw);
			String query = "UPDATE mvc_board SET bName = ?, bTitle= ?, bContent = ? WHERE bId = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, bName);
			ps.setString(2, bTitle);
			ps.setString(3, bContent);
			ps.setInt(4, Integer.parseInt(bId));

			int result = preparedStatement.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {

				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e2) {

				e2.printStackTrace();
			}

		}
*/
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
		
		
		/*try {

			connection = DriverManager.getConnection(url, uid, upw);
			String query = "DELETE FROM mvc_board WHERE bId = ?";
			PreparedStatement ps = connection.prepareStatement(query);			
			ps.setInt(1, Integer.parseInt(strID));

			int result = preparedStatement.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {

				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e2) {

				e2.printStackTrace();
			}

		}*/
		
	}

	
	
	public BDto reply_view(String strID) {
		// TODO Auto-generated method stub
		
		String query = "SELECT * FROM mvc_board WHERE bId = ?";
		
		BDto dto =template.queryForObject(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		
		return dto;
		
		/*BDto dto = null;
		
		try {

			connection = DriverManager.getConnection(url, uid, upw);
			String query = "SELECT * FROM mvc_board WHERE bId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(strID));
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
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

		} finally {

			try {

				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e2) {

				e2.printStackTrace();
			}

		}
		
		return dto;*/

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
		
		//replyShape(bGroup, bStep);
		
		/*try {

			connection = DriverManager.getConnection(url, uid, upw);
			String query = "INSERT INTO mvc_board(bId, bName, bTitle, bContent, bGroup, bStep, bIndent) VALUES(mvc_board_seq.nextval,?,?,?,?,?,?)";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, bName);
			ps.setString(2, bTitle);
			ps.setString(3, bContent);
			ps.setInt(4, Integer.parseInt(bGroup));
			ps.setInt(5, Integer.parseInt(bStep) + 1);
			ps.setInt(6, Integer.parseInt(bIndent) + 1);

			int result = preparedStatement.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {

				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e2) {

				e2.printStackTrace();
			}

		}
*/
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
		
	/*	try {

			connection = DriverManager.getConnection(url, uid, upw);
			String query = "UPDATE mvc_board SET bStep = bStep + 1 WHERE bGroup = ? and bStep > ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(strGroup));
			ps.setInt(2, Integer.parseInt(strStep));

			int result = preparedStatement.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {

				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e2) {

				e2.printStackTrace();
			}

		}*/
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
		

		/*try {

			connection = DriverManager.getConnection(url, uid, upw);
			String query = "UPDATE mvc_board SET bHit = bHit + 1 WHERE bId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bId);

			int result = preparedStatement.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {

				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e2) {

				e2.printStackTrace();
			}

		}
*/
	}

}
