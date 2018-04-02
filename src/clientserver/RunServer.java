package clientserver;
import java.io.IOException;
import java.sql.SQLException;

import clientserver.server.Server;

public class RunServer {
	public static void main(String args[]) {
		Server server = new Server();
		try {
			server.runServer();
		}catch(IOException ex) {
			System.out.println("[!] Ocorreu um erro iniciar o servidor!");
			ex.printStackTrace();
		}
	}
}
