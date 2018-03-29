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
	private String message = "";
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String chatServer;
	private Socket client;
	
	public Client(String host) {
		chatServer = host;
	}
	
	public void runClient(String loginName) throws IOException {
		connectToServer();
		setStreams();
		try {
			if(logIn(loginName)) {
			//INVOCAR CLIENTE
				System.out.println("Logado com sucesso!");
			}else {
				JOptionPane.showMessageDialog(null, "Nome não encontrado", "ERRO AO LOGAR", 1);
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		message = "0";
		sendData();
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
	
	public void sendData() throws IOException {
		output.writeObject(message);
		output.flush();
	}
	
	private boolean logIn(String loginName) throws ClassNotFoundException, IOException {
		output.writeObject("LOGIN;"+loginName);
		output.flush();
		return (boolean) input.readObject();
	}
	
}