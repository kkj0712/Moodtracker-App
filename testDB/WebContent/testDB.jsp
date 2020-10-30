<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="db.beans.*, java.sql.*, java.util.*, java.io.*" %>
<jsp:useBean id="QueryBean" scope="page" class="db.beans.QueryBean" />
<jsp:setProperty name="QueryBean" property="*"/>

<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
	request.setCharacterEncoding("UTF-8");
	
	QueryBean.getConnection();
	ArrayList resArr=new ArrayList();
	
	String memo=request.getParameter("memo")==null? "":request.getParameter("memo").trim();
	
	try{
		resArr=QueryBean.getUserInfo(memo);
	}catch(SQLException e){
		out.print(e.toString());
	}finally{
		QueryBean.closeConnection();
	}

	out.println("{");
	out.println("\"datas\":[");
	
	if(resArr.size()==0){
		out.println("]");
		out.println("}");
	}else{
		out.print("{");
		out.print("\"imagename\": \""     +(String)resArr.get(0) + "\", ");
		out.print("\"writedate\": \""   +(String)resArr.get(1) + "\", ");
		out.print("\"writetime\": \""  +(String)resArr.get(2) + "\", ");
		out.print("\"memo\": \""  +(String)resArr.get(3) + "\", ");
		out.print("\"num\": \""  +resArr.get(4) + "\" ");
		out.print("} ");
		
		for(int i=5; i<resArr.size(); i+=5){
			out.print(",");
			out.print("{");
			out.print("     \"imagename\": \""     +(String)resArr.get(i)+ "\", ");
			out.print("     \"writedate\": \""   +(String)resArr.get(i+1)+ "\", ");
			out.print("     \"writetime\": \""  +(String)resArr.get(i+2)+ "\", ");
			out.print("     \"memo\": \""  +(String)resArr.get(i+3)+ "\", ");
			out.print("     \"num\": \"" +resArr.get(i+4)+ "\" ");
			
			out.print("} ");
		}out.println("]");
		 out.println("}");
	}
%>