<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.jiulou.tool.GenTool" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>result</title>
</head>
<body>
<%
	String strDto = "";
	String strSqlMapper = "";
	String sqlCreate = request.getParameter("sqlCreate");
	if (null != sqlCreate && !sqlCreate.isEmpty()){
		GenTool gt = new GenTool();
		gt.parseMetadata(sqlCreate);
		strDto = gt.genDto();
		strSqlMapper = gt.genSqlMapper();
	}else{
		strDto = "Error para.";
	}
%>
<pre>
<c:out value="<%=strDto %>" escapeXml="true"></c:out>
</pre>
<pre>
<c:out value="<%=strSqlMapper %>" escapeXml="true"></c:out>
</pre>
</body>
</html>