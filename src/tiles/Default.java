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
<<<<<<< HEAD
		System.out.println(
				"Interacted with the default tile at x: " + getPosition().getX() + " y: " + getPosition().getY());
=======
		System.out.println(entity.getClass() + "interacted with the default tile at x: " + getPosition().getX() + " y: "
				+ getPosition().getY());
>>>>>>> 34f27f43ef66ee073176b88dfc3a5b60c5bad254
	}

	@Override
	public void onSteppedUpon(Entity entity) {
<<<<<<< HEAD
		System.out.println("Stepped on default tile on x: " + getPosition().getX() + " y: " + getPosition().getY());
=======
		System.out.println(entity.getClass() + "stepped on default tile on x: " + getPosition().getX() + " y: "
				+ getPosition().getY());
>>>>>>> 34f27f43ef66ee073176b88dfc3a5b60c5bad254
	}
}