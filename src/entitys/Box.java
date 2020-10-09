package entitys;

import java.awt.Point;

import abstractclasses.Entity;
import world.Images;

public class Box extends Entity {

	public Box(Point position) {
		super(false,true, position);
		addAnimation(Images.loadEntityAnimation("Box","default animation"));
		triggerObjektAnimation(getObjektanimations().get(0));
		setDescription("Computer tile that gives you some information when interacted");
	}

	@Override
	public void onInteract(Entity entity) {
		getWorld().removeEntity(this);
	}

}
