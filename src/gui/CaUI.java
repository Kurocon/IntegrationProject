package gui;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import includes.Popup;
import sampca.SAMPCA;

import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class CaUI extends Observable implements Observer {

	private static final Logger LOGGER = Logger.getLogger(SAMPCA.class
			.getName());

	public static final String WINDOW_TITLE = "Chat and File Transfer";
	public static final String CONNECTED_USERS = "Users";
	public static final String ONLINE = "Online";
	public static final String MAIN_TAB = "Educafé";
	public static final String MAIN_TAB_HINT = "Main chat";
	public static final String FILE_TAB = "File Transfer";
	public static final String FILE_TAB_HINT = "Files transfered between users";
	public static final String PRIVATE_TAB_HINT = "Private chat with: ";
	public static final String BTN_SEND = "Send";
	public static final String BTN_SEND_FILE = "Send file";
	public static final String LBL_DEFAULT_USERNAME = "(unknown)";

	/**
	 * . Variable to store the frame of the UI
	 */
	public JFrame frame;
	/**
	 * . Variable to store the message field of the chat
	 */
	private JTextField messageField;
	/**
	 * . Variable to store the button to join a game
	 */
	private JButton btnJoinGame;
	/**
	 * . Variable to store the button to join a game
	 */
	private JButton btnJoinGamePC;
	/**
	 * . Variable to store the button to create a game
	 */
	private JButton btnCreateGame;
	/**
	 * . Variable to store the button to send a message
	 */
	private JButton btnSend;
	/**
	 * . Variable to store the JTree which contains all connectedPlayers
	 */
	private JTree connectedPlayers;
	/**
	 * Variable to store the client
	 */
	private SAMPCA client;
	/**
	 * Variable to store the controller of this MVC model
	 */
	private CaController controller;

	/**
	 * Variable to store the chat area tabs
	 */
	public JTabbedPane tabs;

	/**
	 * Variable to store the chat area tabs
	 */
	private int[] keyEvents = new int[10];

	/**
	 * Variable to store the chat area tabs
	 */
	private int nextKeyEvents = 2;

	public DefaultMutableTreeNode treeRoot;
	public DefaultMutableTreeNode mainRoot;

	public String selectedPlayer = "";
	private JLabel lblUsername;

	/**
	 * Create the application.
	 */
	public CaUI(SAMPCA client) {
		this.client = client;
		// this.client.setCaUI(this);
		// this.client.addObserver(this);
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Icons
		ImageIcon serverIcon = addIcon("server.png");
		ImageIcon tabIcon = addIcon("user.png");
		ImageIcon transferIcon = addIcon("transfer.png");
		
		keyEvents[0] = KeyEvent.VK_1;
		keyEvents[1] = KeyEvent.VK_2;
		keyEvents[2] = KeyEvent.VK_3;
		keyEvents[3] = KeyEvent.VK_4;
		keyEvents[4] = KeyEvent.VK_5;
		keyEvents[5] = KeyEvent.VK_6;
		keyEvents[6] = KeyEvent.VK_7;
		keyEvents[7] = KeyEvent.VK_8;
		keyEvents[8] = KeyEvent.VK_9;
		keyEvents[9] = KeyEvent.VK_0;
		
		frame = new JFrame();
		frame.setTitle(SAMPCA.PROGRAM_NAME + " - " + CaUI.MAIN_TAB  +" - " + CaUI.WINDOW_TITLE);
		frame.setMinimumSize(new Dimension(500, 400));
		frame.setBounds(100, 100, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JSplitPane wrapperPane = new JSplitPane();
		wrapperPane.setEnabled(false);
		wrapperPane.setDividerSize(1);
		wrapperPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frame.getContentPane().add(wrapperPane, BorderLayout.CENTER);

		JPanel usernamePanel = new JPanel();
		usernamePanel.setAlignmentX(0.0f);
		wrapperPane.setLeftComponent(usernamePanel);
		FlowLayout fl_usernamePanel = new FlowLayout(FlowLayout.LEFT, 5, 5);
		usernamePanel.setLayout(fl_usernamePanel);

		lblUsername = new JLabel(CaUI.LBL_DEFAULT_USERNAME);
		lblUsername.setFont(new Font("Dialog", Font.PLAIN, 12));
		usernamePanel.add(lblUsername);

		JSplitPane contentPane = new JSplitPane();
		contentPane.setEnabled(false);
		contentPane.setDividerSize(1);
		wrapperPane.setRightComponent(contentPane);

		JSplitPane chatPane = new JSplitPane();
		chatPane.setResizeWeight(1.0);
		chatPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		chatPane.setEnabled(false);
		chatPane.setDividerSize(1);
		contentPane.setRightComponent(chatPane);

		tabs = new JTabbedPane();
		tabs.addTab(MAIN_TAB, serverIcon, newTextArea(),
				MAIN_TAB_HINT);
		tabs.setMnemonicAt(0, keyEvents[0]);
		tabs.addTab(FILE_TAB, transferIcon, newTextArea(),
				FILE_TAB_HINT);
		tabs.setMnemonicAt(1, keyEvents[1]);
		//addTab("someone");
		addTab(tabs, new JTextArea(), "someone", tabIcon);
		addTab(tabs, new JTextArea(), "Kurocon", tabIcon);
		addTab(tabs, new JTextArea(), "Someone else", tabIcon);
		
		//addTab("Kurocon");
		chatPane.setLeftComponent(tabs);

		JSplitPane messagePane = new JSplitPane();
		messagePane.setResizeWeight(1.0);
		messagePane.setDividerSize(1);
		chatPane.setRightComponent(messagePane);

		messageField = new JTextField();
		messageField.setColumns(10);
		messagePane.setLeftComponent(messageField);

		btnSend = new JButton(CaUI.BTN_SEND);
		messagePane.setRightComponent(btnSend);

		JSplitPane menuPane = new JSplitPane();
		menuPane.setEnabled(false);
		menuPane.setDividerSize(1);
		menuPane.setResizeWeight(1.0);
		menuPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.setLeftComponent(menuPane);

		JPanel buttonPanel = new JPanel();
		menuPane.setRightComponent(buttonPanel);
		buttonPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("165px"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("25px"), FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("25px"), FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("25px"), }));

		connectedPlayers = new JTree();
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		//ConnectedUsersTreeRenderer renderer = new ConnectedUsersTreeRenderer();

		renderer.setDisabledIcon(serverIcon);
		renderer.setClosedIcon(serverIcon);
		renderer.setOpenIcon(serverIcon);
		renderer.setIcon(serverIcon);

		connectedPlayers.setCellRenderer(renderer);
		connectedPlayers.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		this.treeRoot = new DefaultMutableTreeNode(CaUI.CONNECTED_USERS);

		this.mainRoot = new DefaultMutableTreeNode(CaUI.ONLINE);

		mainRoot.setAllowsChildren(true);

		treeRoot.add(mainRoot);

		DefaultTreeModel treeModel = new DefaultTreeModel(treeRoot);
		connectedPlayers.setModel(treeModel);

		for (int i = 0; i < connectedPlayers.getRowCount(); i++) {
			connectedPlayers.expandRow(i);
		}

		connectedPlayers.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) connectedPlayers
						.getLastSelectedPathComponent();

				/* if nothing is selected */
				if (node == null)
					return;

				/* retrieve the node that was selected */
				Object nodeInfo = node.getUserObject();
			}
		});

		// Add tree to pane.
		menuPane.setLeftComponent(connectedPlayers);
		menuPane.setDividerLocation(220);
		contentPane.setDividerLocation(190);
		frame.setSize(1000, 800);
		frame.setLocationRelativeTo(null);
		this.controller = new CaController(this, client);
		btnSend.addActionListener(this.controller);
	}

	private ImageIcon addIcon(String image){
		Image img;
		try {
			img = ImageIO.read(getClass().getResource("images/" + image));
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Could not load image " + image);
			new Popup("Could not load image " + image);
			img = null;
		}

		return new ImageIcon(img);
	}
	
	private JTextArea newTextArea(){
		JTextArea tempTextArea = new JTextArea();
		tempTextArea.setEditable(false);
		tempTextArea.setAutoscrolls(true);
		tempTextArea.setFocusable(false);
		tempTextArea.setLineWrap(true);
		return tempTextArea;
	}
	
	private void addTab(final JTabbedPane tabbedPane, final JComponent c, final String nickname,
	          final Icon icon){
		ImageIcon closeIcon = addIcon("close.png");	
		ImageIcon closeIcon2 = addIcon("close2.png");	
		tabs.addTab(null, c);
		tabs.setMnemonicAt(this.nextKeyEvents, keyEvents[this.nextKeyEvents]);
		this.nextKeyEvents++;
	    int pos = tabbedPane.indexOfComponent(c);

	    // Create a FlowLayout that will space things 5px apart
	    FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);

	    // Make a small JPanel with the layout and make it non-opaque
	    JPanel pnlTab = new JPanel(f);
	    pnlTab.setOpaque(false);

	    // Add a JLabel with title and the left-side tab icon
	    JLabel lblTitle = new JLabel(nickname);
	    lblTitle.setIcon(icon);

	    // Create a JButton for the close tab button
	    JButton btnClose = new JButton();
	    btnClose.setBorderPainted(false); 
	    btnClose.setContentAreaFilled(false); 
	    btnClose.setFocusPainted(false); 
	    btnClose.setOpaque(false);
	    // Configure icon and rollover icon for button
	    btnClose.setRolloverIcon(closeIcon2);
	    btnClose.setRolloverEnabled(true);
	    btnClose.setIcon(closeIcon);

	    // Set border null so the button doesn't make the tab too big
	    btnClose.setBorder(null);

	    // Make sure the button can't get focus, otherwise it looks funny
	    btnClose.setFocusable(false);

	    // Put the panel together
	    pnlTab.add(lblTitle);
	    pnlTab.add(btnClose);

	    // Add a thin border to keep the image below the top edge of the tab
	    // when the tab is selected
	    pnlTab.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

	    // Now assign the component for the tab
	    tabbedPane.setTabComponentAt(pos, pnlTab);

	    // Add the listener that removes the tab
	    ActionListener listener = new ActionListener() {
	      @Override
	      public void actionPerformed(ActionEvent e) {
	        // The component parameter must be declared "final" so that it can be
	        // referenced in the anonymous listener class like this.
	        tabbedPane.remove(c);
	      }
	    };
	    btnClose.addActionListener(listener);

	    // Optionally bring the new tab to the front
	    tabbedPane.setSelectedComponent(c);

	    //-------------------------------------------------------------
	    // Bonus: Adding a <Ctrl-W> keystroke binding to close the tab
	    //-------------------------------------------------------------
	    AbstractAction closeTabAction = new AbstractAction() {
	      @Override
	      public void actionPerformed(ActionEvent e) {
	        tabbedPane.remove(c);
	      }
	    };

	    // Create a keystroke
	    KeyStroke controlW = KeyStroke.getKeyStroke("control W");

	    // Get the appropriate input map using the JComponent constants.
	    // This one works well when the component is a container. 
	    InputMap inputMap = c.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

	    // Add the key binding for the keystroke to the action name
	    inputMap.put(controlW, "closeTab");

	    // Now add a single binding for the action name to the anonymous action
	    c.getActionMap().put("closeTab", closeTabAction);
		
	}
	
	@Override
	public void update(Observable arg1, Object arg2) {
		if (this.controller != null) {
			LOGGER.log(Level.FINE, "Updating GUI");

			// LOGGER.log(Level.FINE, "Number of connected clients: "
			// + this.client.serverInfo.clients.size());
			// ArrayList<ClientInformation> clients =
			// this.client.serverInfo.clients;
			ArrayList<DefaultMutableTreeNode> users = new ArrayList<DefaultMutableTreeNode>();

			// for (ClientInformation client : clients) {
			// LOGGER.log(Level.FINE, "New user detected: "
			// + client.nickname);
			// DefaultMutableTreeNode user = new DefaultMutableTreeNode(client);
			// users.add(user);
			// }

			LOGGER.log(Level.FINE, "Removing all children");
			mainRoot.removeAllChildren();

			for (DefaultMutableTreeNode user : users) {
				LOGGER.log(Level.FINE,
						"Adding new user to group: " + user.getUserObject());
				mainRoot.add(user);
			}

			connectedPlayers.updateUI();

			frame.repaint();

			for (int i = 0; i < connectedPlayers.getRowCount(); i++) {
				connectedPlayers.expandRow(i);
			}
		}
	}

	/**
	 * . Method to return the messageField
	 * 
	 * @return messageField - field containing chat message
	 */
	public JTextField getMessageField() {
		return messageField;
	}

	/**
	 * . Method to return the button to join a game
	 * 
	 * @return btnJoinGame - "Join game" button
	 */
	public JButton getJoinGameButton() {
		return btnJoinGame;
	}

	/**
	 * . Method to return the button to join a game as pc
	 * 
	 * @return btnJoinGame - "Join game" button
	 */
	public JButton getJoinGamePCButton() {
		return btnJoinGamePC;
	}

	/**
	 * . Method to return the button to create a game
	 * 
	 * @return btnCreateGame - "Create game" button
	 */
	public JButton getCreateGameButton() {
		return btnCreateGame;
	}

	/**
	 * . Method to return the button to send a chat message
	 * 
	 * @return btnSend - "Send" button
	 */
	public JButton getSendButton() {
		return btnSend;
	}

	/**
	 * . Method to return the JTree containing all connected players
	 * 
	 * @return connectedPlayers - JTree with all connected players
	 */
	public JTree getConnectedPlayers() {
		return connectedPlayers;
	}

	public JFrame getFrame() {
		return frame;
	}

	public JLabel getNickLabel() {
		return lblUsername;
	}

//	public void addMessage(String name, String body) {
//		if (chatArea != null && messageField != null) {
//			chatArea.append(name + ": " + body + "\n");
//			// if (name.toLowerCase().equals(
//			// this.client.clientInfo.nickname.toLowerCase())) {
//			// messageField.setText("");
//			// }
//		}
//	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CaUI window = new CaUI(new SAMPCA(5555, "228.133.102.88", "Kurocon", "password"));
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}