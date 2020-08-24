package tasks;

import logic.Task;
import world.Imageholder;

public class ChangeImage extends Task {
	
	Imageholder imageholder;

	public ChangeImage(Integer tickDifference,Imageholder imageholder,Boolean loop) {
		super(tickDifference,loop);
		this.imageholder = imageholder;
	}

	@Override
	public void runCode() {
		//System.out.println("nxt");
		imageholder.nextImage();
	}
}