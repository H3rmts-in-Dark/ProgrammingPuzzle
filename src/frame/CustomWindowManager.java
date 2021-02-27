package frame;


import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JDesktopPane;
import javax.swing.JLayeredPane;

import abstractclasses.CustomWindow;
import logic.Constants;
import logic.Debugger;


public class CustomWindowManager extends JLayeredPane implements Constants {

	HashMap<Integer,JDesktopPane> desktops;

	public CustomWindowManager(Rectangle rectangle) {
		super();
		desktops = new HashMap<>();

		setBounds(rectangle.x,rectangle.y,rectangle.width,rectangle.height);

		desktops.put(0,new JDesktopPane());
		desktops.put(1,new JDesktopPane());

		desktops.get(0).setBounds(rectangle.x,rectangle.y,rectangle.width,rectangle.height);
		desktops.get(1).setBounds(rectangle.x,rectangle.y,rectangle.width,rectangle.height);

		desktops.get(0).setOpaque(false);
		desktops.get(1).setOpaque(false);

		desktops.get(0).setVisible(true);
		desktops.get(1).setVisible(true);

		add(desktops.get(0),JLayeredPane.DEFAULT_LAYER);
		add(desktops.get(1),JLayeredPane.PALETTE_LAYER);

		System.out.println(Arrays.asList(getComponents()));
	}

	public void addWindow(int layer,CustomWindow window) {
		desktops.get(layer).add(window);
	}

	public void removeWindow(int layer,CustomWindow Window) {
		desktops.get(layer).remove(Window);
		Debugger.removeWindow(Window);
	}

	public ArrayList<CustomWindow> getWindows() {
		var windows = new ArrayList<CustomWindow>();
		for (JDesktopPane dp : desktops.values()) {
			windows.addAll(Arrays.asList((Arrays.asList(dp.getComponents()).toArray(new CustomWindow[0]))));
		}
		return windows;
	}

}
