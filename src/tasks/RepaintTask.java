package tasks;

import abstractclasses.CustomWindow;
import abstractclasses.Task;

public class RepaintTask extends Task {
	
	CustomWindow window;
	
	public RepaintTask(Integer tickDifference, CustomWindow window) {
		super(tickDifference, -1);
		this.window = window;
	}

	@Override
	public void runCode() {
		window.triggerFullRepaint();
	}

	@Override
	public void onEnd() {
		// will not end
	}

}
