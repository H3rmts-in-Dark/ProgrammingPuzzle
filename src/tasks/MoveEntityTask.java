package tasks;

import java.awt.Point;

import abstractclasses.Entity;
import abstractclasses.Task;
import logic.Rotation;

public class MoveEntityTask extends Task {
	
	private Entity entity;
	private Rotation rotation;
	
	public MoveEntityTask(Integer tickDifference, Entity entity, Rotation rotation) {
		super(tickDifference, DEFAULTTILEWIDTH);
		this.entity = entity;
		this.rotation = rotation;
	}

	@Override
	public void runCode() {
		switch (rotation) {
		case down:
			entity.setPixelPosition(new Point(entity.getDrawX(0),entity.getDrawX(1)));
			break;
		case left:
			entity.setPixelPosition(new Point(entity.getDrawX(-1),entity.getDrawX(0)));
			break;
		case right:
			entity.setPixelPosition(new Point(entity.getDrawX(+1),entity.getDrawX(0)));
			break;
		case up:
			entity.setPixelPosition(new Point(entity.getDrawX(0),entity.getDrawX(-1)));
			break;
		}
	}

	@Override
	public void onEnd() {
		entity.setPixelPosition(new Point(entity.getPosition().x*DEFAULTTILEWIDTH,entity.getPosition().y*DEFAULTTILEWIDTH));
		entity.getWorld().getTile(entity.getPosition().y,entity.getPosition().y).onSteppedUpon(entity);
	}

}
