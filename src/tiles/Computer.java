package tiles;

import world.Entity;
import world.Images;
import world.Tile;
import world.World.Layers;

public class Computer extends Tile {

	public Computer() {
		super(false, false);
		addImage(Images.loadImage("Default", Layers.Floor));
		addImage(Images.loadImage("Computer", Layers.Objects));
		setDescription("Computer tile that gives you some information when interacted with");
	}

	@Override
	public void onInteract(Entity entity) {
		System.out.println("interacted with computer " + toString() + "\n" + "USELESS INFORMATION");
	}

	@Override
	public void onSteppedUpon(Entity entity) {
		// not passable
	}
}
