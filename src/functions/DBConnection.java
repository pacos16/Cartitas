package functions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private static Connection connection;
	

	public static Connection getInstance() {

		if(connection==null) {
			String urlConnection = "jdbc:mysql://localhost:3306/juegoapi?serverTimezone=UTC"; 																		 
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(urlConnection, "root", "");
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return connection;
	}

}