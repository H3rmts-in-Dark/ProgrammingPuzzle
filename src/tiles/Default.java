package tiles;

import abstractclasses.Entity;
import abstractclasses.Tile;
import world.Images;
import world.World.Layers;

public class Default extends Tile {

	public Default() {
		super(false, false);
		addImage(Images.loadImage("Default", Layers.Floor));
		setDescription("Default Tile (for testing purposes)");
	}

	@Override
	public void onInteract(Entity entity) {
		System.out.println("Interacted with the default tile at x: " + getPosition().getX() + " y: " + getPosition().getY());
	}

	@Override
	public void onSteppedUpon(Entity entity) {
		System.out.println("Stepped on default tile on x: " + getPosition().getX() + " y: " + getPosition().getY());
	}
}