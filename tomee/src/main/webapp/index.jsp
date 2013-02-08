<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.beans.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        
        <%
        try {
	        Context initCtx = new InitialContext();
	        Context envCtx = (Context) initCtx.lookup("java:comp/env");
	        if (envCtx == null) {
	        	%>envCTX == null<%	
	        	
	        } else {
	        
		        DataSource ds = (DataSource) envCtx.lookup("jdbc/forumtest");
		        
		        if (ds != null) {
		        	
			        Connection con = ds.getConnection();
			        if (con != null) {
			        	PreparedStatement statement = con.prepareStatement("select * from forum_entry");
				        ResultSet rs = statement.getResultSet();
				        
				        if (rs != null) {
				        	
					        while (rs.next()) {
					        	%><%= rs.getString(1) %><br /><%
					        }
				        }
			        	
				        } else {
				        	%>con == null<%	
				        }
			        	
			        
		        } else {
		        	%>ds == null<%	
		        }
	        }
        } catch (Exception e ) {
        	e.printStackTrace();
        }
        %>
        
    </body>
</html>
