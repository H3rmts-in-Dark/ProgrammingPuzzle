package tiles;

import logic.ImageLoader;
<<<<<<< HEAD
import tasks.ChangeImage;
import world.Tile;
import world.World.Layers;
=======
import world.*;
>>>>>>> 704ce74452233b45c6ce6e181e249505ac92d1a6

public class Default extends Tile {

	public Default() {
		super(false, false);
		setPassable(false);
<<<<<<< HEAD
		
		addImage(ImageLoader.loadImage("Default", Layers.Floor));
		//addImage(ImageLoader.loadImage("Defaultobjekt", Layers.Objects));
		
		new ChangeImage(5,getImageholder(Layers.Floor),true);
		
=======

		addImage(ImageLoader.loadImage("Default", world.World.Layers.Floor));
		addImage(ImageLoader.loadImage("Defaultobjekt", world.World.Layers.Objects));
	}

	@Override
	public void onInteract() {
		System.out.println(
				"Interacted with the default tile at x: " + getPosition().getX() + " y: " + getPosition().getY());
	}

	@Override
	public void onSteppedUpon() {
		System.out.println("Stepped on default tile on x: " + getPosition().getX() + " y: " + getPosition().getY());
>>>>>>> 704ce74452233b45c6ce6e181e249505ac92d1a6
	}
}