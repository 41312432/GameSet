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