package gui;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import sampca.SAMPCA;

public class FileChoose extends JPanel{
	private static final String LBL_SELECTED = "Currently selected file:";
	private static final String LBL_RECEIVER = "Send file to:";
	private JButton openButton, closeButton, transferButton;
	private JTextArea log;
	private JFileChooser fc;
	private SAMPCA client;
	private FileController controller;
	private CaController control;
	private JComboBox users;

	public FileChoose(CaController control, SAMPCA client) {
		super(new BorderLayout());
	
		this.client = client;
		this.control = control;

		// Create the log first, because the action listeners
		// need to refer to it.
		log = new JTextArea();
		log.setEditable(false);

		// Create a file chooser
		fc = new JFileChooser();
		
		this.controller = new FileController(this, fc, client, control);

		// Uncomment one of the following lines to try a different
		// file selection mode. The first allows just directories
		// to be selected (and, at least in the Java look and feel,
		// shown). The second allows both files and directories
		// to be selected. If you leave these lines commented out,
		// then the default mode (FILES_ONLY) will be used.
		//
		// fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		// Create the open button. We use the image from the JLF
		// Graphics Repository (but we extracted it from the jar).
		openButton = new JButton("Select a File");
		openButton.addActionListener(controller);
		closeButton = new JButton("Exit");
		closeButton.addActionListener(controller);
		transferButton = new JButton("Transfer");
		transferButton.addActionListener(controller);

		// For layout purposes, put the buttons in a separate panel
		JPanel buttonPanel = new JPanel(); // use FlowLayout
		buttonPanel.add(openButton);

		JPanel center = new JPanel();
		center.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("25px"), ColumnSpec.decode("250px"),
				ColumnSpec.decode("25px"), }, new RowSpec[] {
				RowSpec.decode("5px"), RowSpec.decode("15px"),
				RowSpec.decode("5px"), RowSpec.decode("15px"),
				RowSpec.decode("5px"), RowSpec.decode("15px"),
				RowSpec.decode("5px"), RowSpec.decode("25px"), }));

		users = new JComboBox(client.getUsers().toArray());
		users.setSelectedIndex(0);
		users.addActionListener(controller);
		
		JLabel selectedLabel = new JLabel(this.LBL_SELECTED);
		JLabel receiverLabel = new JLabel(this.LBL_RECEIVER);
		
		center.add(selectedLabel, "2, 2, fill, top");
		center.add(log, "2, 4, fill, top");
		center.add(receiverLabel, "2, 6, fill, top");
		center.add(users, "2, 8, fill, fill");

		JPanel secondButtonPanel = new JPanel(); // use FlowLayout
		secondButtonPanel.add(transferButton);
		secondButtonPanel.add(closeButton);

		// Add the buttons and the log to this panel.
		add(buttonPanel, BorderLayout.PAGE_START);
		add(center, BorderLayout.CENTER);
		add(secondButtonPanel, BorderLayout.PAGE_END);
	}

	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = FileChoose.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	
	public JButton getOpenButton() {
		return this.openButton;
	}

	public JButton getExitButton() {
		return this.closeButton;
	}
	
	public JButton getTransferButton() {
		return this.transferButton;
	}
	
	public JTextArea getTextArea() {
		return this.log;
	}
	
	public JComboBox getUserList() {
		return this.users;
	}

}
