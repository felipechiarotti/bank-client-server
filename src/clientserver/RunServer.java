package clientserver;
import java.io.IOException;
import java.sql.SQLException;

import clientserver.server.Server;

public class RunServer {
	public static void main(String args[]) {
		Server server = new Server();
		try {
			server.runServer();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
