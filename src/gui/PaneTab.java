package gui;

import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PaneTab extends JSplitPane{

	/**
	 * . Variable to store the message field of the chat
	 */
	private JTextField messageField;
	/**
	 * . Variable to store the message field of the chat
	 */
	private JTextArea textArea;
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
		this.textArea = newTextArea();
		initialize();
	}
	
	public void initialize(){
		super.setResizeWeight(1.0);
		super.setOrientation(JSplitPane.VERTICAL_SPLIT);
		super.setEnabled(false);
		super.setDividerSize(1);
		super.setLeftComponent(new JScrollPane(this.textArea));
	
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
		messageField.addKeyListener(controller);
	}
	
	private JTextArea newTextArea(){
		JTextArea tempTextArea = new JTextArea();
		tempTextArea.setEditable(false);
		tempTextArea.setAutoscrolls(true);
		tempTextArea.setFocusable(false);
		tempTextArea.setLineWrap(true);
		return tempTextArea;
	}
	
	public JTextField getMessageField(){
		return this.messageField;
	}
	
	public JTextArea getTetArea(){
		return this.textArea;
	}
	
	public JButton getSendButton(){
		return this.btnSend;
	}
	
	public String getNickName(){
		return this.nickName;
	}
	
	public InetAddress getAddress(){
		return this.address;
	}	
}
