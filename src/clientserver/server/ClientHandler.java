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
	private String sessionAuth;
	
	public ClientHandler(Socket conn) throws SQLException, IOException {
		this.connection = conn;
		setStreams();
		stmt = 	(PgStatement) Database.getConnection().createStatement();
	}


	private void setStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		input = new ObjectInputStream(connection.getInputStream());
	}
	
	
	
	@Override
	public void run() {
		try {
			while(true) {
				waitForMessage();
				System.out.println("["+connection.getInetAddress().getHostName()+"]: "+this.message);
				try {
					processMessage(message);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}catch(IOException e) {
			System.out.println("[!] Perda de conexão com cliente!");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}finally {
			try {
				System.out.println("[!] Cliente Desconectado: "+connection.getInetAddress().getHostName());
				this.connection.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void waitForMessage() throws ClassNotFoundException, IOException {
			message = (String) input.readObject();
	}
	
	private void processMessage(String message) throws SQLException, IOException {
		String op[] = message.split(";");
		String sql = "";
		ResultSet result;
	
		switch(op[0]) {
			case "LOGIN":
				sql = "SELECT * FROM clients WHERE name = '"+op[1]+"'";
				result = stmt.executeQuery(sql);
				output.writeObject(result.next());
				output.flush();
				sessionAuth = op[1];
			break;
			
			case "SALDO":
				sql = "SELECT * FROM clients WHERE name = '"+sessionAuth+"'";
				result = stmt.executeQuery(sql);
				result.next();
				output.writeObject(Integer.toString(result.getInt(6)));
				output.flush();
			break;
			
			case "SAQUE":
				sql = "SELECT * FROM clients WHERE name = '"+sessionAuth+"'";
				result = stmt.executeQuery(sql);
				result.next();
				int newValue = result.getInt(6) - Integer.parseInt(op[1]);
				if(newValue > 0) {
					sql = "UPDATE clients SET balance = balance - "+Integer.parseInt(op[1])+" WHERE name = '"+sessionAuth+"'";
					stmt.executeUpdate(sql);
					output.writeObject("true");
				}else {
					output.writeObject("false");
				}
			break;
		}
	}



}