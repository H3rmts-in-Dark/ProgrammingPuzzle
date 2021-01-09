package tasks;

import java.util.ArrayList;

import abstractclasses.CustomWindow;
import abstractclasses.Task;
import frame.WorldWindow;

public class WindowRepaintTask extends Task {

	CustomWindow window;
	
	static ArrayList<CustomWindow> torepaint = new ArrayList<>();

	public WindowRepaintTask(Integer tickDifference, CustomWindow window, boolean loop) {
		super(tickDifference, loop ? -1 : 1);
		this.window = window;
	}

	@Override
	public void runCode() {
		window.triggerFullRepaint();
		if (window instanceof WorldWindow) 
			((WorldWindow) window).renewImage(null);
	}
	
	@Override
	public void onEnd() {
		torepaint.remove(window);
	}
	
	public static void RepaintWindow(CustomWindow window) {
		if (!torepaint.contains(window)) {
			new WindowRepaintTask(1,window,false);
			torepaint.add(window);
		}
	}

}
