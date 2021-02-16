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
	TreeMap<Integer,LinkedList<CustomWindow>> windows;

	public CustomWindowManager() {
		windows = new TreeMap<Integer,LinkedList<CustomWindow>>();
	}

	public void addWindow(int layer,CustomWindow window) {
		
		if (windows.get(layer) == null)
			windows.put(layer,new LinkedList<>());
		windows.get(layer).add(window);
		add(window);
		windowToFront(window);
		
		//add(window);
	}
	
	public void removeWindow(CustomWindow newWindow) {
		for (LinkedList<CustomWindow> linkedList : windows.values()) {
			for (CustomWindow customWindow : linkedList) {
				if (customWindow == newWindow) {
					linkedList.remove(newWindow);
					break;
				}
			}
		}
		Debugger.removeWindow(newWindow);
		clean();
	}

	public ArrayList<CustomWindow> getWindows() {
		ArrayList<CustomWindow> ret = new ArrayList<>();
		for (LinkedList<CustomWindow> list : windows.values()) {
			for (CustomWindow customWindow : list) {
				ret.add(customWindow);
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
		for (LinkedList<CustomWindow> linkedList : windows.values()) {
			for (CustomWindow customWindow : linkedList) {
				setLayer(customWindow,customWindow.getLayer());
			}
		}
	}

}
