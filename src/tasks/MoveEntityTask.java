package tasks;


import Enums.Rotations;
import abstractclasses.Entity;
import abstractclasses.Task;


public class MoveEntityTask extends Task {

	private Entity entity;
	private Rotations direction;

	public MoveEntityTask(Integer tickDifference,Entity entity,Rotations direction) {
		super(tickDifference,DEFAULTIMAGEWIDHTHEIGHT);
		this.entity = entity;
		this.direction = direction;
	}

	@Override
	public void runCode() {
		entity.move(direction);
	}

}
