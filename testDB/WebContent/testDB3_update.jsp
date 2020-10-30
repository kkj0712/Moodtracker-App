<%@ page import="db.beans.*,java.sql.*,java.util.*,java.io.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="QueryBean" scope="page" class="db.beans.QueryBean"/>
<jsp:setProperty property="*" name="QueryBean"/>

<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
	request.setCharacterEncoding("UTF-8");
	
	String imagename = request.getParameter("imagename") == null? "" : request.getParameter("imagename").trim();
	String writedate = request.getParameter("writedate") == null? "" : request.getParameter("writedate").trim();
	String writetime = request.getParameter("writetime") == null? "" : request.getParameter("writetime").trim();
	String memo = request.getParameter("memo") == null? "" : request.getParameter("memo").trim();
	
	int num=Integer.parseInt(request.getParameter("num"))==0? 0 : Integer.parseInt(request.getParameter("num"));
	
	System.out.println(imagename+","+writedate+","+writetime+","+memo);
	
	QueryBean.getConnection();
	int res = 0;
	try{
		res=QueryBean.updateUserInfo(imagename, writedate, writetime, memo, num);
	}catch(Exception e){
		QueryBean.closeConnection();
	}
	out.print("[{");
	out.print("\"RESULT_OK\":\""+res+"\" ");
	out.print("}]");
	System.out.println("res: " +res);
%>