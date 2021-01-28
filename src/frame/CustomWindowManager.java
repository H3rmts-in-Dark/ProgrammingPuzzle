package frame;


import java.util.LinkedList;

import javax.swing.JLayeredPane;

import abstractclasses.CustomWindow;
import logic.Constants;
import logic.Debugger;


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
		clean();
	}

	public void removeWindow(CustomWindow newWindow) {
		windows.remove(newWindow);
		Debugger.removeWindow(newWindow);
		clean();
	}

	public void windowToFront(CustomWindow window) {
		window.setLayer(-1);
		clean();
	}

	private void clean() {
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
			add(window);
		}
	}

}
