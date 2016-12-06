import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Main {

	/* Options */
	protected static int copyKey;
	protected static int pasteKey;
	protected static int commandSpeed;
	protected static int releaseDelay;

	protected static Automaton automaton;

	protected static Map<String, String> ampCodes = new HashMap<>();
	protected static String[] colors = { "Insert Color Code", "Black", "Dark Blue", "Dark Green", "Dark Aqua",
			"Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple",
			"Yellow", "White" };
	protected static String[] formats = { "Insert Format Code", "Obfuscated", "Bold", "Strikethrough", "Underline",
			"Italic", "Reset", "No Wrap" };

	public static void main(String[] args) throws AWTException {
		/* Set defaults */
		String osName = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
		automaton = new Automaton();
		commandSpeed = 60;
		releaseDelay = 5;

		/* Parse Args */
		String arg;
		for (int i = 0; i < args.length; i++) {
			arg = args[i];
			if (arg.equalsIgnoreCase("-o") && i + 1 < args.length) {
				osName = args[i + 1];
			}
		}
		if (osName.contains("mac") || osName.contains("darwin")) {
			copyKey = KeyEvent.VK_META;
		} else {
			copyKey = KeyEvent.VK_CONTROL;
		}
		pasteKey = KeyEvent.VK_CONTROL;

		ampCodes.put("Black", "&0");
		ampCodes.put("Dark Blue", "&1");
		ampCodes.put("Dark Green", "&2");
		ampCodes.put("Dark Aqua", "&3");
		ampCodes.put("Dark Red", "&4");
		ampCodes.put("Dark Purple", "&5");
		ampCodes.put("Gold", "&6");
		ampCodes.put("Gray", "&7");
		ampCodes.put("Dark Gray", "&8");
		ampCodes.put("Blue", "&9");
		ampCodes.put("Green", "&a");
		ampCodes.put("Aqua", "&b");
		ampCodes.put("Red", "&c");
		ampCodes.put("Light Purple", "&d");
		ampCodes.put("Yellow", "&e");
		ampCodes.put("White", "&f");
		ampCodes.put("Obfuscated", "&k");
		ampCodes.put("Bold", "&l");
		ampCodes.put("Strikethrough", "&m");
		ampCodes.put("Underline", "&n");
		ampCodes.put("Italic", "&o");
		ampCodes.put("Reset", "&r");
		ampCodes.put("No Wrap", "&*");
		ampCodes.put("Insert Color Code", "");
		ampCodes.put("Insert Format Code", "");

		/* Open MainGUI */
		MainGUI mainGui = new MainGUI();
		mainGui.build();
	}
}
