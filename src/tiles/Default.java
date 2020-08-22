package tiles;

import logic.ImageLoader;
import world.Tile;
import world.World.Layers;

public class Default extends Tile {

	public Default(Integer x, Integer y) {
		super(x, y, false, false); // TODO Need Path
		addImage(ImageLoader.loadImage("Default", Layers.Floor));
		addImage(ImageLoader.loadImage("Defaultobjekt", Layers.Objects));
		setPassable(false);
	}

	@Override
	public void onInteract() {
		System.out.println("Interacted with the default tile at x: " + location.getX() + " y: " + location.getY());
	}

	@Override
	public void onSteppedUpon() {
		System.out.println("Stepped on default tile on x: " + location.getX() + " y: " + location.getY());
	}
}