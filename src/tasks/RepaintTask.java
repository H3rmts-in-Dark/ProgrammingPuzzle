package tasks;

import abstractclasses.CustomWindow;
import abstractclasses.Task;
import frame.Frame;

public class RepaintTask extends Task {

	CustomWindow window;

	public RepaintTask(Integer tickDifference, CustomWindow window) {
		super(tickDifference, -1);
		this.window = window;
	}

	@Override
	public void runCode() {
		window.triggerFullRepaint();
		window.repaint();
	}

}
