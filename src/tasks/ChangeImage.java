package tasks;

import logic.Task;
import world.Imageholder;

public class ChangeImage extends Task {
	
	Imageholder imageholder;

	public ChangeImage(Double timeDifference,Imageholder imageholder,Boolean loop) {
		super(timeDifference,loop);
		this.imageholder = imageholder;
	}

	@Override
	public void runCode() {
		System.out.println("nxt");
		//imageholder.nextImage();
	}
}