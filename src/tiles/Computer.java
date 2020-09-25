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
		setDescription("Computer tile that gives you some information when interacted");
	}

	@Override
	public void onInteract(Entity entity) {
<<<<<<< HEAD
		System.out.println(
				"Interacted with the computer tile at x: " + getPosition().getX() + " y: " + getPosition().getY());
=======
		System.out.println("interacted with computer " + toString() + "\n" + "USELESS INFORMATION");
>>>>>>> 34f27f43ef66ee073176b88dfc3a5b60c5bad254
	}

	@Override
	public void onSteppedUpon(Entity entity) {
<<<<<<< HEAD
		System.out.println("Stepped on computer tile on x: " + getPosition().getX() + " y: " + getPosition().getY());
=======
		// not passable
>>>>>>> 34f27f43ef66ee073176b88dfc3a5b60c5bad254
	}
}
