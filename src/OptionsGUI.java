import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

	private final String keyPrompt = "Press a key";

	/* GUI Items */
	private JLabel timerLabel = new JLabel("Timer (sec):");
	private JTextField timerField = new JTextField(8);
	private JPanel timerPanel = new JPanel();

	private JLabel pasteKeyLabel = new JLabel("Paste modifier key:");
	private JTextField pasteKeyField = new JTextField(8);
	private JButton pasteKeyButton = new JButton("Change");
	private JPanel pasteKeyPanel = new JPanel();

	private JButton cancelButton = new JButton("Cancel");
	private JButton okButton = new JButton("OK");

	/* Event Listeners */
	private ActionListener onClickChangePaste = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			pasteKeyField.setText(keyPrompt);
			pasteKeyButton.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
				}

				@Override
				public void keyPressed(KeyEvent e) {
					pasteKeyField.setText("" + e.getKeyCode());
					pasteKeyButton.removeKeyListener(this);
				}

				@Override
				public void keyReleased(KeyEvent e) {
				}
			});
		}
	};
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
			Main.pasteKey = Integer.parseInt(pasteKeyField.getText());
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
	private FocusListener onClickAway = new FocusListener() {
		@Override
		public void focusGained(FocusEvent e) {
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (pasteKeyField.getText().equals(keyPrompt)) {
				pasteKeyField.setText("" + Main.pasteKey);
			}
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

		timerField.setText("" + Main.releaseDelay);
		timerPanel.add(timerLabel);
		timerPanel.add(timerField);

		pasteKeyField.setText("" + Main.pasteKey);
		pasteKeyField.setEditable(false);
		pasteKeyButton.addActionListener(onClickChangePaste);
		pasteKeyButton.addFocusListener(onClickAway);
		pasteKeyPanel.add(pasteKeyLabel);
		pasteKeyPanel.add(pasteKeyField);
		pasteKeyPanel.add(pasteKeyButton);

		cancelButton.addActionListener(onClickCancel);
		okButton.addActionListener(onClickOk);

		addAll(timerPanel, pasteKeyPanel, cancelButton, okButton);
		addWindowListener(windowListener);
		pack();
		setVisible(true);
	}
}
