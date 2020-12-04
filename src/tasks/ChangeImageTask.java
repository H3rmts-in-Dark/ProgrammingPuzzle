package tasks;

import abstractclasses.Task;
import world.Animation;

public class ChangeImageTask extends Task {

	final private Animation animation;

	public ChangeImageTask(Integer tickDifference, Animation animation, Integer loop) {
		super(tickDifference, loop);
		this.animation = animation;
	}

	@Override
	public void runCode() {
		animation.nextImage();
		animation.redrawimage();
	}

	@Override
	public void onEnd() {
		animation.triggerDefault();
	}
}