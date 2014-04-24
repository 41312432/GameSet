// This can be used as a Java Server Page Implementation of connecting to a MySQL through a JDBC Driver
// Will probably not use a JSP for connection...most likely use just a JAVA implementation in the Game Lobby

<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>

<HTML>
	<HEAD>
	</HEAD>
	<BODY>
	
	<%
    	Connection con = null;
    	String conURL = "jdbc:mysql://host/database";
    	String username = "NAMEEEE"
    	String password = "PASSSSS"
    	
		try {
  			Class.forName("com.mysql.jdbc.Driver").newInstance();
  			con = DriverManager.getConnection(conURL, username, password);
  			if(!connection.isClosed())
                 out.println("Successfully Connected");
            connection.close();
		}
		catch(Exception e){
  			out.write(e);
		}
	%>
	
	</BODY>
</HTML>