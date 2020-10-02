package entitys;

import java.awt.Point;

import abstractclasses.Entity;
import world.Images;
import world.World.Layers;

public class Box extends Entity {

	public Box(Point position) {
		super(false, true, position);
		setImage(Images.loadImage("Box", Layers.Entitys));
		setDescription("Computer tile that gives you some information when interacted");
	}

	@Override
	public void onInteract(Entity entity) {
		getWorld().removeEntity(this);
	}

}
