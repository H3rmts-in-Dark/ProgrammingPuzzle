package logic;

import java.util.ArrayList;

import javax.swing.JLayeredPane;

import abstractclasses.CustomWindow;

public class Customwindowmanger extends JLayeredPane {
	
	ArrayList<CustomWindow> windows;
	
	public Customwindowmanger() {
		windows = new ArrayList<>();
	}
	
	public void addWindow(CustomWindow window) {
		windows.add(window);
		window.setLayer(nexthighestLayer());
		window.setFocused(true);
		clean();
	}
	
	public void removeWindow(CustomWindow newWindow) {
		windows.remove(newWindow);
		clean();
	}
	
	public void Windowtofront(CustomWindow window) {
		window.setLayer(nexthighestLayer());
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
			windows.get(windows.size()-1).setFocused(true);
		addComponents();
	}
	
	private void addComponents() {
		removeAll();
		for (CustomWindow window : windows) {
			add(window,window.getLayer());
		}
	}

	private int nexthighestLayer() {
		Integer layer = 0;
		for (CustomWindow window : windows) {
			if (window.getLayer()>layer)
				layer = window.getLayer();
		}
		return layer+1;
	}
}