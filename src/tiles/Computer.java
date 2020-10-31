package tiles;

import abstractclasses.Entity;
import abstractclasses.Tile;
import world.Animation;
import world.Images;
import logic.Layers;

public class Computer extends Tile {

	public Computer() {
		super(UNPASSABLE, ANIMATED, -5, -20);
		setDescription("Computer tile that gives you" + "\n" + "some information when interacted with");
	}

	@Override
	public void loadAnimation() {
		setImage(Layers.Floor, Images.loadLayerPicture("Default", Layers.Floor));
		addObjektAnimation(Animation.loadObjektAnimation("Computer", null, DEFAULTANIMATION, this));
		addObjektAnimation(Animation.loadObjektAnimation("Computer", null, INTERACTANIMATION, this));
	}

	@Override
	public void onInteract(Entity entity) {
		System.out.println("interacted with computer " + toString() + "\n" + "USE LESS INFORMATION");
	}
}
