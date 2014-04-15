package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

import network.User;

import sampca.SAMPCA;

public class CaController implements ActionListener, KeyListener {
	/**
	 * . View of the MVC model of the Lobby UI
	 */
	private CaUI view;

	private static final Logger LOGGER = Logger.getLogger(SAMPCA.class.getName());
	
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
		//this.view.getNickLabel().setText(this.client.clientInfo.nickname);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int i = view.tabs.getSelectedIndex();
        String tabName;
        if (i != -1) {
        	tabName = view.tabs.getTitleAt(i);
        }else{
        	tabName = "ERROR TAB NOT FOUND";
        }
        view.frame.setTitle(SAMPCA.PROGRAM_NAME + " - " + tabName  +" - " + CaUI.WINDOW_TITLE);
        
//		if (e.getSource() == this.view.getCreateGameButton()) {
//			LOGGER.log(Level.INFO, "Creating game.");
//			this.client.getServerHandler().sendCommand(
//					ServerProtocol.CREATE_GAME, new String[0]);
//		} else
			if (e.getSource() == this.view.getPrivateButton()) {
				User user = this.view.selectedPlayer;
				this.view.addTab(user);
				this.view.getPrivateButton().setEnabled(false);
			}
//			} else if (this.view.getJoinGameButton().getText().toLowerCase()
//					.equals(LobbyUI.BTN_START_GAME.toLowerCase())) {
//				// Start game if owner.
//				if (this.client.clientInfo.ownsGame) {
//					LOGGER.log(Logger.INFO, "Starting game.");
//					// TODO show GameUI
//					this.client.getServerHandler().sendCommand(
//							ServerProtocol.START_GAME, new String[0]);
//				}
//			}
//		} else if (e.getSource() == this.view.getJoinGamePCButton()) {
//			// Join game of selected user.
//			String creator = this.view.selectedPlayer;
//			LOGGER.log(Level.INFO, "Joining game by player " + creator + ".");
//			this.client.getServerHandler().sendCommand(
//					ServerProtocol.JOIN_GAME, new String[] { creator });
//			this.client.clientInfo.joiningGame = creator;l
//			this.client.clientInfo.joinAsComputer = true;
//		} else 
        
        PaneTab selectedTab = (PaneTab) this.view.tabs.getSelectedComponent();
    	if (e.getSource() == selectedTab.getSendButton()) {
    		sendMessage(selectedTab);
		}
	}
	
    public void keyPressed(KeyEvent e){
    	PaneTab selectedTab = (PaneTab) this.view.tabs.getSelectedComponent();
    	if(e.getKeyCode() == KeyEvent.VK_ENTER){
    		sendMessage(selectedTab);
        }       
    }
    
    private void sendMessage(PaneTab selectedTab){
    	if (!selectedTab.getMessageField().getText().isEmpty()) {
    		String message = selectedTab.getMessageField().getText();
			if(this.view.tabs.getSelectedIndex() == 0){
				//Public message
				int maxPublicMessageLength = this.client.MAX_PACKET_SIZE - this.client.GENERAL_HEADER_SIZE - this.client.PUBLIC_CHAT_HEADER_SIZE;
				int it = 0;
				while(it + maxPublicMessageLength < message.length()){
					client.sendPublicMessage(message.substring(it, it+maxPublicMessageLength-1));
					it = it+maxPublicMessageLength;
				}
				
				client.sendPublicMessage(message.substring(it, message.length()));
				
				
			}else{
				//private message
				int maxPrivateMessageLength = this.client.MAX_PACKET_SIZE - this.client.GENERAL_HEADER_SIZE - this.client.PRIVATE_CHAT_HEADER_SIZE;

				int it = 0;
				while(it + maxPrivateMessageLength < message.length()){
					client.sendPrivateMessage(message.substring(it, it+maxPrivateMessageLength-1), selectedTab.getAddress());
					it = it+maxPrivateMessageLength;
				}
				client.sendPrivateMessage(message.substring(it, message.length()), selectedTab.getAddress());
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
}