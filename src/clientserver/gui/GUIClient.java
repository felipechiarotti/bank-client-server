package clientserver.gui;

import javax.swing.JOptionPane;

import clientserver.client.Client;

public class GUIClient {
	String clientName;
	Client client = new Client("172.16.242.57");
	
	public void go() {
		 clientName = JOptionPane.showInputDialog("Digite o nome do Cliente:");
		 client.sendData("LOGIN");
	}
}
