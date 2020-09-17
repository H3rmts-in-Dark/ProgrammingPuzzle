package tiles;

import tasks.ChangeImageTask;
import world.Images;
import world.Tile;
import world.World.Layers;

public class Default extends Tile {

	public Default() {
		super(false, false);
		setPassable(false);
		addImage(Images.loadImage("Default",Layers.Floor));
		//addImage(Images.loadImage("Defaultobjekt",Layers.Objects));
		new ChangeImageTask(5,getImageholder(Layers.Floor),true);
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