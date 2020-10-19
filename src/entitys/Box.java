package entitys;

import java.awt.Point;

import abstractclasses.Entity;
import world.Images;

public class Box extends Entity {

	public Box(Integer X, Integer Y) {
		super(true, new Point(X, Y), 8, 15);
		setDescription("Computer tile that gives you some information when interacted");
	}

	@Override
	public void onInteract(Entity entity) {
		getWorld().removeEntity(this);
	}

	@Override
	public void loadAnimation() {
		addAnimation(Images.loadEntityAnimation("Box", DEFAULTANIMATION, this));
	}

}
