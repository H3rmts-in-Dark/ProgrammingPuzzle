package tasks;

import java.awt.Point;

import abstractclasses.Entity;
import abstractclasses.Task;

public class MoveEntityTask extends Task {

	private Entity entity;

	public MoveEntityTask(Integer tickDifference, Entity entity) {
		super(tickDifference, TILEHEIGHTWIDHT);
		this.entity = entity;
	}

	@Override
	public void runCode() {
		System.out.println("switch");
		switch (entity.getRotation()) {
		case down:
			entity.setPixelPosition(new Point(entity.getPixelposition().x, entity.getPixelposition().y + 1));
			break;
		case left:
			entity.setPixelPosition(new Point(entity.getDrawX(-1), entity.getDrawX(0)));
			break;
		case right:
			entity.setPixelPosition(new Point(entity.getPixelposition().x + 1, entity.getPixelposition().y));
			break;
		case up:
			entity.setPixelPosition(new Point(entity.getDrawX(0), entity.getDrawX(-1)));
			break;
		}
		entity.getWorld().getWindow().triggerFullRepaint();
	}

	@Override
	public void onEnd() {
		entity.getWorld().getTile(entity.getPosition().y, entity.getPosition().y).onSteppedUpon(entity);
	}

}
