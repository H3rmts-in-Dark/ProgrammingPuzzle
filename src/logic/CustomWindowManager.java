package logic;

import java.util.ArrayList;

import javax.swing.JLayeredPane;

import abstractclasses.CustomWindow;
import frame.Frame;

public class CustomWindowManager extends JLayeredPane {

	CustomWindow fullscreen;
	CustomWindow focused;
	ArrayList<CustomWindow> windows;

	public CustomWindowManager() {
		windows = new ArrayList<>();
	}

	public void addWindow(CustomWindow window) {
		windows.add(window);
		window.setLayer(nextHighestLayer());
		setFocused(window);
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

	public void setFocused(CustomWindow focused) {
		this.focused = focused;
	}

	public void setFullscreen(CustomWindow fullscreen) {
		this.fullscreen = fullscreen;
	}

	public Boolean isFocused(CustomWindow test) {
		return focused.equals(test);
	}
	
	public Boolean isFullscreen(CustomWindow test) {
		return fullscreen == null ? false : fullscreen.equals(test);
	}

	private void clean() {
		windows.sort(null);
		Integer actlayer = 0;
		for (CustomWindow window : windows) {
			window.setLayer(actlayer);
			actlayer++;
		}
		if (windows.size() > 0)
			setFocused(windows.get(windows.size() - 1));
		addComponents();
	}

	private void addComponents() {
		removeAll();
		for (CustomWindow window : windows) {
			add(window, window.getLayer());
		}
		Frame.repaint();
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