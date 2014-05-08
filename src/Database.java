import java.sql.*;

public class Database{
	
	// Make the connection
	public static Connection getConnection(){
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://199.98.20.119:3304/SetDatabase","TDGuest","TDPass");
			if(!con.isClosed())
				System.out.println("Successfully connected to MySql server");
		} catch (Exception e){
			System.err.println("Exception: " + e.getMessage());
		}
		return con;
	}
	
	// Setup main Table
	public static void setupTable(Connection con){
		try {
		Statement s = con.createStatement();
		s.executeUpdate("DROP TABLE IF EXISTS main");
		s.executeUpdate(
				"CREATE TABLE main ("
						+ "id INT UNSIGNED NOT NULL AUTO_INCREMENT,"
						+ "PRIMARY KEY (id),"
						+ "name CHAR(40), password CHAR(40), wins integer NOT NULL, losses integer NOT NULL)");
		} catch (Exception e){
			System.err.println("Exception: " + e.getMessage());
		}
	}
	
	// Add player to the database
	public static void addPlayer(Connection con, Player player){
		int update;
		String sqlName = player.getPlayerName();
		//String sqlPass = player.getPlayerPass();
		try{
			String updateString = "INSERT INTO main(name, password, wins, losses)" + " VALUES" + "(?, ?, 0, 0)";
			PreparedStatement s = con.prepareStatement(updateString);
			s.setString(1,sqlName);
			//s.setString(2, sqlPass);
			s.execute();
			System.out.println(s + "rows were inserted");
		} catch (Exception e){
			System.err.println("Exception: " + e.getMessage());
		}
	}
	
	// Query the database for an entered username/password
	public boolean queryDatabase(Connection con, Player player){
		String enteredName = player.getPlayerName();
		//String enteredPass = player.getPlayerPass();
		try{
		Statement s = con.createStatement();
		s.executeQuery("SELECT id, name, password FROM main");
		ResultSet rs = s.getResultSet();
		int update=0;
		while(rs.next())
		{
				int idVal = rs.getInt("id");
				String nameVal = rs.getString("name");
				String passVal = rs.getString("password");
				//if(enteredName == nameVal && enteredPass == passVal){
					//System.out.println("Successfully Validate User" + nameVal);
				//	break;
				//	return true;
				//}
				++update;
		}
		} catch (Exception e){
			System.err.println("Exception: " + e.getMessage());
		}
		return false;
	}
}


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