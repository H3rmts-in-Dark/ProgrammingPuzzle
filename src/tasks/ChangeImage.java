package tasks;

import logic.Task;
import world.Imageholder;

public class ChangeImage extends Task {

	Imageholder imageholder;

<<<<<<< HEAD
	public ChangeImage(Integer tickDifference,Imageholder imageholder,Boolean loop) {
		super(tickDifference,loop);
=======
	public ChangeImage(Double timeDifference, Imageholder imageholder, Boolean loop) {
		super(timeDifference, loop);
>>>>>>> 704ce74452233b45c6ce6e181e249505ac92d1a6
		this.imageholder = imageholder;
	}

	@Override
	public void runCode() {
<<<<<<< HEAD
		//System.out.println("nxt");
		imageholder.nextImage();
=======
		System.out.println("nxt");
		// imageholder.nextImage();
>>>>>>> 704ce74452233b45c6ce6e181e249505ac92d1a6
	}
}