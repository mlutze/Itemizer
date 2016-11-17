import java.awt.Component;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public abstract class GUI extends JFrame {
	
	protected GUI parent = null;
	
	public void addAll(Component... components) {
		for (Component component : components) {
			add(component);
		}
	}
	
	public void close() {
		if (parent != null) {
			parent.setEnabled(true);
		}
		dispose();
	}
	
	public abstract void build();
}
