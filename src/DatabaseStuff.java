//import java.sql.*;

// 1
// Make the connection and set up main table: /////////////////////////////////////////////////////////
//Connection con;
//try {
//	Class.forName("com.mysql.jdbc.Driver").newInstance();
//	con = DriverManager.getConnection("jdbc:mysql://199.98.20.119/SetDatabase","TDguest","TDpass");
//	if(!con.isClosed())
//		System.out.println("Successfully connected to MySql server");
//	
//	Statement s = con.createStatement();
//	s.executeUpdate("DROP TABLE IF EXISTS main");
//	s.executeUpdate(
//			"CREATE TABLE main ("
//			+ "id INT UNSIGNED NOT NULL AUTO_INCREMENT,"
//			+ "PRIMARY KEY (id),"
//			+ "name CHAR(40), password CHAR(40), wins integer NOT NULL, losses integer NOT NULL)");
//} catch (Exception e){
//	System.err.println("Exception: " + e.getMessage());
//} 



// 2
// Add initial player entries to the database////////////////////////////////////////////////////////
//int update;
//update = s.executeUpdate(
//		"INSERT INTO main(name, password, wins, losses)"
//		+ " VALUES"
//		+ "(player.name, player.password, 0, 0)");
//System.out.println(update + "rows were inserted");



// 3
// Query the database for entered username/password
//Statement s = con.createStatement();
//s.executeQuery("SELECT id, name, password FROM main");
//ResultSet rs = s.getResultSet();
//update=0;
//while(rs.next())
//{
//		int idVal = rs.getInt("id");
//		String nameVal = rs.getString("name");
//		String passVal = rs.getString("password");
//		if(enteredName == nameVal && enteredPass == passVal){
//			System.out.println("Successfully Validate User" + nameVal);
//			User is authorized-->continue to Join/Leave Game
//			break;
//		}
//		++update;
//}




// 4
// Update Database once game has ended with wins and losses
//Statement s = con.createStatement();
//s.executeQuery("SELECT id, name FROM main");
//ResultSet rs = s.getResultSet();
//update=0;
//while(rs.next())
//{
//		int idVal = rs.getInt("id");
//		String nameVal = rs.getString("name");
//		if(nameVal == player.name){
//			String winsVal = rs.getString("wins");
//			rs.updateInt("wins", player.wins);
//			String lossesVal = rs.getString("losses");
//			rs.updateInt("losses", player.losses);
//			rs.updateRow();
//		}
//		++update;
//}



// 5
// Retrieve players' Wins and Losses
//Statement s = con.createStatement();
//s.executeQuery("SELECT id, name FROM main");
//ResultSet rs = s.getResultSet();
//update=0;
//while(rs.next())
//{
//		int idVal = rs.getInt("id");
//		String nameVal = rs.getString("name");
//		String winsVal = rs.getString("wins");
//		String lossesVal = rs.getString("losses");
//		if(enteredName == nameVal){
//			System.out.println("Player " + nameVal + "has " + winsVal + "wins and " + lossesVal + "losses.");
//			break;
//		}
//		++update;
//}