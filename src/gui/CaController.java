package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import sampca.SAMPCA;

public class CaController implements ActionListener {
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
		this.view = view;
		this.client = client;
		//this.view.getNickLabel().setText(this.client.clientInfo.nickname);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		if (e.getSource() == this.view.getCreateGameButton()) {
//			LOGGER.log(Level.INFO, "Creating game.");
//			this.client.getServerHandler().sendCommand(
//					ServerProtocol.CREATE_GAME, new String[0]);
//		} else if (e.getSource() == this.view.getJoinGameButton()) {
//			// This button can be a Join Game button if client has no game, or
//			// Start Game button if user has game and is owner.
//			if (this.view.getJoinGameButton().getText().toLowerCase()
//					.equals(CaUI.BTN_JOIN_GAME.toLowerCase())) {
//				// Join game of selected user.
//				String creator = this.view.selectedPlayer;
//				LOGGER.log(Logger.INFO, "Joining game by player " + creator
//						+ ".");
//				this.client.getServerHandler().sendCommand(
//						ServerProtocol.JOIN_GAME, new String[] { creator });
//				this.client.clientInfo.joiningGame = creator;
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
//			this.client.clientInfo.joiningGame = creator;
//			this.client.clientInfo.joinAsComputer = true;
//		} else if (e.getSource() == this.view.getSendButton()) {
//			if (this.view.getMessageField() != null) {
//				String message = this.view.getMessageField().getText();
//				this.client.getServerHandler().sendCommand(
//						ServerProtocol.MESSAGE, new String[] { message });
//			}
//		}
	}
}