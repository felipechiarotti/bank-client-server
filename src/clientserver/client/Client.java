package clientserver.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private String message = "";
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String chatServer;
	private Socket client;
	BufferedReader in;
	
	public Client(String host) {
		chatServer = host;
		this.in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public void runClient() throws IOException {
		connectToServer();
		setStreams();
		do {
			this.message = in.readLine();
			sendData();
		}while(!this.message.equals("0"));
		this.client.close();
	}

	private void connectToServer() {
		try {
			client = new Socket(chatServer, 12345);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Conectado ao servidor!");
	}
	
	private void setStreams() {
		try {
			output = new ObjectOutputStream(client.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			input = new ObjectInputStream(client.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendData() throws IOException {
		output.writeObject(message);
		output.flush();
	}
	
}