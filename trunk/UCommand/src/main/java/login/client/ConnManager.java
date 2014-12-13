package login.client;

//
//import java.sql.Connection;
//import java.sql.DriverManager;
//
//public abstract class ConnManager {
//
//	private static Connection conn = null;
//
//	public static Connection getConnection() throws Exception {
//
//		if (conn == null || conn.isClosed()) {
//
//			String connString = "jdbc:mysql://localhost/practica";
//			String driver = "com.mysql.jdbc.Driver";
//			String user = "root";
//			String pass = "root";
//
//			Class.forName(driver).newInstance();
//			conn = DriverManager.getConnection(connString, user, pass);
//		}
//		return conn;
//	}
//}
/* *************************************************************************************** */

import java.sql.*;

public class ConnManager {
	Connection connection;
	String driverName = "com.mysql.jdbc.Driver";
	String serverName = "localhost";
	String userName = "root";
	String password = "root";
	String url = "jdbc:mysql://localhost/practica";
	/*
	 * 
	 * Creates a connection and Stores it in Connection object
	 */
	public boolean doConnection()
	{
		try {
			Class.forName(driverName);
			connection = DriverManager.getConnection(url, userName, password);
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("aaaa" + e.getMessage());
			return false;
		}
		catch (SQLException e)
		{
			System.out.println("abc " + e.getMessage());
			return false;
		}
		return true;
	}
	/*
	 * 
	 * Closes The current Connection to the Databases
	 */
	public boolean closeConnection()
	{
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public Connection getConnect()
	{
		return connection;
	}
}