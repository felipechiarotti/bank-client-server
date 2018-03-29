package clientserver.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	private static Connection conn = null;
	
	public static Connection getConnection() throws SQLException{
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(conn == null) {
			String url = "jdbc:postgresql://localhost:5432/bank";
			String user = "chiarotti";
			String password = "21361qpo";
			conn = DriverManager.getConnection(url, user, password);			
		}
		return conn;
	}
}