package db.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QueryBean {
	Connection conn; 
	Statement st; 
	ResultSet rs; 
	PreparedStatement ps;
	
	public QueryBean() {
		conn=null; st=null; rs=null;
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
		try {
			if(st!=null) st.close();
			if(conn!=null) conn.close();
			if(rs!=null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	//전체보기
	public ArrayList getUserInfo(String memo) throws Exception{
		String sql="select imagename, writedate, writetime, memo, num from moodtracker where memo like '%"+memo+"%' order by writedate desc";
		rs=st.executeQuery(sql);
		ArrayList res=new ArrayList();
		while(rs.next()) {
			res.add(rs.getString(1));
			res.add(rs.getString(2));
			res.add(rs.getString(3));
			res.add(rs.getString(4));
			res.add(rs.getInt(5));
		}
		return res;
	}
	
	//추가하기
	public int setUserInfo(String imagename, String writedate, String writetime, String memo){
		int result =0;
		ps = null;
		String sql="insert into moodtracker (imagename, writedate, writetime, memo, num) values (?,?,?,?,moodtracker_seq.nextval)";
		try {
			ps = conn.prepareStatement(sql);
			ps.clearParameters();
			ps.setString(1, imagename);
			ps.setString(2, writedate);
			ps.setString(3, writetime);
			ps.setString(4, memo);
			result = ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps!= null) ps.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//수정하기
	public int updateUserInfo(String imagename, String writedate, String writetime, String memo, int num) {
		int result =0;
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		sb.append("update moodtracker set imagename=?, writedate=?, writetime=?, memo=? where num=?");
		
		try{
			ps = conn.prepareStatement(sb.toString());
			ps.clearParameters();
			ps.setString(1, imagename);
			ps.setString(2, writedate);
			ps.setString(3, writetime);
			ps.setString(4, memo);
			ps.setInt(5, num);
			result = ps.executeUpdate();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(ps != null) ps.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//삭제하기
	public int deleteUserInfo(int num) throws Exception{
		int result = 0;
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from moodtracker where num=?");
		
		try{
			ps = conn.prepareStatement(sb.toString());
			ps.clearParameters();
			ps.setInt(1, num);
			result = ps.executeUpdate();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(ps != null) ps.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}