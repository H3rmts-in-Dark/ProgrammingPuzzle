package tiles;

import abstractclasses.Entity;
import abstractclasses.Tile;
import world.Images;
import world.World.Layers;

public class Empty extends Tile {

	public Empty() {
		super(true, false);
		setDescription(""); // no description should be shown if clicked on tile
		addImage(Images.loadImage("Missing", Layers.Floor));
	}

	@Override
	public void onInteract(Entity entity) {
	}

	@Override
	public void onSteppedUpon(Entity entity) {
	}
}
