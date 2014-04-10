package gui;

import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PaneTab extends JSplitPane {
	
	/**
	 * . Variable to store the message field of the chat
	 */
	private JTextField messageField;
	/**
	 * . Variable to store the button to send a message
	 */
	private JButton btnSend;
	/**
	 * . Variable to store the button to send a message
	 */
	private String nickName;
	/**
	 * . Variable to store the button to send a message
	 */
	private InetAddress address;
	/**
	 * . Variable to store the button to send a message
	 */
	private CaController controller;
	
	public PaneTab(String nickname, InetAddress address, CaController controller){
		this.nickName = nickname;
		this.address = address;
		this.controller = controller;
		initialize();
	}
	
	public void initialize(){
		super.setResizeWeight(1.0);
		super.setOrientation(JSplitPane.VERTICAL_SPLIT);
		super.setEnabled(false);
		super.setDividerSize(1);
		
		super.setLeftComponent(newTextArea());
	
		JSplitPane messagePane = new JSplitPane();
		messagePane.setResizeWeight(1.0);
		messagePane.setDividerSize(1);
		super.setRightComponent(messagePane);
	
		messageField = new JTextField();
		messageField.setColumns(10);
		messagePane.setLeftComponent(messageField);
	
		btnSend = new JButton(CaUI.BTN_SEND);
		messagePane.setRightComponent(btnSend);

		btnSend.addActionListener(controller);
	}
	
	private JTextArea newTextArea(){
		JTextArea tempTextArea = new JTextArea();
		tempTextArea.setEditable(false);
		tempTextArea.setAutoscrolls(true);
		tempTextArea.setFocusable(false);
		tempTextArea.setLineWrap(true);
		return tempTextArea;
	}
	
}
