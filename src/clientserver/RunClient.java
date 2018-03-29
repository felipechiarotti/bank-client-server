package clientserver;
import java.io.IOException;

import javax.swing.JOptionPane;

import clientserver.client.*;

public class RunClient {
	public static void main(String args[]) {
		Client client = new Client("127.0.0.1");
		try {
			client.runClient(JOptionPane.showInputDialog("Digite o nome do Cliente:"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
