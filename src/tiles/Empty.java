package tiles;

import logic.ImageLoader;
import world.World.Layers;

public class Empty extends world.Tile {

	public Empty(Integer x, Integer y) {
		super(true, false);
		setPassable(true);
		addImage(ImageLoader.loadImage("Missing", Layers.Floor));
	}

	@Override
	public void onInteract() {
	}

	@Override
	public void onSteppedUpon() {
	}
}
