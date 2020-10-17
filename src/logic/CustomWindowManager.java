package logic;

import java.util.ArrayList;

import javax.swing.JLayeredPane;

import abstractclasses.CustomWindow;

public class CustomWindowManager extends JLayeredPane {

	// Haha Windows
	ArrayList<CustomWindow> windows;

	public CustomWindowManager() {
		windows = new ArrayList<>();
	}

	public void addWindow(CustomWindow window) {
		windows.add(window);
		window.setLayer(nextHighestLayer());
		window.setFocused(true);
		clean();
	}

	public void removeWindow(CustomWindow newWindow) {
		windows.remove(newWindow);
		clean();
	}

	public void windowToFront(CustomWindow window) {
		window.setLayer(nextHighestLayer());
		clean();
	}

	private void clean() {
		windows.sort(null);
		Integer actlayer = 0;
		for (CustomWindow window : windows) {
			window.setLayer(actlayer);
			window.setFocused(false);
			actlayer++;
		}
		if (windows.size() > 0)
			windows.get(windows.size() - 1).setFocused(true);
		addComponents();
	}

	private void addComponents() {
		removeAll();
		for (CustomWindow window : windows) {
			add(window, window.getLayer());
		}
	}

	private int nextHighestLayer() {
		Integer layer = 0;
		for (CustomWindow window : windows) {
			if (window.getLayer() > layer)
				layer = window.getLayer();
		}
		return ++layer;
	}
}