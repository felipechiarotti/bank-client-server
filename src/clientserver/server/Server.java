package clientserver.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import clientserver.database.Database;

public class Server {
	private ServerSocket server;
	private Socket connection;
	private int connectedCount = 0;

	public void runServer() throws SQLException, IOException {
			server = new ServerSocket(12345, 100);
			while(true) {
				waitForConnection();
			}
	}
	
	
	private void waitForConnection() {
		System.out.println("[!] Esperando a conexão de clientes...");
		try {
			connection = server.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		connectedCount++;
		System.out.println("[!] Conexão estabelecida com " + connection.getInetAddress().getHostName());
		Thread t = new Thread(new ClientHandler(connection));
		t.start();
	}
	

	
	
}
