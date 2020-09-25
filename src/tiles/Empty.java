package tiles;

import world.*;
import world.World.Layers;

public class Empty extends Tile {

	public Empty(Integer x, Integer y) {
		super(true, false);
		setPassable(true);
		setDescription("");  //no description should be shown if clicked on tile
		addImage(Images.loadImage("Missing", Layers.Floor));
	}

	@Override
	public void onInteract(Entity entity) {
	}

	@Override
	public void onSteppedUpon(Entity entity) {
	}
}
