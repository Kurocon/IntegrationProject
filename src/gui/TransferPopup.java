package gui;

import javax.swing.JOptionPane;

public class TransferPopup {

	public TransferPopup(String message) {
		JOptionPane.showMessageDialog(null, message, "Please wait",
				JOptionPane.INFORMATION_MESSAGE);
	}
}
