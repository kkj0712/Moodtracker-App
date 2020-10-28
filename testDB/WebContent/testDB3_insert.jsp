<%@ page import="db.beans.*,java.sql.*,java.util.*,java.io.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="QueryBean" scope="page" class="db.beans.QueryBean"/>
<jsp:setProperty property="*" name="QueryBean"/>

<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
	
	request.setCharacterEncoding("UTF-8");
	
	String id = request.getParameter("id") == null? "000" : request.getParameter("id").trim();
	String name = request.getParameter("name") == null? "" : request.getParameter("name").trim();
	String phone = request.getParameter("phone") == null? "" : request.getParameter("name").trim();
	String grade = request.getParameter("grade") == null? "" : request.getParameter("grade").trim();
	
	System.out.println("id: "+id);
	System.out.println("name: "+name);
	System.out.println("phone:"+phone);
	System.out.println("grade:"+grade);
	
	QueryBean.getConnection();
	//ArrayList resArr = new ArrayList();
	int res = 0;
	
	try{
		res=QueryBean.setUserInfo(id, name, phone, grade);
	}
	catch(Exception e)
	{
		QueryBean.closeConnection();
	}
	out.print("[");
	out.print("{");
	out.print("\"RESULT_OK\":\""+res+"\" ");
	out.print("}");
	out.print("]");
	
	System.out.println("res: " +res);
	
%>