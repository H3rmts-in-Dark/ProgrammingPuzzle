package tiles;

import world.Entity;
import world.Images;
import world.Tile;
import world.World.Layers;

public class Computer extends Tile {

	public Computer() {
		super(false, false);
		setPassable(false);
		addImage(Images.loadImage("Default", Layers.Floor));
		addImage(Images.loadImage("Computer", Layers.Objects));
	}

	@Override
	public void onInteract(Entity entity) {
	}

	@Override
	public void onSteppedUpon(Entity entity) {
	}
}
