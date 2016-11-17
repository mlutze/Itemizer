import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class OptionsGUI extends GUI {
	
	public OptionsGUI(GUI parent) {
		this.parent = parent;
	}

	/* GUI Items */
	private JLabel timerLabel = new JLabel("Timer (sec):");
	private JTextField timerField = new JTextField(8);
	private JLabel commandDelay = new JLabel("Output delay (ms):");
	private JTextField delayField = new JTextField(8);
	private JButton cancelButton = new JButton("Cancel");
	private JButton okButton = new JButton("OK");

	/* Event Listeners */
	private ActionListener onClickOk = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean ok = true;
			try {
				Main.releaseDelay = (int) Float.parseFloat(timerField.getText());
			} catch (NumberFormatException nfe) {
				timerField.setText("Number Only");
				ok = false;
			}
			try {
				Main.commandDelay = (int) Float.parseFloat(delayField.getText());
			} catch (NumberFormatException nfe) {
				delayField.setText("Number Only");
				ok = false;
			}
			if (ok) {
				close();
			}
		}
	};
	private ActionListener onClickCancel = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			close();
		}
	};
	private WindowListener windowListener = new WindowListener() {

		@Override
		public void windowOpened(WindowEvent e) {
		}

		@Override
		public void windowClosing(WindowEvent e) {
			close();
		}
		@Override
		public void windowClosed(WindowEvent e) {
		}

		@Override
		public void windowIconified(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
		}

		@Override
		public void windowActivated(WindowEvent e) {
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		}
		
	};

	public void build() {
		parent.setEnabled(false);
		setTitle("Options");
		setLayout(new FlowLayout());
		
		timerField.setText(""+Main.releaseDelay);
		delayField.setText(""+Main.commandDelay);
		
		cancelButton.addActionListener(onClickCancel);
		okButton.addActionListener(onClickOk);
		
		addAll(timerLabel, timerField, commandDelay, delayField, cancelButton, okButton);
		addWindowListener(windowListener);
		pack();
		setVisible(true);
	}
}
