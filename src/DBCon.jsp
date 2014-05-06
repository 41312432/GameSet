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
    	String username = "NAME"
    	String password = "PASS"
    	
		try {
  			Class.forName("com.mysql.jdbc.Driver").newInstance();
  			con = DriverManager.getConnection(conURL, username, password);
  			if(!connection.isClosed())
                 out.println("Successfully Connected");
                 
            Statement s = con.createStatement ();
            int count;
            
            //Create a table and insert data
            s.exectueUpdate("DROP TABLE IF EXISTS player");
            s.executeUpdate(
            	"CREATE TABLE player ("
            	+ "id INT UNSIGNED NOT NULL AUTO_INCREMENT,"
            	+ "PRIMARY KEY (id),"
            	+ "name CHAR(40), pass CHAR(40))");
            
            //insert and query also given. Would this be put into the other files?                
                 
            connection.close();
		}
		catch(Exception e){
  			out.write(e);
		}
	%>
	
	</BODY>
</HTML>