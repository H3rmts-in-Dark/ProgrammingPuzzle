package tiles;

import tasks.ChangeImageTask;
import world.Entity;
import world.Images;
import world.Tile;
import world.World.Layers;

public class Default extends Tile {

	public Default() {
		super(false, false);
		setPassable(false);
		addImage(Images.loadImage("Default", Layers.Floor));
		// addImage(Images.loadImage("Defaultobjekt",Layers.Objects));
		setDescription("Default Tile (for testing purposes)");
		new ChangeImageTask(5, this, Layers.Floor, true);
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