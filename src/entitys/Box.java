package entitys;

import java.awt.Point;

import abstractclasses.Entity;
import world.Images;

public class Box extends Entity {

	public Box(Point position) {
		super(true, position);
		setDescription("Computer tile that gives you some information when interacted");
	}

	@Override
	public void onInteract(Entity entity) {
		getWorld().removeEntity(this);
	}

	@Override
	public void loadanimation() {
		addAnimation(Images.loaddefaultEntityAnimation("Box", this),defaultanimation);
	}

}
