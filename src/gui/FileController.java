package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import protocol.Timestamp;

import network.User;

import sampca.SAMPCA;

public class FileController implements ActionListener {

	private FileChoose view;
	private SAMPCA client;
	private JFileChooser fc;
	private CaController control;
	private File file;

	public FileController(FileChoose fileChoose, JFileChooser fc, SAMPCA client,CaController control) {
		this.view = fileChoose;
		this.client = client;
		this.fc = fc;
		this.control = control;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Handle open button action.
		if (e.getSource() == view.getOpenButton()) {
			int returnVal = fc.showOpenDialog(view);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file = fc.getSelectedFile();
				// This is where a real application would open the file.
				view.getTextArea().setText(file.getName());
			} else {
				view.getTextArea().setText("Open command cancelled by user.");
			}
		}
		if (e.getSource() == view.getExitButton()) {
			if (this.view.getExitButton() == e.getSource()) {
				control.getFrame().dispose();
			}
		}
		if (e.getSource() == view.getTransferButton()) {
			if(file != null){
				new TransferPopup("Please wait, transfering file!\nThe program might become inresponsive.");
				User user = (User) view.getUserList().getSelectedItem();
				this.client.sendFile(file.getAbsolutePath(), user.getIP());
				this.control.getGui().addTransferMessage(this.client.getOwnUser().getIP(), user.getIP(), file.getName(), Timestamp.getCurrentTimeAsLong(), false);
				control.getFrame().dispose();
			} else {
				new Popup("No file selected");
			}
		}
	}
}
