package clientserver;
import java.io.IOException;

import clientserver.client.*;

public class RunClient {
	public static void main(String args[]) {
		Client client = new Client("172.16.242.95");
		try {
			client.runClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
