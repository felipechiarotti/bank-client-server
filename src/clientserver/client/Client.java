package clientserver.client;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Client {
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String chatServer;
	private Socket client;
	
	public Client(String host) {
		chatServer = host;
	}
	
	public void runClient(String loginName) throws IOException{
		connectToServer();
		setStreams();
		if(!logIn(loginName)) {
			this.client.close();
			throw new IOException();
		}
	}
	public String receiveServerMessage() {
		try {
			return (String)input.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private void connectToServer() throws IOException {
		client = new Socket(chatServer, 12345);
	}
	
	private void setStreams() throws IOException {
		output = new ObjectOutputStream(client.getOutputStream());
		input = new ObjectInputStream(client.getInputStream());
	}
	
	public void sendData(String message) throws IOException {
		output.writeObject(message);
		output.flush();
	}
	
	private boolean logIn(String loginName) throws IOException {
		output.writeObject("LOGIN;"+loginName);
		output.flush();
		try {
			return (boolean) input.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}