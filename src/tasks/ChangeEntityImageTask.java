package tasks;


import Enums.Animation;
import abstractclasses.Entity;
import abstractclasses.Task;


public class ChangeEntityImageTask extends Task {

	private Entity entity;
	private Animation animation;

	public ChangeEntityImageTask(Integer tickDifference,Entity entity,Integer loop,Animation animation) {
		super(tickDifference,loop);
		this.entity = entity;
		this.animation = animation;
	}

	@Override
	public boolean runCode() {
		entity.nextimage();
		return true;
	}

	@Override
	public void onEnd() {
		if (Animation.getRepeat(animation))
			entity.triggerAnimation(animation);
		else
			entity.triggerAnimation(Animation.deactivatedanimation);
	}

}
