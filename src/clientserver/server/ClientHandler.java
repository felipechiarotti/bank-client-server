package clientserver.server;

import java.beans.Statement;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.jdbc.PgStatement;

import clientserver.database.Database;

public class ClientHandler implements Runnable{
	private PgStatement stmt;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket connection;
	private String message;
	
	public ClientHandler(Socket conn) {
		this.connection = conn;
		setStreams();

		try {
			stmt = 	(PgStatement) Database.getConnection().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void setStreams() {
		try {
			output = new ObjectOutputStream(connection.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			input = new ObjectInputStream(connection.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public void run() {
		do {
			waitForMessage();
			System.out.println("["+connection.getInetAddress().getHostName()+"]: "+this.message);
			try {
				processMessage(message);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while(!message.equals("0"));
		
		
		
		
		try {
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void waitForMessage() {
		try {
			message = (String) input.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void processMessage(String message) throws SQLException {
		String op[] = message.split(";");
		String sql = "";

	
		switch(op[0]) {
			case "LOGIN":
				sql = "SELECT * FROM clients WHERE name = '"+op[1]+"'";
				ResultSet result = stmt.executeQuery(sql);
				try {
					output.writeObject(result.next());
					output.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
		}
	}



}