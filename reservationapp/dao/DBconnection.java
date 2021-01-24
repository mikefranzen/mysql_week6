
	

	package reservationapp.dao;
	
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.SQLException;

	public class DBconnection {
		
		
		private static final String URL = "jdbc:mysql://localhost:3306/employees";
		private static final String USER_NAME = "root";
		private static final String PASSWORD = "xochitl821";

		public static Connection getConnection() throws SQLException {
			return DriverManager.getConnection(URL,USER_NAME, PASSWORD);
			
		}
	}



