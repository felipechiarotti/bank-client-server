package clientserver;
import java.io.IOException;

import javax.swing.JOptionPane;

import clientserver.client.*;
import clientserver.gui.GUIClient;

public class RunClient {
	public static void main(String args[]) {
		GUIClient client = new GUIClient();
		client.go();
	}
}
