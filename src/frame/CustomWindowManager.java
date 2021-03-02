package frame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.swing.JLayeredPane;

import abstractclasses.CustomWindow;
import logic.Constants;
import logic.Debugger;

public class CustomWindowManager extends JLayeredPane implements Constants {

	CustomWindow fullscreen;
	TreeMap<Integer, LinkedList<CustomWindow>> windows;

	ArrayList<CustomWindow> remove;

	public CustomWindowManager() {
		windows = new TreeMap<Integer, LinkedList<CustomWindow>>();
		remove = new ArrayList<>();
	}

	public void addWindow(int layer, CustomWindow window) {
		if (windows.get(layer) == null)
			windows.put(layer, new LinkedList<>());
		windows.get(layer).add(window);
		add(window);
		windowToFront(window);
	}

	public void removeWindow(CustomWindow Window) {
		remove.add(Window);
		Debugger.removeWindow(Window);
		clean();
	}

	public ArrayList<CustomWindow> getWindows() {
		ArrayList<CustomWindow> ret = new ArrayList<>();
		for (LinkedList<CustomWindow> list : windows.values()) {
			for (int i = 0; i < list.size(); i++) {
				if (!remove.contains(list.get(i)))
					ret.add(list.get(i));
				else
					list.remove(list.get(i));
			}
		}
		return ret;
	}

	public void windowToFront(CustomWindow window) {
		window.setLayer(10000);
		clean();
	}

	private void clean() {
		for (LinkedList<CustomWindow> linkedList : windows.values()) {
			linkedList.sort(null);
			int actlayer = 0;
			for (CustomWindow window : linkedList) {
				window.setLayer(actlayer);
				actlayer++;
			}
		}
		addComponents();
	}

	private void addComponents() {
		for (LinkedList<CustomWindow> linkedList : windows.values())
			for (CustomWindow customWindow : linkedList)
				setLayer(customWindow, customWindow.getLayer());
	}

}
