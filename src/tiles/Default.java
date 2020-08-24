package tiles;

import logic.ImageLoader;
import tasks.ChangeImage;
import world.Tile;
import world.World.Layers;

public class Default extends Tile {

	public Default() {
		super(false, false);
		setPassable(false);
		addImage(ImageLoader.loadImage("Default",Layers.Floor));
		//addImage(ImageLoader.loadImage("Defaultobjekt",Layers.Objects));
		new ChangeImage(5,getImageholder(Layers.Floor),true);
	}

	@Override
	public void onInteract() {
		System.out.println(
				"Interacted with the default tile at x: " + getPosition().getX() + " y: " + getPosition().getY());
	}

	@Override
	public void onSteppedUpon() {
		System.out.println("Stepped on default tile on x: " + getPosition().getX() + " y: " + getPosition().getY());
	}
}