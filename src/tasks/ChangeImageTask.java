package tasks;

import logic.Task;
import world.Imageholder;

public class ChangeImageTask extends Task {

	Imageholder imageholder;

	public ChangeImageTask(Integer tickDifference,Imageholder imageholder,Boolean loop) {
		super(tickDifference,loop ? -1:0);
		this.imageholder = imageholder;
	}

	@Override
	public void runCode() {
		imageholder.nextImage();
	}
}