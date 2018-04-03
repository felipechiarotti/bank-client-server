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

	public void runServer() throws IOException{
			server = new ServerSocket(12345, 100);
			while(true) {
				waitForConnection();
			}
	}
	
	
	private void waitForConnection() {
		try {
			Socket connection;
			System.out.println("[!] Esperando a conexão de clientes...");
			connection = server.accept();
			System.out.println("[!] Conexão estabelecida com " + connection.getInetAddress().getHostName());			
			Thread t = new Thread(new ClientHandler(connection));
			t.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}catch(SQLException sqlEx) {
			sqlEx.printStackTrace();
		}
		
	}
}
