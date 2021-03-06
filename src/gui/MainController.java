package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import sampca.SAMPCA;

/**
 * . Controller of the MainUI
 * 
 * @author Jeroen Waals
 * 
 */
public class MainController implements ActionListener, KeyListener {

	/**
	 * . View of the MVC model
	 */
	private MainUI view;

	/**
	 * . Constructor
	 * 
	 * @param view
	 *            - the view of the MVC model for this GUI
	 */
	public MainController(MainUI view) {
		this.view = view;
	}

	/**
	 * . Action that is performed when an ActionEvent is triggered When a button
	 * is pressed, this method will be performed
	 * 
	 * @param e
	 *            - event that happens
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.view.getExitButton() == e.getSource()) {
			System.exit(0);
		}

		if (this.view.getConnectButton() == e.getSource()) {
			this.makeConnection();
		}
	}
	
    public void keyPressed(KeyEvent e){
    	if(e.getKeyCode() == KeyEvent.VK_ENTER){
    		this.makeConnection();
        }       
    }
    
    private void makeConnection(){
		String serverIP = this.view.getServerField().getText();
		String portText = this.view.getPortField().getText();
		String nickname = this.view.getUsernameField().getText();
        String password = this.view.getPasswordField().getText();
		if (!serverIP.equals("") && !portText.equals("") && !nickname.equals("") && !password.equals("")) {
			try {
				int port = Integer.parseInt(portText);
				SAMPCA client = new SAMPCA(port, MainUI.LBL_DEF_First_IP_PART + serverIP, nickname, password);
				// Client client = new Client("luna", 5555, "Kurocon");
//				synchronized (client) {
//					try {
//						while (!client.isConnected && !client.finished) {
//							client.wait();
//						}
//					} catch (InterruptedException ie) {
//
//					}
//				}
				if (!client.finished) {
					this.view.getFrame().setVisible(false);
					//client.hello();
					//@SuppressWarnings("unused")
					// Is used to create a lobby view
					//LobbyUI lobby = new LobbyUI(client);
					//client.updateGUI();
				} else {
					new Popup("Connection failed");
				}
			} catch (NumberFormatException nfe) {
				new Popup("The port is not a number");
			}
		} else {
			new Popup("Invalid information");
		}
    }

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
