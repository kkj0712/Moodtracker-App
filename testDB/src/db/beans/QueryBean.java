package db.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QueryBean {
	Connection conn; //연결 객체
	Statement st; //질의 객체
	ResultSet rs; //결과 객체
	PreparedStatement ps;
	
	public QueryBean() {
		conn=null; 
		st=null;
		rs=null;
	}
	
	public void getConnection() {
		try {
			conn=DBConnection.getConnection();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			st=conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeConnection() {
		if(st!=null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//전체보기 및 검색
	public ArrayList getUserInfo(String id) throws Exception{
		String sql="select u_id, u_name, u_phone, u_grade, write_time from user_info_sample where u_id like '%"+id+"%'";
		rs=st.executeQuery(sql);
		ArrayList res=new ArrayList();
		while(rs.next()) {
			res.add(rs.getString(1));
			res.add(rs.getString(2));
			res.add(rs.getString(3));
			res.add(rs.getString(4));
			res.add(rs.getString(5));
		}
		return res;
	}
	
	//추가
	public int setUserInfo(String id, String name, String phone, String grade)
	{
		int result =0;
		ps = null;
		String sql="insert into user_info_sample (u_id, u_name, u_phone, u_grade, write_time) values (?,?,?,?, sysdate)";
		try {
			ps = conn.prepareStatement(sql);
			ps.clearParameters();
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setString(3, phone);
			ps.setString(4, grade);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(ps!= null) {
					ps.close();
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//수정
	public int updateUserInfo(String id, String name, String phone, String grade) {
		int result =0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		
		sb.append(" UPDATE ");
		sb.append("     USER_INFO_SAMPLE ");
		sb.append(" SET ");
		sb.append("     U_NAME=?, U_PHONE=?, U_GRADE=?, WRITE_TIME=sysdate ");
		sb.append(" WHERE ");
		sb.append("     U_ID=? ");
		System.out.println(sb.toString());
		
		try
		{
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.clearParameters();
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			pstmt.setString(3, grade);
			pstmt.setString(4, id);
			result = pstmt.executeUpdate();
		}
		catch (Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null){
					pstmt.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//삭제
	public int deleteUserInfo(String id) throws Exception
	{
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		
		sb.append(" DELETE ");
		sb.append("     FROM ");
		sb.append("        USER_INFO_SAMPLE ");
		sb.append("     WHERE ");
		sb.append("        U_ID = ? ");
		
		try
		{
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.clearParameters();
			pstmt.setString(1, id);
			
			result = pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				if(pstmt != null)
				{
					pstmt.close();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
		
	}
}