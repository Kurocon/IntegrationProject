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

public class FileChoose extends JPanel implements ActionListener {
	static private final String newline = "\n";
	private static final String LBL_SELECTED = "Currently selected file:";
	private static final String LBL_RECEIVER = "Send file to:";
	JButton openButton, closeButton, transferButton;
	JTextArea log;
	JFileChooser fc;
	private SAMPCA client;

	public FileChoose(SAMPCA client) {
		super(new BorderLayout());

		this.client = client;

		// Create the log first, because the action listeners
		// need to refer to it.
		log = new JTextArea();
		log.setEditable(false);

		// Create a file chooser
		fc = new JFileChooser();

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
		openButton = new JButton("Open a File...");
		openButton.addActionListener(this);
		closeButton = new JButton("Exit");
		closeButton.addActionListener(this);

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

		JComboBox users = new JComboBox(client.getUsers().toArray());
		users.setSelectedIndex(0);
		users.addActionListener(this);
		
		JLabel selectedLabel = new JLabel(this.LBL_SELECTED);
		JLabel receiverLabel = new JLabel(this.LBL_RECEIVER);
		
		center.add(selectedLabel, "2, 2, fill, top");
		center.add(log, "2, 4, fill, top");
		center.add(receiverLabel, "2, 6, fill, top");
		center.add(users, "2, 8, fill, fill");

		JPanel secondButtonPanel = new JPanel(); // use FlowLayout
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

	@Override
	public void actionPerformed(ActionEvent e) {

		// Handle open button action.
		if (e.getSource() == openButton) {
			int returnVal = fc.showOpenDialog(FileChoose.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				// This is where a real application would open the file.
				log.setText(file.getName());
			} else {
				log.append("Open command cancelled by user." + newline);
			}
			log.setCaretPosition(log.getDocument().getLength());

			// Handle save button action.
		}
	}
}
