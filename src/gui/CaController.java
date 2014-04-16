package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import protocol.Timestamp;

import network.User;

import sampca.SAMPCA;

public class CaController implements ActionListener, KeyListener,
		ChangeListener {
	/**
	 * . View of the MVC model of the Lobby UI
	 */
	private CaUI view;
	
	private JFrame frame;

	private static final Logger LOGGER = Logger.getLogger(SAMPCA.class
			.getName());

	private SAMPCA client;

	/**
	 * . Constructor of this class
	 * 
	 * @param view
	 *            - View of the MVC model of the Lobby UI
	 */
	public CaController(CaUI view, SAMPCA client) {
		LOGGER.setLevel(SAMPCA.GLOBAL_LOGGER_LEVEL);
		this.view = view;
		this.client = client;
		// this.view.getNickLabel().setText(this.client.clientInfo.nickname);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		// if (e.getSource() == this.view.getCreateGameButton()) {
		// LOGGER.log(Level.INFO, "Creating game.");
		// this.client.getServerHandler().sendCommand(
		// ServerProtocol.CREATE_GAME, new String[0]);
		// } else
		if (e.getSource() == this.view.getPrivateButton()) {
			User user = this.view.selectedPlayer;
			this.view.addTab(user);
			this.view.getPrivateButton().setEnabled(false);
		}
		if (e.getSource() == this.view.getTransferButton()) {
	        //Create and set up the window.
	        frame = new JFrame("FileChooserDemo");
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	 
	        //Add content to the window.
	        frame.add(new FileChoose(this, client));
	 
	        //Display the window.
	        frame.pack();
	        frame.setSize(300, 200);
			frame.setLocationRelativeTo(null);
			frame.setResizable(false);
	        frame.setVisible(true);
	        
		}
		// } else if (this.view.getJoinGameButton().getText().toLowerCase()
		// .equals(LobbyUI.BTN_START_GAME.toLowerCase())) {
		// // Start game if owner.
		// if (this.client.clientInfo.ownsGame) {
		// LOGGER.log(Logger.INFO, "Starting game.");
		// // TODO show GameUI
		// this.client.getServerHandler().sendCommand(
		// ServerProtocol.START_GAME, new String[0]);
		// }
		// }
		// } else if (e.getSource() == this.view.getJoinGamePCButton()) {
		// // Join game of selected user.
		// String creator = this.view.selectedPlayer;
		// LOGGER.log(Level.INFO, "Joining game by player " + creator + ".");
		// this.client.getServerHandler().sendCommand(
		// ServerProtocol.JOIN_GAME, new String[] { creator });
		// this.client.clientInfo.joiningGame = creator;l
		// this.client.clientInfo.joinAsComputer = true;
		// } else

		PaneTab selectedTab = (PaneTab) this.view.tabs.getSelectedComponent();
		if (e.getSource() == selectedTab.getSendButton()) {
			sendMessage(selectedTab);
		}
	}

	public JFrame getFrame(){
		return this.frame;
	}
	
	public CaUI getGui(){
		return this.view;
	}
	
	public void keyPressed(KeyEvent e) {
		PaneTab selectedTab = (PaneTab) this.view.tabs.getSelectedComponent();
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			sendMessage(selectedTab);
		}
	}

	private void sendMessage(PaneTab selectedTab) {
		if (!selectedTab.getMessageField().getText().isEmpty()) {
			String message = selectedTab.getMessageField().getText();
			if (this.view.tabs.getSelectedIndex() == 0) {
				// Public message
				int maxPublicMessageLength = this.client.MAX_PACKET_SIZE
						- this.client.GENERAL_HEADER_SIZE
						- this.client.PUBLIC_CHAT_HEADER_SIZE;
				int it = 0;
				while (it + maxPublicMessageLength < message.length()) {
					client.sendPublicMessage(message.substring(it, it
							+ maxPublicMessageLength - 1));
					it = it + maxPublicMessageLength;
				}

				client.sendPublicMessage(message.substring(it, message.length()));

			} else {
				// private message
				int maxPrivateMessageLength = this.client.MAX_PACKET_SIZE
						- this.client.GENERAL_HEADER_SIZE
						- this.client.PRIVATE_CHAT_HEADER_SIZE;

				int it = 0;
				while (it + maxPrivateMessageLength < message.length()) {
					client.sendPrivateMessage(
							message.substring(it, it + maxPrivateMessageLength
									- 1), selectedTab.getAddress());
					it = it + maxPrivateMessageLength;
				}
				client.sendPrivateMessage(
						message.substring(it, message.length()),
						selectedTab.getAddress());
				this.view.addMessage(this.client.getOwnUser().getIP(),
						selectedTab.getAddress(), message,
						Timestamp.getCurrentTimeAsLong(), true);
			}
		}
		selectedTab.getMessageField().setText("");
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		int i = view.tabs.getSelectedIndex();
		String tabName;
		if (i != -1) {
			tabName = view.tabs.getTitleAt(i);
		} else {
			tabName = "ERROR TAB NOT FOUND";
		}
		view.frame.setTitle(SAMPCA.PROGRAM_NAME + " - " + tabName + " - "
				+ CaUI.WINDOW_TITLE);
		if (view.tabs.getComponent(i).getClass().equals(gui.PaneTab.class)) {
			PaneTab tab = (PaneTab) view.tabs.getComponentAt(i);
			tab.getMessageField().requestFocus();
		}
	}
}