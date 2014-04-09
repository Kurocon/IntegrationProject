package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import sampca.SAMPCA;

/**
 * Login user interface
 * 
 * @author
 * 
 */
public class MainUI implements Observer {
	/**
	 * Variable to store the frame of the UI
	 */
	private JFrame frame;
	/**
	 * Variable to store the textfield containing server name/IP
	 */
	private JTextField serverField;
	/**
	 * Variable to store the textfield containing port number
	 */
	private JTextField portField;
	/**
	 * Variable to store the textfield containing the username
	 */
	private JTextField usernameField;
	/**
	 * Variable to store the textfield containing the password
	 */
	private JPasswordField passwordField;
	/**
	 * Variable to store the button "Exit"
	 */
	private JButton exitButton;
	/**
	 * Variable to store the button "Connect"
	 */
	private JButton connectButton;
	/**
	 * Variable to store the controller of the MVC model of this user
	 * interface
	 */
	private MainController controller;

	public static final String WINDOW_TITLE = " - Login";

	public static final String BTN_CONNECT = "Connect";
	public static final String BTN_EXIT = "Exit";

	public static final String LBL_SERVER_IP = "Ad_Hoc IP:";
	public static final String LBL_SERVER_PORT = "Port:";
	public static final String LBL_USERNAME = "Username:";

	public static final String LBL_DEF_First_IP_PART = "228.";
	public static final String LBL_DEF_SECOND_IP_PART = "133.102.88";
	public static final String LBL_DEF_PORT = "5555";
	public static final String LBL_DEF_NICK = "John Doe";

	/**
	 * Launch the application.
	 * 
	 * @param args
	 *            - not used
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI window = new MainUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainUI() {
		initialize();
		frame.setResizable(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame(SAMPCA.PROGRAM_NAME + MainUI.WINDOW_TITLE);
		frame.getContentPane().setMinimumSize(new Dimension(220, 270));
		frame.getContentPane().setMaximumSize(new Dimension(220, 270));
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);

		JLabel serverLabel = new JLabel(MainUI.LBL_SERVER_IP);
		JLabel serverFirstDefLabel = new JLabel(MainUI.LBL_DEF_First_IP_PART);

		serverField = new JTextField();
		serverField.setColumns(10);
		serverField.setText(LBL_DEF_SECOND_IP_PART);

		JLabel portLabel = new JLabel(MainUI.LBL_SERVER_PORT);

		portField = new JTextField();
		portField.setColumns(10);
		portField.setText(LBL_DEF_PORT);

		connectButton = new JButton(MainUI.BTN_CONNECT);

		exitButton = new JButton(MainUI.BTN_EXIT);

		JLabel usernameLabel = new JLabel(MainUI.LBL_USERNAME);

		usernameField = new JTextField();
		usernameField.setColumns(10);
		usernameField.setText(LBL_DEF_NICK);

		this.controller = new MainController(this);

		GroupLayout groupLayoutPanel = new GroupLayout(panel);
		groupLayoutPanel
				.setHorizontalGroup(groupLayoutPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayoutPanel
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayoutPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayoutPanel
																		.createSequentialGroup()
																		.addGroup(
																				groupLayoutPanel
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayoutPanel
																										.createSequentialGroup()
																										.addComponent(
																												serverFirstDefLabel)))
																		.addGroup(
																				groupLayoutPanel
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayoutPanel
																										.createSequentialGroup()
																										.addComponent(
																												serverField,
																												GroupLayout.DEFAULT_SIZE,
																												28,
																												Short.MAX_VALUE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED))
																						.addGroup(
																								groupLayoutPanel
																										.createSequentialGroup()
																										.addComponent(
																												serverLabel)
																										.addGap(10)))
																		.addGap(15)
																		.addGroup(
																				groupLayoutPanel
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								portField,
																								GroupLayout.PREFERRED_SIZE,
																								81,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								portLabel,
																								GroupLayout.PREFERRED_SIZE,
																								59,
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																groupLayoutPanel
																		.createSequentialGroup()
																		.addGroup(
																				groupLayoutPanel
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								usernameField,
																								GroupLayout.DEFAULT_SIZE,
																								128,
																								Short.MAX_VALUE)
																						.addComponent(
																								usernameLabel)))
														.addGroup(
																groupLayoutPanel
																		.createSequentialGroup()
																		.addGroup(
																				groupLayoutPanel
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayoutPanel
																										.createParallelGroup(
																												Alignment.LEADING)
																										.addComponent(
																												connectButton,
																												GroupLayout.DEFAULT_SIZE,
																												128,
																												Short.MAX_VALUE)))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				groupLayoutPanel
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayoutPanel
																										.createParallelGroup(
																												Alignment.LEADING)
																										.addComponent(
																												exitButton,
																												GroupLayout.DEFAULT_SIZE,
																												128,
																												Short.MAX_VALUE)))))
										.addGap(10)));
		groupLayoutPanel
				.setVerticalGroup(groupLayoutPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayoutPanel
										.createSequentialGroup()
										.addContainerGap()
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayoutPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																serverLabel)
														.addComponent(portLabel))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayoutPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																serverFirstDefLabel)
														.addComponent(
																serverField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																portField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayoutPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																usernameLabel))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayoutPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																usernameField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayoutPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																connectButton)
														.addComponent(
																exitButton))
										.addContainerGap(10, Short.MAX_VALUE)));

		panel.setLayout(groupLayoutPanel);
		frame.setSize(300, 170);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		usernameField.requestFocusInWindow();
		connectButton.addActionListener(this.controller);
		exitButton.addActionListener(this.controller);
	}

	/**
	 * Method to update the UI.
	 * 
	 * @param arg0
	 *            - Observable
	 * @param arg1
	 *            - Object that is observed
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * Method to return the JTextField containing the server name/IP
	 * 
	 * @return JTextField - textfield that should contain the server name/IP
	 */
	public JTextField getServerField() {
		return serverField;
	}

	/**
	 * Method to return the JTextField containing the port number
	 * 
	 * @return JTextField - textfield that should contain the port number
	 */
	public JTextField getPortField() {
		return portField;
	}

	/**
	 * Method to return the JTextField containing the username
	 * 
	 * @return JTextField - textfield that should contain the username
	 */
	public JTextField getUsernameField() {
		return usernameField;
	}

	/**
	 * Method to return the JTextField containing the password
	 * 
	 * @return JTextField - textfield that should contain the password
	 */
	public JTextField getPasswordField() {
		return passwordField;
	}

	/**
	 * Method to return the exit button
	 * 
	 * @return JButton - "Exit" button
	 */
	public JButton getExitButton() {
		return exitButton;
	}

	/**
	 * Method to return the connect button
	 * 
	 * @return JButton - "Connect" button
	 */
	public JButton getConnectButton() {
		return connectButton;
	}

	public JFrame getFrame() {
		return this.frame;
	}
}
