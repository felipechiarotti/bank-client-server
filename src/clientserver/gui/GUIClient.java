package clientserver.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import clientserver.client.Client;

public class GUIClient {
	String clientName;
	Client client = new Client("172.16.0.111");
	JFrame frame;
	JButton executeAction;
	JComboBox actions;
	String actionsList[] = {"Sacar", "Ver Saldo"};
	
	
	public void setFrame() {
		frame = new JFrame(clientName);
		
		executeAction = new JButton("executar");
		actions = new JComboBox(actionsList);
		
		executeAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String tmp = "";
				if(actions.getSelectedIndex() == 0) {
					tmp = "SAQUE;" + JOptionPane.showInputDialog("Insira o valor para sacar:");
					try {
						client.sendData(tmp);
						if(client.receiveServerMessage().equals("false")) {
							JOptionPane.showMessageDialog(null, "Saldo insuficiente para efetuar saque");
						}
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					try {
						client.sendData("SALDO");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "R$"+client.receiveServerMessage());
				}


			}
			
		});
		
		frame.setLayout(new GridLayout(1,2));
		frame.add(actions);
		frame.add(executeAction);
		
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setSize(300,100);
		frame.setVisible(true);
	}
	
	
	public void go() {
		 clientName = JOptionPane.showInputDialog("Digite o nome do Cliente:");
		 try {
			client.runClient(clientName);
			setFrame();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao efetuar login!", "ERRO", 1);
		}

	}
}
