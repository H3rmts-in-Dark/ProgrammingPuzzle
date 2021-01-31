package tasks;


import Enums.Animations;
import abstractclasses.Entity;
import abstractclasses.Task;


public class ChangeEntityImageTask extends Task {

	private Entity entity;
	private Animations animation;

	public ChangeEntityImageTask(Integer tickDifference,Entity entity,Integer loop,Animations animation) {
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
		if (Animations.getRepeat(animation))
			entity.triggerAnimation(animation);
		else
			entity.triggerAnimation(Animations.deactivatedanimation);
	}

}
