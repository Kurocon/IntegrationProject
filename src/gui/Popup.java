package gui;

import javax.swing.JOptionPane;

public class Popup {

	public Popup(String message) {
		JOptionPane.showMessageDialog(null, message, "Error",
				JOptionPane.ERROR_MESSAGE);
	}
}
