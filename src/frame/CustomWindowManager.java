package frame;


import java.util.LinkedList;

import javax.swing.JLayeredPane;

import abstractclasses.CustomWindow;
import logic.Constants;


public class CustomWindowManager extends JLayeredPane implements Constants {

	CustomWindow fullscreen;
	LinkedList<CustomWindow> windows;

	public CustomWindowManager() {
		windows = new LinkedList<>();
	}
	
	public LinkedList<CustomWindow> getWindows() {
		return windows;
	}

	public void addWindow(CustomWindow window) {
		windows.add(window);
		windowToFront(window);
		clean();
		windowToFront(window);
	}

	public void removeWindow(CustomWindow newWindow) {
		windows.remove(newWindow);
		clean();
	}

	public void windowToFront(CustomWindow window) {
		window.setLayer(nextHighestLayer());
		clean();
	}

	public void setFullscreen(CustomWindow fullscreen) {
		if (this.fullscreen != null) {
			this.fullscreen.setBounds(DEFAULTX,DEFAULTY,DEFAULTWITH,DEFAULTHEIGHT);
		}
		this.fullscreen = fullscreen;
		if (fullscreen != null)
			fullscreen.setLayer(-1);
		clean();
	}
	
	public boolean isFullscreen(CustomWindow window) {
		return fullscreen == window;
	}

	private void clean() {
		for (CustomWindow window : windows) {
			System.out.println(window + ":" + window.getLayer());
		}
		System.out.println();
		windows.sort(null);
		Integer actlayer = 0;
		for (CustomWindow window : windows) {
			window.setLayer(actlayer);
			actlayer++;
		}
		addComponents();
	}

	private void addComponents() {
		removeAll();
		for (CustomWindow window : windows) {
			System.out.println(window + ":" + window.getLayer());
			add(window);
		}
		System.out.println();
		System.out.println();
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
