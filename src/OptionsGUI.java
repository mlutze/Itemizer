import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class OptionsGUI extends GUI {
	
	public OptionsGUI(GUI parent) {
		this.parent = parent;
	}

	/* GUI Items */
	private JLabel timerLabel = new JLabel("Timer (sec):");
	private JTextField timerField = new JTextField(8);
	private JPanel timerPanel = new JPanel();
	
	private JLabel speedLabel = new JLabel("Output speed (cpm):");
	private JTextField speedField = new JTextField(8);
	private JPanel speedPanel = new JPanel();
	
	private JLabel copyKeyLabel = new JLabel("Copy modifier key:");
	private JTextField copyKeyField = new JTextField(8);
	private JPanel copyKeyPanel = new JPanel();
	
	private JLabel pasteKeyLabel = new JLabel("Paste modifier key:");
	private JTextField pasteKeyField = new JTextField(8);
	private JPanel pasteKeyPanel = new JPanel();
	
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
				Main.commandSpeed = (int) Float.parseFloat(speedField.getText());
			} catch (NumberFormatException nfe) {
				speedField.setText("Number Only");
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
		timerPanel.add(timerLabel);
		timerPanel.add(timerField);
		
		speedField.setText(""+Main.commandSpeed);
		speedPanel.add(speedLabel);
		speedPanel.add(speedField);
		
		copyKeyField.setText(""+Main.copyKey);
		copyKeyPanel.add(copyKeyLabel);
		copyKeyPanel.add(copyKeyField);
		
		pasteKeyField.setText(""+Main.pasteKey);
		pasteKeyPanel.add(pasteKeyLabel);
		pasteKeyPanel.add(pasteKeyField);
		
		cancelButton.addActionListener(onClickCancel);
		okButton.addActionListener(onClickOk);
		
		addAll(timerPanel, speedPanel, copyKeyPanel, pasteKeyPanel, cancelButton, okButton);
		addWindowListener(windowListener);
		pack();
		setVisible(true);
	}
}
