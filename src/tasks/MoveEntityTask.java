package tasks;


import Enums.Rotations;
import abstractclasses.Entity;
import abstractclasses.Task;
import abstractclasses.Tile;


public class MoveEntityTask extends Task {

	private Entity entity;
	private Rotations direction;
	private Tile left;

	public MoveEntityTask(Integer tickDifference,Entity entity,Rotations direction) {
		super(tickDifference,DEFAULTIMAGEWIDHTHEIGHT);
		this.entity = entity;
		this.direction = direction;
		left = entity.getWorld().getTile(entity.getPosition().x,entity.getPosition().y);
	}

	@Override
	public boolean runCode() {
		if (left.getActivated()) {
			entity.move(direction);
			return true;
		}
		return false;
	}

	@Override
	public void onEnd() {
		left.onEntityLeft(entity);
		entity.getWorld().getTile(entity.getPosition().x,entity.getPosition().y).onSteppedUpon(entity);
		entity.setHeight(entity.getWorld().getTile(entity.getPosition().x,entity.getPosition().y).getHeight());
	}

}
