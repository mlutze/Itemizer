import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.util.List;

public class Automaton extends Robot implements ClipboardOwner {

	private Transferable clipboardCache;
	private Clipboard clipboard;

	public Automaton() throws AWTException {
		super();
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	}

	public void sendString(String string) {
		cacheClipboard();
		sendStringUnprotected(string);
		decacheClipboard();
	}

	private void sendStringUnprotected(String string) {
		fillClipboard(string);
		sendPaste();
	}

	private void cacheClipboard() {
		clipboardCache = clipboard.getContents(this);
	}

	private void fillClipboard(String string) {
		clipboard.setContents(new StringSelection(string), this);
	}

	private void sendPaste() {
		keyPress(Main.pasteKey);
		delay(100);
		keyTap(KeyEvent.VK_V);
		delay(100);
		keyRelease(Main.pasteKey);
	}

	private void decacheClipboard() {
		clipboard.setContents(clipboardCache, this);
	}

	public void keyTap(int key) {
		keyPress(key);
		keyRelease(key);
	}

	private void sendCommandUnprotected(String string) {
		int delay = 100;
		keyTap(KeyEvent.VK_SLASH);
		delay(delay);
		sendStringUnprotected(string);
		delay(delay);
		keyTap(KeyEvent.VK_ENTER);
		delay(delay);
	}

	public void sendCommand(String string) {
		cacheClipboard();
		sendCommandUnprotected(string);
		decacheClipboard();
	}

	public void sendCommands(List<String> strings) {
		cacheClipboard();
		for (String string : strings) {
			sendCommandUnprotected(string);
		}
		decacheClipboard();
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
	}
}
