package tiles;

import abstractclasses.Entity;
import abstractclasses.Tile;
import world.Images;
import world.World.Layers;

public class Computer extends Tile {

	public Computer() {
		super(UNPASSABLE, ANIMATED);
		setDescription("Computer tile that gives you" + "\n" + "some information when interacted with");
	}

	@Override
	public void loadAnimation() {
		setImage(Layers.Floor, Images.loadLayeranimation("Default", Layers.Floor));
		addObjektAnimation(Images.loadObjektAnimation("Computer", DEFAULTANIMATION, this));
		addObjektAnimation(Images.loadObjektAnimation("Computer", INTERACTANIMATION, this));
	}

	@Override
	public void onInteract(Entity entity) {
		System.out.println("interacted with computer " + toString() + "\n" + "USELESS INFORMATION");
	}
}
