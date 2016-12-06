import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class MainGUI extends GUI {

	/* Instance */
	MainGUI self = this;

	GrabAndRelease grabAndRelease = new GrabAndRelease();

	/* GUI Panels */
	private JPanel nwPanel = new JPanel();
	private JPanel wPanel = new JPanel();
	private JPanel swPanel = new JPanel();
	private JPanel nPanel = new JPanel();
	private JPanel cPanel = new JPanel();
	private JPanel sPanel = new JPanel();
	private JPanel nePanel = new JPanel();
	private JPanel ePanel = new JPanel();
	private JPanel sePanel = new JPanel();
	private JPanel[] panels = { nwPanel, nPanel, nePanel, wPanel, cPanel, ePanel, swPanel, sPanel, sePanel };

	/* GUI Components */
	private JButton loadItemButton = new JButton("Load Item");
	private JButton saveItemButton = new JButton("Save Item");
	private JButton clearItemInfoButton = new JButton("Clear Item Info");

	private JButton loadTemplateButton = new JButton("Load Template");
	private JButton saveTemplateButton = new JButton("Save Template");
	private JButton clearTemplateButton = new JButton("Clear Template");

	private JSlider wrapSlider = new JSlider();

	private JButton optionsButton = new JButton("Options");
	private JButton helpButton = new JButton("Help");

	private JComboBox<String> insertColorCodeCombo = new JComboBox<>(Main.colors);
	private JComboBox<String> insertFormatCodeCombo = new JComboBox<>(Main.formats);

	private JButton grabButton = new JButton("Grab");
	private JButton dropButton = new JButton("Drop");

	private JProgressBar timerProgressBar = new JProgressBar();

	private JTextArea itemInfoTextArea = new JTextArea();
	private JScrollPane itemInfoScrollPane = new JScrollPane(itemInfoTextArea);
	private JTextArea templateTextArea = new JTextArea();
	private JScrollPane templateScrollPane = new JScrollPane(templateTextArea);
	private JTextPane previewTextPane = new JTextPane();
	private JScrollPane previewScrollPane = new JScrollPane(previewTextPane);

	private JFileChooser fileChooser = new JFileChooser();

	/* Event Listeners */
	private ActionListener onClickLoadItem = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Click!");
			if (fileChooser.showOpenDialog(loadItemButton) == JFileChooser.APPROVE_OPTION) {
				try {
					loadItem(fileChooser.getSelectedFile());
				} catch (IOException e1) {
					// TODO ERROR HANDLING
				}
			}
		}
	};
	private ActionListener onClickSaveItem = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (fileChooser.showSaveDialog(saveItemButton) == JFileChooser.APPROVE_OPTION) {
				try {
					saveItem(fileChooser.getSelectedFile());
				} catch (IOException e1) {
					// TODO ERROR HANDLING
				}
			}
		}
	};
	private ActionListener onClickClearItemInfo = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			itemInfoTextArea.setText("");
		}
	};
	private ActionListener onClickLoadTemplate = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (fileChooser.showOpenDialog(loadTemplateButton) == JFileChooser.APPROVE_OPTION) {
				try {
					loadTemplate(fileChooser.getSelectedFile());
				} catch (IOException e1) {
					// TODO ERROR HANDLING
				}
			}
		}
	};
	private ActionListener onClickSaveTemplate = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (fileChooser.showSaveDialog(saveTemplateButton) == JFileChooser.APPROVE_OPTION) {
				try {
					saveTemplate(fileChooser.getSelectedFile());
				} catch (IOException e1) {
					// TODO ERROR HANDLING
				}
			}
		}
	};
	private ActionListener onClickClearTemplate = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			templateTextArea.setText("");
		}
	};
	private ChangeListener onChangeWidth = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			updatePreview();
		}
	};
	private ActionListener onClickOptions = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			(new OptionsGUI(self)).build();
		}
	};
	private ActionListener onClickHelp = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Click!");
		}
	};
	private DocumentListener onTextChange = new DocumentListener() {
		@Override
		public void insertUpdate(DocumentEvent e) {
			updatePreview();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			updatePreview();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
		}
	};
	private ActionListener onSelectCode = new ActionListener() {
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox<String> cb = (JComboBox<String>) e.getSource();
			String code = Main.ampCodes.get(cb.getSelectedItem());
			int position = templateTextArea.getCaretPosition();
			templateTextArea.insert(code, position);
		}
	};
	private ActionListener onClickGrab = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			grabAndRelease = new GrabAndRelease();
			grabAndRelease.execute();
		}
	};
	private ActionListener onClickDrop = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			grabAndRelease.cancel(true);
		}
	};

	@Override
	public void build() {
		setTitle("Itemizer");
		setLayout(new GridLayout(3, 3));

		/* NW Panel */
		loadItemButton.addActionListener(onClickLoadItem);
		saveItemButton.addActionListener(onClickSaveItem);
		clearItemInfoButton.addActionListener(onClickClearItemInfo);
		nwPanel.add(loadItemButton);
		nwPanel.add(saveItemButton);
		nwPanel.add(clearItemInfoButton);

		/* W Panel */
		loadTemplateButton.addActionListener(onClickLoadTemplate);
		saveTemplateButton.addActionListener(onClickSaveTemplate);
		clearTemplateButton.addActionListener(onClickClearTemplate);
		wPanel.add(loadTemplateButton);
		wPanel.add(saveTemplateButton);
		wPanel.add(clearTemplateButton);

		/* SW Panel */

		wrapSlider.addChangeListener(onChangeWidth);
		wrapSlider.setMinimum(8);
		wrapSlider.setMaximum(64);
		wrapSlider.setPaintTicks(true);
		wrapSlider.setMajorTickSpacing(8);
		wrapSlider.setMinorTickSpacing(2);
		wrapSlider.setSnapToTicks(true);
		wrapSlider.setPaintLabels(true);
		wrapSlider.setValue(32);
		swPanel.add(new JLabel("Width"));
		swPanel.add(wrapSlider);

		/* NE Panel */
		optionsButton.addActionListener(onClickOptions);
		helpButton.addActionListener(onClickHelp);
		nePanel.add(optionsButton);
		nePanel.add(helpButton);

		/* E Panel */
		insertColorCodeCombo.addActionListener(onSelectCode);
		insertFormatCodeCombo.addActionListener(onSelectCode);
		ePanel.add(insertColorCodeCombo);
		ePanel.add(insertFormatCodeCombo);

		/* SE Panel */
		grabButton.addActionListener(onClickGrab);
		dropButton.addActionListener(onClickDrop);
		timerProgressBar.setMinimum(0);
		timerProgressBar.setMaximum(100);
		timerProgressBar.setValue(0);
		sePanel.add(grabButton);
		sePanel.add(dropButton);
		sePanel.add(timerProgressBar);

		/* N Panel */
		nPanel.setLayout(new BorderLayout());
		itemInfoTextArea.getDocument().addDocumentListener(onTextChange);
		nPanel.add(itemInfoScrollPane);

		/* C Panel */
		cPanel.setLayout(new BorderLayout());
		templateTextArea.getDocument().addDocumentListener(onTextChange);
		cPanel.add(templateScrollPane);

		/* S Panel */
		previewTextPane.setEditable(false);
		previewTextPane.setContentType("text/html");
		previewTextPane.setBackground(new Color(63, 63, 63));
		sPanel.setLayout(new BorderLayout());
		sPanel.add(previewScrollPane);

		addAll(panels);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void loadTemplate(File file) throws IOException {
		String template = Utilities.readFileToString(file);
		templateTextArea.setText(template);
		StringBuilder sb = new StringBuilder();
		boolean notFirstLine = false;
		for (String variable : Utilities.getVariables(template)) {
			if (notFirstLine) {
				sb.append('\n');
			}
			sb.append(variable + " = ");
			notFirstLine = true;
		}
		itemInfoTextArea.setText(sb.toString());
	}

	private void loadItem(File file) throws IOException {
		Scanner sc = new Scanner(file);
		int infoLines = Integer.parseInt(sc.nextLine());
		StringBuilder sb = new StringBuilder();
		boolean notFirstLine = false;
		for (int i = 0; i < infoLines; i++) {
			if (notFirstLine) {
				sb.append('\n');
			}
			sb.append(sc.nextLine());
			notFirstLine = true;
		}
		itemInfoTextArea.setText(sb.toString());

		sb = new StringBuilder();
		notFirstLine = false;
		while (sc.hasNextLine()) {
			if (notFirstLine) {
				sb.append('\n');
			}
			sb.append(sc.nextLine());
			notFirstLine = true;
		}
		templateTextArea.setText(sb.toString());
		sc.close();
	}

	private void saveTemplate(File file) throws IOException {
		Utilities.writeStringToFile(templateTextArea.getText(), file);
	}

	private void saveItem(File file) throws IOException {
		Utilities.writeStringToFile(buildItemString(), file);
	}

	private void updatePreview() {
		String wrapped = getCommandLines();
		String html = Utilities.minecraftCodeToHtml(wrapped);
		previewTextPane.setText(html);
	}

	private String getCommandLines() {
		String filled = Utilities.fillTemplate(itemInfoTextArea.getText(), templateTextArea.getText());
		return Utilities.wrapCode(filled, wrapSlider.getValue());
	}

	private String buildItemString() {
		StringBuilder sb = new StringBuilder();
		String itemInfo = itemInfoTextArea.getText();
		String template = templateTextArea.getText();
		int lineCount = 1;
		for (int i = 0; i < itemInfo.length(); i++) {
			if (itemInfo.charAt(i) == '\n') {
				lineCount++;
			}
		}
		sb.append(lineCount);
		sb.append('\n');
		sb.append(itemInfo);
		sb.append('\n');
		sb.append(template);
		return sb.toString();
	}

	private class GrabAndRelease extends SwingWorker<Integer, Integer> {

		@Override
		protected Integer doInBackground() {
			int fps = 10;
			int fullDelay = fps * Main.releaseDelay;
			int subDelay = 1000 / fps;
			int percent;
			for (int i = 1; i <= fullDelay; i++) {
				try {
					Thread.sleep(subDelay);
				} catch (InterruptedException ignored) {
				}
				percent = i * 100 / fullDelay;
				timerProgressBar.setValue(percent);
				if (isCancelled()) {
					return 1;
				}
			}
			String wrapped = getCommandLines();
			Scanner sc = new Scanner(wrapped);
			List<String> commands = new LinkedList<>();
			commands.add("itemizer clear name lore");
			commands.add("itemizer name " + sc.nextLine());
			while (sc.hasNextLine()) {
				commands.add("itemizer advlore add " + sc.nextLine());
			}
			sc.close();
			Main.automaton.sendCommands(commands);
			return 0;
		}
	}
}
